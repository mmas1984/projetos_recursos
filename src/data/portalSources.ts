import { Edital } from '../types';

export interface PortalInfo {
  nome: string;
  sigla: string;
  descricao: string;
  url_oficial: string;
  categoria: string;
  como_inscrever: string;
}

export const PORTAIS_OFICIAIS: PortalInfo[] = [
  {
    nome: "Transferegov.br (Plataforma Mais Brasil)",
    sigla: "TRANSFEREGOV",
    descricao: "Principal portal do Governo Federal para transferências voluntárias da União, emendas parlamentares e convênios para Estados e Municípios.",
    url_oficial: "https://www.gov.br/transferegov/pt-br",
    categoria: "Federal / Convênios",
    como_inscrever: "Acesso via SouGov/Certificado Digital; cadastramento de propostas em programas abertos pelos Ministérios concedentes."
  },
  {
    nome: "BNDES - Financiamento e Apoio ao Setor Público",
    sigla: "BNDES",
    descricao: "Linhas de crédito reembolsáveis e não-reembolsáveis (Fundo Clima, Fundo Amazônia) para modernização municipal, iluminação, resíduos e saneamento.",
    url_oficial: "https://www.bndes.gov.br/wps/portal/site/home/financiamento/setor-publico",
    categoria: "Banco de Desenvolvimento",
    como_inscrever: "Submissão de Carta-Consulta via BNDES Online ou instituições financeiras credenciadas."
  },
  {
    nome: "FINEP - Chamadas Públicas & FNDCT",
    sigla: "FINEP",
    descricao: "Financiadora de Estudos e Projetos vinculada ao MCTI. Chamadas públicas para parques tecnológicos, cidades inteligentes, transição energética e inovação pública.",
    url_oficial: "http://www.finep.gov.br/chamadas-publicas",
    categoria: "Ciência & Tecnologia",
    como_inscrever: "Submissão de formulário eletrônico (FAP) na Plataforma de Chamadas da Finep."
  },
  {
    nome: "Fundo Nacional de Saúde (FNS)",
    sigla: "FNS / MS",
    descricao: "Repasses Fundo a Fundo e convênios do Ministério da Saúde para aquisição de ambulâncias SAMU, construção de UBS, reformas e custeio MAC.",
    url_oficial: "https://portalfns.saude.gov.br/",
    categoria: "Saúde Pública",
    como_inscrever: "Cadastramento de proposta de aquisição de equipamentos e obras no InvestSUS Cidadão / Gestor."
  },
  {
    nome: "Novo PAC Seleções (Casa Civil / Ministérios)",
    sigla: "NOVO PAC",
    descricao: "Programa de aceleração do crescimento com seleções abertas para creches, escolas integrais, policlínicas, esgotamento sanitário e urbanização.",
    url_oficial: "https://www.gov.br/casacivil/pt-br/novopac/selecoes",
    categoria: "Infraestrutura & Obras",
    como_inscrever: "Inscrição pelo Transferegov.br dentro dos editais específicos do Novo PAC Seleções."
  },
  {
    nome: "Fundo Nacional sobre Mudança do Clima (Fundo Clima)",
    sigla: "FUNDO CLIMA",
    descricao: "Recursos para projetos municipais de mitigação e adaptação à mudança do clima, drenagem urbana sustentável, descarbonização da frota e resíduos.",
    url_oficial: "https://www.gov.br/mma/pt-br/assuntos/mudanca-do-clima/fundo-clima",
    categoria: "Meio Ambiente",
    como_inscrever: "Projetos submetidos aos agentes operadores (BNDES para reembolsável; MMA para não-reembolsável)."
  },
  {
    nome: "CONFAP - Conselho Nacional das FAPs",
    sigla: "CONFAP / FAPs",
    descricao: "Rede das Fundações de Amparo à Pesquisa dos 26 Estados e DF (FAPESP, FAPEMIG, FAPERJ, FAPESC, etc.) com editais conjuntos para inovação no setor público.",
    url_oficial: "https://confap.org.br/pt/",
    categoria: "Pesquisa Estadual",
    como_inscrever: "Acesso ao portal da Fundação de Amparo à Pesquisa do respectivo Estado da federação."
  },
  {
    nome: "Ministério das Cidades (MCID)",
    sigla: "MCID",
    descricao: "Programas de saneamento básico, habitação de interesse social (Minha Casa Minha Vida), mobilidade urbana e contenção de encostas.",
    url_oficial: "https://www.gov.br/cidades/pt-br/acesso-a-informacao/acoes-e-programas",
    categoria: "Desenvolvimento Urbano",
    como_inscrever: "Via Transferegov com projetos de engenharia validados pela Caixa Econômica Federal."
  }
];

export const EDITAIS_VERIFICADOS_BASE: Edital[] = [
  {
    id: "FINEP-CIDADES-INTELIGENTES-2025-2026",
    orgao_emissor: "FINEP / MCTI",
    titulo: "Chamada Pública Finep Mais Inovação - Cidades Inteligentes e Sustentáveis",
    resumo_objeto: "Apoio a projetos de infraestrutura tecnológica, internet das coisas (IoT), telemetria urbana, transição energética e modernização de serviços públicos municipais.",
    elegibilidade: "Prefeituras Municipais, Consórcios Públicos Intermunicipais e Entidades de Direito Público",
    valor_total_ou_teto: "R$ 1.500.000,00 a R$ 8.000.000,00 por proposta (FNDCT Não Reembolsável)",
    prazo_submissao: "Fluxo Contínuo / Janelas semestrais até 18:00",
    fonte_recurso: "Fundo Nacional de Desenvolvimento Científico e Tecnológico (FNDCT) - A Fundo Perdido",
    url_oficial: "http://www.finep.gov.br/chamadas-publicas",
    area_tematica: "Tecnologia e Inovação",
    estado_regiao: "Nacional"
  },
  {
    id: "BNDES-FUNDO-CLIMA-CIDADES-VIGENTE",
    orgao_emissor: "BNDES / Ministério do Meio Ambiente",
    titulo: "Programa Fundo Clima - Eixo Cidades Resilientes e Desenvolvimento Urbano Sustentável",
    resumo_objeto: "Financiamento com juros altamente subsidiados para gestão integrada de resíduos sólidos, drenagem pluvial urbana, contenção de encostas e eletromobilidade no transporte coletivo.",
    elegibilidade: "Municípios, Estados, Consórcios Públicos e Concessionárias Públicas",
    valor_total_ou_teto: "Até R$ 30.000.000,00 por operação (taxas a partir de TR + 1% a.a.)",
    prazo_submissao: "Fluxo Contínuo até esgotamento de dotação",
    fonte_recurso: "Fundo Clima / BNDES Finem Setor Público",
    url_oficial: "https://www.bndes.gov.br/wps/portal/site/home/financiamento/produto/fundo-clima",
    area_tematica: "Meio Ambiente e Clima",
    estado_regiao: "Nacional"
  },
  {
    id: "MS-FNS-EQUIPAMENTOS-UBS-SAMU",
    orgao_emissor: "Ministério da Saúde / Fundo Nacional de Saúde",
    titulo: "Programa de Estruturação da Rede de Serviços de Atenção Primária e Urgência (InvestSUS)",
    resumo_objeto: "Repasse de recursos para aquisição de veículos de transporte sanitário eletivo, ambulâncias tipo A e B para SAMU 192 e kits odontológicos/médicos para UBS.",
    elegibilidade: "Secretarias Municipais de Saúde e Fundos Municipais de Saúde com cadastro regular",
    valor_total_ou_teto: "R$ 350.000,00 a R$ 1.200.000,00 por proposta conforme porte populacional",
    prazo_submissao: "Janela Anual de Propostas até 30/11 até às 23:59",
    fonte_recurso: "Orçamento Geral da União - Repasse Fundo a Fundo FNS (Não reembolsável)",
    url_oficial: "https://portalfns.saude.gov.br/",
    area_tematica: "Saúde",
    estado_regiao: "Nacional"
  },
  {
    id: "TRANSFEREGOV-MCID-DRENAGEM-ENCOSTAS",
    orgao_emissor: "Ministério das Cidades",
    titulo: "Apoio a Obras de Prevenção a Desastres Naturais, Macrodrenagem e Contenção Geotécnica",
    resumo_objeto: "Financiamento a fundo perdido para obras prioritárias de contenção de encostas de alto risco geológico e obras de macrodrenagem urbana em áreas vulneráveis.",
    elegibilidade: "Prefeituras Municipais com áreas de risco mapeadas (CPRM/SGB) e Consórcios",
    valor_total_ou_teto: "Até R$ 15.000.000,00 por município (Sem exigência de contrapartida para municípios até 50k hab)",
    prazo_submissao: "Cadastro aberto via Transferegov com análise técnica contínua",
    fonte_recurso: "Novo PAC / OGU - Transferência Obrigatória e Discricionária",
    url_oficial: "https://www.gov.br/cidades/pt-br/acesso-a-informacao/acoes-e-programas",
    area_tematica: "Infraestrutura e Obras",
    estado_regiao: "Nacional"
  },
  {
    id: "MEC-FNDE-CRECHES-ESCOLAS-INTEGRAL",
    orgao_emissor: "Ministério da Educação / FNDE",
    titulo: "Programa Nacional de Reestruturação da Educação Básica e Creches - PAR / Transferegov",
    resumo_objeto: "Repasse de recursos para construção de creches tipo 1 e 2, escolas de tempo integral e aquisição de ônibus escolares rurais (Caminho da Escola).",
    elegibilidade: "Prefeituras Municipais e Secretarias de Educação que preencheram o Plano de Ações Articuladas (PAR)",
    valor_total_ou_teto: "R$ 4.500.000,00 por unidade escolar (Repasse direto via FNDE)",
    prazo_submissao: "Adesão e validação contínua no SIMEC/PAR",
    fonte_recurso: "Fundo Nacional de Desenvolvimento da Educação (FNDE)",
    url_oficial: "https://www.gov.br/fnde/pt-br",
    area_tematica: "Educação",
    estado_regiao: "Nacional"
  },
  {
    id: "FUNASA-SANEAMENTO-RURAL-RESIDUOS",
    orgao_emissor: "Fundação Nacional de Saúde (FUNASA / Ministério das Cidades)",
    titulo: "Melhorias Sanitárias Domiciliares e Sistemas Singelos de Abastecimento de Água Rural",
    resumo_objeto: "Implantação de módulos sanitários domiciliares, poços artesianos e pequenas estações de tratamento de água em comunidades rurais e quilombolas.",
    elegibilidade: "Municípios de até 50.000 habitantes e Consórcios Públicos de Saneamento",
    valor_total_ou_teto: "R$ 600.000,00 a R$ 2.500.000,00 a fundo perdido",
    prazo_submissao: "Conforme cronograma de abertura do Transferegov",
    fonte_recurso: "Repasse Orçamentário Federal - FUNASA",
    url_oficial: "https://www.gov.br/transferegov/pt-br",
    area_tematica: "Saneamento Básico",
    estado_regiao: "Nacional"
  },
  {
    id: "BNDES-MODERNA-ILUMINACAO-PPP",
    orgao_emissor: "BNDES Estruturação de Parcerias",
    titulo: "Fábrica de Projetos BNDES - Estruturação de PPPs de Iluminação Pública e Resíduos Sólidos",
    resumo_objeto: "Apoio técnico e financiamento de estudos preliminares para consórcios públicos intermunicipais modelarem concessões e parcerias público-privadas de iluminação LED e aterros sanitários.",
    elegibilidade: "Consórcios Públicos Intermunicipais formalmente constituídos (Lei nº 11.107/2005)",
    valor_total_ou_teto: "Custeio 100% dos estudos de modelagem ressarcidos pelo concessionário vencedor",
    prazo_submissao: "Fluxo Contínuo de manifestação de interesse",
    fonte_recurso: "BNDES FEP (Fundo de Estruturação de Parcerias)",
    url_oficial: "https://hubdeprojetos.bndes.gov.br/",
    area_tematica: "Consórcios e PPPs",
    estado_regiao: "Nacional"
  },
  {
    id: "MINC-POLITICA-ALDIR-BLANC-2",
    orgao_emissor: "Ministério da Cultura (MinC)",
    titulo: "Política Nacional Aldir Blanc de Fomento à Cultura (PNAB) - Ciclo Anual de Execução",
    resumo_objeto: "Repasse de recursos para planos de ação municipais e estaduais para editais culturais locais, subsídios a espaços culturais e fomento a pontos de memória.",
    elegibilidade: "Municípios, Estados e DF que registraram seus Planos de Ação na Plataforma Transferegov",
    valor_total_ou_teto: "R$ 3 bilhões anuais partilhados proporcionalmente aos entes federados",
    prazo_submissao: "Execução orçamentária anual obrigatória pelos municípios",
    fonte_recurso: "Fundo Nacional de Cultura (FNC) / Lei nº 14.399/2022",
    url_oficial: "https://www.gov.br/cultura/pt-br/assuntos/pnab",
    area_tematica: "Cultura e Turismo",
    estado_regiao: "Nacional"
  },
  {
    id: "FAPESP-INOVACAO-SETOR-PUBLICO-PITE",
    orgao_emissor: "FAPESP (São Paulo)",
    titulo: "Programa de Pesquisa em Políticas Públicas (PPPP) - Parcerias Municípios e Pesquisadores",
    resumo_objeto: "Financiamento de soluções científicas e diagnósticos aplicados para secretarias de educação, saúde, segurança e trânsito em parceria com universidades.",
    elegibilidade: "Órgãos do Governo do Estado de SP e Prefeituras Paulistas conveniadas",
    valor_total_ou_teto: "Até R$ 500.000,00 por projeto de pesquisa aplicada",
    prazo_submissao: "Chamadas periódicas ao longo do ano",
    fonte_recurso: "Fundação de Amparo à Pesquisa do Estado de SP (FAPESP)",
    url_oficial: "https://fapesp.br/politicaspublicas",
    area_tematica: "Tecnologia e Inovação",
    estado_regiao: "São Paulo (SP)"
  }
];

export interface CaucItem {
  id: string;
  grupo: string;
  titulo: string;
  descricao: string;
  orgao_validador: string;
}

export const CAUC_ITEMS: CaucItem[] = [
  { id: "1.1", grupo: "Grupo I - Obrigações Financeiras", titulo: "Tributos Federais e Dívida Ativa da União", descricao: "Certidão Conjunta Negativa da Receita Federal e PGFN.", orgao_validador: "Receita Federal / PGFN" },
  { id: "1.2", grupo: "Grupo I - Obrigações Financeiras", titulo: "Contribuições Previdenciárias", descricao: "Regularidade das contribuições ao Regime Geral ou Próprio de Previdência (RPPS).", orgao_validador: "RFB / MPS" },
  { id: "1.3", grupo: "Grupo I - Obrigações Financeiras", titulo: "Regularidade perante o FGTS (CRF)", descricao: "Certificado de Regularidade do FGTS emitido pela Caixa.", orgao_validador: "Caixa Econômica" },
  { id: "1.4", grupo: "Grupo I - Obrigações Financeiras", titulo: "CADIN - Cadastro Informativo de Créditos", descricao: "Ausência de inscrições restritivas no CADIN Federal.", orgao_validador: "Banco Central / STN" },
  { id: "2.1", grupo: "Grupo II - Prestação de Contas", titulo: "Prestação de Contas de Convênios da União", descricao: "Inexistência de inadimplência em convênios anteriores no Transferegov.", orgao_validador: "Ministérios Concedentes" },
  { id: "3.1", grupo: "Grupo III - Transparência e LRF", titulo: "Relatório de Gestão Fiscal (RGF)", descricao: "Homologação do RGF tempestivo no Siconfi/Tesouro Nacional.", orgao_validador: "Secretaria do Tesouro Nacional" },
  { id: "3.2", grupo: "Grupo III - Transparência e LRF", titulo: "Relatório Resumido da Execução Orçamentária (RREO)", descricao: "Publicação bimestral no Siconfi.", orgao_validador: "Secretaria do Tesouro Nacional" },
  { id: "3.3", grupo: "Grupo III - Transparência e LRF", titulo: "Limite de Despesa com Pessoal", descricao: "Cumprimento do limite máximo de 54% (Executivo) previsto na LRF.", orgao_validador: "Tribunais de Contas / TCE" },
  { id: "3.4", grupo: "Grupo III - Transparência e LRF", titulo: "Limite da Dívida Consolidada Líquida", descricao: "Não ultrapassar os limites fixados pelo Senado Federal.", orgao_validador: "STN" },
  { id: "4.1", grupo: "Grupo IV - Gastos Mínimos Constitucionais", titulo: "Aplicação Mínima em Ações e Serviços de Saúde", descricao: "Aplicação de no mínimo 15% das receitas de impostos no SIOPS.", orgao_validador: "Ministério da Saúde / SIOPS" },
  { id: "4.2", grupo: "Grupo IV - Gastos Mínimos Constitucionais", titulo: "Aplicação Mínima no Ensino (Educação)", descricao: "Aplicação de no mínimo 25% no SIOPE/FNDE.", orgao_validador: "FNDE / MEC" }
];
