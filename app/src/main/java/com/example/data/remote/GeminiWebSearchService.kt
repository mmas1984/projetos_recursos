package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.model.EditalPublico
import com.example.model.FilterParams
import com.example.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class GeminiWebSearchService {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun searchEditais(filters: FilterParams): SearchResult = withContext(Dispatchers.IO) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val apiKey = BuildConfig.GEMINI_API_KEY

        // Check if API key is provided and not default placeholder
        val hasValidApiKey = apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY"

        if (hasValidApiKey) {
            try {
                val liveResult = executeLiveSearch(apiKey, filters, currentDate)
                if (liveResult != null && liveResult.editais.isNotEmpty()) {
                    return@withContext liveResult
                }
            } catch (e: Exception) {
                Log.e("GeminiWebSearch", "Error executing live Gemini search: ${e.message}", e)
            }
        }

        // Fallback: Rich curated verified database matching filters
        return@withContext getVerifiedFallbackResult(filters, currentDate)
    }

    private fun executeLiveSearch(apiKey: String, filters: FilterParams, currentDate: String): SearchResult? {
        val userPrompt = buildPrompt(filters, currentDate)

        val requestJson = JSONObject().apply {
            val contentsArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", userPrompt)
                        })
                    })
                })
            }
            put("contents", contentsArray)

            // Enable Google Search Grounding for real-time web search
            val toolsArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("googleSearch", JSONObject())
                })
            }
            put("tools", toolsArray)

            put("generationConfig", JSONObject().apply {
                put("temperature", 0.1)
                put("topP", 0.95)
            })
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
        val requestBody = requestJson.toString().toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = httpClient.newCall(request).execute()
        val responseBody = response.body?.string() ?: return null

        if (!response.isSuccessful) {
            Log.w("GeminiWebSearch", "HTTP ${response.code}: $responseBody")
            return null
        }

        return parseGeminiResponse(responseBody, currentDate, filters)
    }

    private fun buildPrompt(filters: FilterParams, currentDate: String): String {
        val tema = if (filters.areaTematica.isNotBlank() && !filters.areaTematica.contains("Todas")) {
            "${filters.areaTematica} ${filters.termoBusca}".trim()
        } else if (filters.termoBusca.isNotBlank()) {
            filters.termoBusca
        } else {
            "Saneamento, Infraestrutura, Saúde, Educação, Tecnologia e Cidades Inteligentes"
        }

        val ente = filters.tipoEnte.ifBlank { "Prefeituras Municipais e Consórcios Públicos" }
        val regiao = filters.estadoRegiao.ifBlank { "Nacional (Todos os Estados)" }

        return """
[PAPEL]
Você é um Rastreador e Analista Especialista em Captação de Recursos e Editais Públicos no Brasil, com acesso à busca na web em tempo real.

[CONTEXTO]
O usuário necessita de informações precisas, atualizadas e com prazos vigentes sobre editais, chamadas públicas e programas de financiamento de projetos destinados a entes públicos municipais, estaduais e consórcios públicos no território brasileiro (ex.: Transferegov, BNDES, FINEP, Ministérios, Fundações Estaduais de Amparo à Pesquisa/FAPs e organismos internacionais atuando no Brasil).

[OBJETIVO]
Buscar ativamente na web editais de financiamento com inscrições abertas ou futuras voltados ao setor público, extrair seus requisitos críticos e fornecer links oficiais diretos e funcionais para acesso à íntegra do edital.

[TAREFA]
1. Realizar busca na web utilizando termos estratégicos relacionados a: $tema, considerando o público-alvo: $ente e região: $regiao.
2. Filtrar apenas editais vigentes (com prazo de submissão não expirado na data de hoje $currentDate).
3. Extrair as informações estruturadas de cada oportunidade encontrada.
4. Validar que a URL coletada seja a página oficial do órgão emissor ou da plataforma do edital.

[REGRAS E RESTRIÇÕES CRÍTICAS]
- ZERO ALUCINAÇÃO DE LINKS: Retorne única e exclusivamente URLs reais encontradas diretamente nos resultados da busca do Google Search. Jamais deduza, invente ou tente reconstruir URLs da memória.
- Se o link direto do documento PDF não estiver evidente, forneça a URL da página oficial de publicação do chamamento.
- Descarte resultados de editais cujos prazos de submissão já estejam encerrados na data da consulta ($currentDate).
- Se nenhuma oportunidade vigente for encontrada com os filtros fornecidos, retorne a lista vazia informando a ausência de chamadas abertas no momento.

[FORMATO DE SAÍDA]
Retorne a resposta estritamente no formato JSON abaixo, sem blocos de texto adicionais fora do JSON:

{
  "total_encontrados": 0,
  "data_consulta": "$currentDate",
  "editais": [
    {
      "id": "identificador_unico_ou_numero_edital",
      "orgao_emissor": "Nome da Instituição/Ministério",
      "titulo": "Título oficial da chamada pública",
      "resumo_objeto": "Descrição concisa (máximo 2 linhas) do que é financiado",
      "elegibilidade": "Quem pode propor (ex: Prefeituras, Consórcios, Estados)",
      "valor_total_ou_teto": "Ex: R$ 500.000,00 por proposta (ou 'Não informado')",
      "prazo_submissao": "DD/MM/AAAA até às HH:mm (ou 'Fluxo Contínuo')",
      "fonte_recurso": "Fundo Nacional, Repasse a fundo perdido, Financiamento, etc.",
      "url_oficial": "https://link-real-da-pagina-do-edital.gov.br"
    }
  ]
}

[ENTRADAS DO USUÁRIO]
- Área Temática: $tema
- Ente / Proponente: $ente
- Estado / Região de interesse: $regiao
""".trimIndent()
    }

    private fun parseGeminiResponse(rawResponse: String, currentDate: String, filters: FilterParams): SearchResult? {
        val rootObj = JSONObject(rawResponse)
        val candidates = rootObj.optJSONArray("candidates") ?: return null
        if (candidates.length() == 0) return null

        val firstCandidate = candidates.getJSONObject(0)
        val content = firstCandidate.optJSONObject("content") ?: return null
        val parts = content.optJSONArray("parts") ?: return null
        if (parts.length() == 0) return null

        val text = parts.getJSONObject(0).optString("text", "")
        if (text.isBlank()) return null

        // Extract sources if grounding metadata exists
        val sources = mutableListOf<String>()
        val groundingMetadata = firstCandidate.optJSONObject("groundingMetadata")
        val webSearchQueries = groundingMetadata?.optJSONArray("webSearchQueries")
        if (webSearchQueries != null) {
            for (i in 0 until webSearchQueries.length()) {
                sources.add(webSearchQueries.optString(i))
            }
        }

        // Clean markdown backticks ```json ... ```
        val jsonCleaned = cleanJsonMarkdown(text)
        return try {
            val jsonObject = JSONObject(jsonCleaned)
            val total = jsonObject.optInt("total_encontrados", 0)
            val dataConsulta = jsonObject.optString("data_consulta", currentDate)
            val editaisJsonArray = jsonObject.optJSONArray("editais") ?: JSONArray()

            val editaisList = mutableListOf<EditalPublico>()
            for (i in 0 until editaisJsonArray.length()) {
                val item = editaisJsonArray.getJSONObject(i)
                val edital = EditalPublico(
                    id = item.optString("id", "ED-${i + 1}"),
                    orgaoEmissor = item.optString("orgao_emissor", "Órgão Público"),
                    titulo = item.optString("titulo", "Chamada Pública"),
                    resumoObjeto = item.optString("resumo_objeto", ""),
                    elegibilidade = item.optString("elegibilidade", filters.tipoEnte),
                    valorTotalOuTeto = item.optString("valor_total_ou_teto", "Não informado"),
                    prazoSubmissao = item.optString("prazo_submissao", "Vigente"),
                    fonteRecurso = item.optString("fonte_recurso", "Transferência da União"),
                    urlOficial = sanitizeUrl(item.optString("url_oficial", "https://www.gov.br/transferegov/pt-br")),
                    areaTematica = filters.areaTematica,
                    estadoOuRegiao = filters.estadoRegiao,
                    tipoEnte = filters.tipoEnte,
                    dataConsulta = dataConsulta
                )
                editaisList.add(edital)
            }

            SearchResult(
                totalEncontrados = editaisList.size,
                dataConsulta = dataConsulta,
                editais = editaisList,
                isLiveWeb = true,
                fontesConsultadas = sources,
                rawJson = jsonCleaned
            )
        } catch (e: Exception) {
            Log.e("GeminiWebSearch", "Error parsing JSON from text: ${e.message}", e)
            null
        }
    }

    private fun cleanJsonMarkdown(raw: String): String {
        var trimmed = raw.trim()
        if (trimmed.startsWith("```")) {
            val firstLineBreak = trimmed.indexOf('\n')
            if (firstLineBreak != -1) {
                trimmed = trimmed.substring(firstLineBreak + 1)
            }
            if (trimmed.endsWith("```")) {
                trimmed = trimmed.substring(0, trimmed.length - 3)
            }
        }
        return trimmed.trim()
    }

    private fun sanitizeUrl(url: String): String {
        val trimmed = url.trim()
        return if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            trimmed
        } else {
            "https://$trimmed"
        }
    }

    private fun getVerifiedFallbackResult(filters: FilterParams, currentDate: String): SearchResult {
        var baseList = RealPortalSources.EDITAIS_VERIFICADOS_BASE

        // Filter by Area if not "Todas"
        if (filters.areaTematica.isNotBlank() && !filters.areaTematica.contains("Todas")) {
            baseList = baseList.filter {
                it.areaTematica.contains(filters.areaTematica, ignoreCase = true) ||
                filters.areaTematica.contains(it.areaTematica, ignoreCase = true) ||
                it.resumoObjeto.contains(filters.areaTematica, ignoreCase = true) ||
                it.titulo.contains(filters.areaTematica, ignoreCase = true)
            }.ifEmpty { RealPortalSources.EDITAIS_VERIFICADOS_BASE }
        }

        // Filter by Termo de Busca
        if (filters.termoBusca.isNotBlank()) {
            val termo = filters.termoBusca.lowercase(Locale.getDefault())
            val filtered = baseList.filter {
                it.titulo.lowercase(Locale.getDefault()).contains(termo) ||
                it.resumoObjeto.lowercase(Locale.getDefault()).contains(termo) ||
                it.orgaoEmissor.lowercase(Locale.getDefault()).contains(termo) ||
                it.fonteRecurso.lowercase(Locale.getDefault()).contains(termo)
            }
            if (filtered.isNotEmpty()) {
                baseList = filtered
            }
        }

        // Filter by Ente
        if (filters.tipoEnte.contains("Consórcio", ignoreCase = true)) {
            val consorcios = baseList.filter {
                it.elegibilidade.contains("Consórcio", ignoreCase = true) ||
                it.tipoEnte.contains("Consórcio", ignoreCase = true)
            }
            if (consorcios.isNotEmpty()) baseList = consorcios
        }

        val jsonOutput = buildJsonOutput(baseList, currentDate)

        return SearchResult(
            totalEncontrados = baseList.size,
            dataConsulta = currentDate,
            editais = baseList.map { it.copy(dataConsulta = currentDate) },
            isLiveWeb = false,
            fontesConsultadas = listOf("Transferegov.br", "BNDES Setor Público", "FINEP / MCTI", "FNS", "Novo PAC"),
            rawJson = jsonOutput
        )
    }

    private fun buildJsonOutput(editais: List<EditalPublico>, currentDate: String): String {
        val root = JSONObject()
        root.put("total_encontrados", editais.size)
        root.put("data_consulta", currentDate)
        val array = JSONArray()
        for (e in editais) {
            val item = JSONObject()
            item.put("id", e.id)
            item.put("orgao_emissor", e.orgaoEmissor)
            item.put("titulo", e.titulo)
            item.put("resumo_objeto", e.resumoObjeto)
            item.put("elegibilidade", e.elegibilidade)
            item.put("valor_total_ou_teto", e.valorTotalOuTeto)
            item.put("prazo_submissao", e.prazoSubmissao)
            item.put("fonte_recurso", e.fonteRecurso)
            item.put("url_oficial", e.urlOficial)
            array.put(item)
        }
        root.put("editais", array)
        return root.toString(2)
    }
}
