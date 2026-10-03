package com.example.model

/**
 * Representa um edital público ou chamada de financiamento
 * em conformidade com o formato estruturado do Rastreador de Captação de Recursos.
 */
data class EditalPublico(
    val id: String,
    val orgaoEmissor: String,
    val titulo: String,
    val resumoObjeto: String,
    val elegibilidade: String,
    val valorTotalOuTeto: String,
    val prazoSubmissao: String,
    val fonteRecurso: String,
    val urlOficial: String,
    // Metadados adicionais para uso no app
    val areaTematica: String = "",
    val estadoOuRegiao: String = "Nacional",
    val tipoEnte: String = "",
    val isSaved: Boolean = false,
    val anotacoes: String = "",
    val diasRestantes: Int? = null,
    val dataConsulta: String = ""
) {
    /**
     * Retorna a representação estrita em JSON de acordo com o padrão exigido
     */
    fun toJsonString(): String {
        return """
        {
          "id": "${id.replace("\"", "\\\"")}",
          "orgao_emissor": "${orgaoEmissor.replace("\"", "\\\"")}",
          "titulo": "${titulo.replace("\"", "\\\"")}",
          "resumo_objeto": "${resumoObjeto.replace("\"", "\\\"")}",
          "elegibilidade": "${elegibilidade.replace("\"", "\\\"")}",
          "valor_total_ou_teto": "${valorTotalOuTeto.replace("\"", "\\\"")}",
          "prazo_submissao": "${prazoSubmissao.replace("\"", "\\\"")}",
          "fonte_recurso": "${fonteRecurso.replace("\"", "\\\"")}",
          "url_oficial": "${urlOficial.replace("\"", "\\\"")}"
        }
        """.trimIndent()
    }

    val isFluxoContinuo: Boolean
        get() = prazoSubmissao.contains("Fluxo Contínuo", ignoreCase = true) ||
                prazoSubmissao.contains("Fluxo Continuo", ignoreCase = true) ||
                prazoSubmissao.contains("Contínuo", ignoreCase = true)
}

data class SearchResult(
    val totalEncontrados: Int,
    val dataConsulta: String,
    val editais: List<EditalPublico>,
    val isLiveWeb: Boolean = false,
    val fontesConsultadas: List<String> = emptyList(),
    val rawJson: String = ""
)

data class FilterParams(
    val areaTematica: String = "Todas as Áreas",
    val tipoEnte: String = "Prefeituras e Municípios",
    val estadoRegiao: String = "Nacional (Todos os Estados)",
    val termoBusca: String = "",
    val apenasNaoReembolsavel: Boolean = false
)
