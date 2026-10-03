export interface Edital {
  id: string;
  orgao_emissor: string;
  titulo: string;
  resumo_objeto: string;
  elegibilidade: string;
  valor_total_ou_teto: string;
  prazo_submissao: string;
  fonte_recurso: string;
  url_oficial: string;
  // Extra fields for app usage
  area_tematica?: string;
  estado_regiao?: string;
  anotacoes?: string;
  saved?: boolean;
}

export interface SearchResult {
  total_encontrados: number;
  data_consulta: string;
  editais: Edital[];
  is_live_web?: boolean;
  fontes_consultadas?: string[];
  raw_json?: string;
}

export interface FilterParams {
  area_tematica: string;
  tipo_ente: string;
  estado_regiao: string;
  termo_busca: string;
}
