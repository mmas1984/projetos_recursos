import { Edital, FilterParams, SearchResult } from '../types';
import { EDITAIS_VERIFICADOS_BASE } from '../data/portalSources';

export async function searchEditais(filters: FilterParams, customApiKey?: string): Promise<SearchResult> {
  const currentDate = new Date().toISOString().split('T')[0];
  
  // Check if API key is provided
  const apiKey = customApiKey || 
    (typeof import.meta !== 'undefined' && (import.meta as any).env?.VITE_GEMINI_API_KEY) || 
    '';

  if (apiKey && apiKey !== 'MY_GEMINI_API_KEY' && apiKey.length > 10) {
    try {
      const liveResult = await executeLiveGeminiSearch(apiKey, filters, currentDate);
      if (liveResult && liveResult.editais.length > 0) {
        return liveResult;
      }
    } catch (err) {
      console.warn("Live Gemini search failed, falling back to verified catalog:", err);
    }
  }

  // Fallback to verified catalog filtered by params
  return getVerifiedCatalogResult(filters, currentDate);
}

async function executeLiveGeminiSearch(apiKey: string, filters: FilterParams, currentDate: string): Promise<SearchResult | null> {
  const tema = filters.area_tematica !== "Todas as Áreas" 
    ? `${filters.area_tematica} ${filters.termo_busca}`.trim() 
    : (filters.termo_busca || "Saneamento, Infraestrutura, Saúde, Educação, Tecnologia e Inovação");
  
  const ente = filters.tipo_ente || "Prefeituras Municipais e Consórcios Públicos";
  const regiao = filters.estado_regiao || "Nacional (Todos os Estados)";

  const prompt = `
[PAPEL]
Você é um Rastreador e Analista Especialista em Captação de Recursos e Editais Públicos no Brasil, com acesso à busca na web em tempo real.

[CONTEXTO]
O usuário necessita de informações precisas, atualizadas e com prazos vigentes sobre editais, chamadas públicas e programas de financiamento de projetos destinados a entes públicos municipais, estaduais e consórcios públicos no território brasileiro (ex.: Transferegov, BNDES, FINEP, Ministérios, Fundações Estaduais de Amparo à Pesquisa/FAPs e organismos internacionais atuando no Brasil).

[OBJETIVO]
Buscar ativamente na web editais de financiamento com inscrições abertas ou futuras voltados ao setor público, extrair seus requisitos críticos e fornecer links oficiais diretos e funcionais para acesso à íntegra do edital.

[TAREFA]
1. Realizar busca na web utilizando termos estratégicos relacionados a: ${tema}, considerando o público-alvo: ${ente} e região: ${regiao}.
2. Filtrar apenas editais vigentes (com prazo de submissão não expirado na data de hoje ${currentDate}).
3. Extrair as informações estruturadas de cada oportunidade encontrada.
4. Validar que a URL coletada seja a página oficial do órgão emissor ou da plataforma do edital.

[REGRAS E RESTRIÇÕES CRÍTICAS]
- ZERO ALUCINAÇÃO DE LINKS: Retorne única e exclusivamente URLs reais encontradas diretamente nos resultados da busca do Google Search. Jamais deduza, invente ou tente reconstruir URLs da memória.
- Se o link direto do documento PDF não estiver evidente, forneça a URL da página oficial de publicação do chamamento.
- Descarte resultados de editais cujos prazos de submissão já estejam encerrados na data da consulta (${currentDate}).
- Se nenhuma oportunidade vigente for encontrada com os filtros fornecidos, retorne a lista vazia informando a ausência de chamadas abertas no momento.

[FORMATO DE SAÍDA]
Retorne a resposta estritamente no formato JSON abaixo, sem blocos de texto adicionais fora do JSON:

{
  "total_encontrados": 0,
  "data_consulta": "${currentDate}",
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
- Área Temática: ${tema}
- Ente / Proponente: ${ente}
- Estado / Região de interesse: ${regiao}
`;

  const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=${apiKey}`;

  const response = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      contents: [{ role: "user", parts: [{ text: prompt }] }],
      tools: [{ googleSearch: {} }],
      generationConfig: {
        temperature: 0.1,
        topP: 0.95
      }
    })
  });

  if (!response.ok) return null;

  const data = await response.json();
  const candidate = data.candidates?.[0];
  const text = candidate?.content?.parts?.[0]?.text;
  if (!text) return null;

  // Extract grounding sources if any
  const sources: string[] = [];
  const webQueries = candidate?.groundingMetadata?.webSearchQueries;
  if (Array.isArray(webQueries)) {
    sources.push(...webQueries);
  }

  // Parse JSON from text
  const cleanJson = cleanMarkdown(text);
  const parsed = JSON.parse(cleanJson);

  return {
    total_encontrados: parsed.editais?.length || 0,
    data_consulta: parsed.data_consulta || currentDate,
    editais: parsed.editais || [],
    is_live_web: true,
    fontes_consultadas: sources.length > 0 ? sources : ["Google Search (Grounding em Tempo Real)"],
    raw_json: JSON.stringify(parsed, null, 2)
  };
}

function cleanMarkdown(text: string): string {
  let cleaned = text.trim();
  if (cleaned.startsWith('```')) {
    const firstNewline = cleaned.indexOf('\n');
    if (firstNewline !== -1) {
      cleaned = cleaned.substring(firstNewline + 1);
    }
    if (cleaned.endsWith('```')) {
      cleaned = cleaned.substring(0, cleaned.length - 3);
    }
  }
  return cleaned.trim();
}

function getVerifiedCatalogResult(filters: FilterParams, currentDate: string): SearchResult {
  let list = [...EDITAIS_VERIFICADOS_BASE];

  // Filter by Area if not "Todas as Áreas"
  if (filters.area_tematica && !filters.area_tematica.includes("Todas")) {
    list = list.filter(item => 
      item.area_tematica?.toLowerCase().includes(filters.area_tematica.toLowerCase()) ||
      filters.area_tematica.toLowerCase().includes(item.area_tematica?.toLowerCase() || '') ||
      item.resumo_objeto.toLowerCase().includes(filters.area_tematica.toLowerCase()) ||
      item.titulo.toLowerCase().includes(filters.area_tematica.toLowerCase())
    );
    if (list.length === 0) list = [...EDITAIS_VERIFICADOS_BASE];
  }

  // Filter by search term
  if (filters.termo_busca.trim()) {
    const term = filters.termo_busca.toLowerCase().trim();
    const filtered = list.filter(item =>
      item.titulo.toLowerCase().includes(term) ||
      item.resumo_objeto.toLowerCase().includes(term) ||
      item.orgao_emissor.toLowerCase().includes(term) ||
      item.fonte_recurso.toLowerCase().includes(term)
    );
    if (filtered.length > 0) list = filtered;
  }

  // Filter by Ente
  if (filters.tipo_ente.includes("Consórcio")) {
    const consorcios = list.filter(item => 
      item.elegibilidade.toLowerCase().includes("consórcio") ||
      item.resumo_objeto.toLowerCase().includes("consórcio")
    );
    if (consorcios.length > 0) list = consorcios;
  }

  const rawJson = JSON.stringify({
    total_encontrados: list.length,
    data_consulta: currentDate,
    editais: list
  }, null, 2);

  return {
    total_encontrados: list.length,
    data_consulta: currentDate,
    editais: list,
    is_live_web: false,
    fontes_consultadas: ["Transferegov.br", "BNDES Setor Público", "FINEP / MCTI", "FNS", "Novo PAC Seleções"],
    raw_json: rawJson
  };
}
