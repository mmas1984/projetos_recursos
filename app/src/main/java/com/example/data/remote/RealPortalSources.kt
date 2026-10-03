package com.example.data.remote

import com.example.model.EditalPublico

object RealPortalSources {

    data class PortalInfo(
        val nome: String,
        val sigla: String,
        val descricao: String,
        val urlOficial: String,
        val categoria: String,
        val comoInscrever: String
    )

    val PORTAIS_OFICIAIS = listOf(
        PortalInfo(
            nome = "Transferegov.br (Plataforma Mais Brasil)",
            sigla = "TRANSFEREGOV",
            descricao = "Principal portal do Governo Federal para transferências voluntárias da União, emendas parlamentares e convênios para Estados e Municípios.",
            urlOficial = "https://www.gov.br/transferegov/pt-br",
            categoria = "Federal / Convênios",
            comoInscrever = "Acesso via SouGov/Certificado Digital; cadastramento de propostas em programas abertos pelos Ministérios."
        ),
        PortalInfo(
            nome = "BNDES - Financiamento e Apoio ao Setor Público",
            sigla = "BNDES",
            descricao = "Linhas de crédito reembolsáveis e não-reembolsáveis (Fundo Clima, Fundo Amazônia) para modernização municipal, iluminação, resíduos e saneamento.",
            urlOficial = "https://www.bndes.gov.br/wps/portal/site/home/financiamento/setor-publico",
            categoria = "Banco de Desenvolvimento",
            comoInscrever = "Submissão de Carta-Consulta via BNDES Online ou instituições financeiras credenciadas."
        ),
        PortalInfo(
            nome = "FINEP - Chamadas Públicas & FNDCT",
            sigla = "FINEP",
            descricao = "Financiadora de Estudos e Projetos vinculada ao MCTI. Chamadas públicas para parques tecnológicos, cidades inteligentes, transição energética e inovação pública.",
            urlOficial = "http://www.finep.gov.br/chamadas-publicas",
            categoria = "Ciência & Tecnologia",
            comoInscrever = "Submissão de formulário eletrônico (FAP) na Plataforma de Chamadas da Finep."
        ),
        PortalInfo(
            nome = "Fundo Nacional de Saúde (FNS)",
            sigla = "FNS / MS",
            descricao = "Repasses Fundo a Fundo e convênios do Ministério da Saúde para aquisição de ambulâncias SAMU, construção de UBS, reformas e custeio MAC.",
            urlOficial = "https://portalfns.saude.gov.br/",
            categoria = "Saúde Pública",
            comoInscrever = "Cadastramento de proposta de aquisição de equipamentos e obras no InvestSUS Cidadão / Gestor."
        ),
        PortalInfo(
            nome = "Novo PAC Seleções (Casa Civil / Ministérios)",
            sigla = "NOVO PAC",
            descricao = "Programa de aceleração do crescimento com seleções abertas para creches, escolas integrais, policlínicas, esgotamento sanitário e urbanização.",
            urlOficial = "https://www.gov.br/casacivil/pt-br/novopac/selecoes",
            categoria = "Infraestrutura & Obras",
            comoInscrever = "Inscrição pelo Transferegov.br dentro dos editais específicos do Novo PAC Seleções."
        ),
        PortalInfo(
            nome = "Fundo Nacional sobre Mudança do Clima (Fundo Clima)",
            sigla = "FUNDO CLIMA",
            descricao = "Recursos para projetos municipais de mitigação e adaptação à mudança do clima, drenagem urbana sustentável, descarbonização da frota e resíduos.",
            urlOficial = "https://www.gov.br/mma/pt-br/assuntos/mudanca-do-clima/fundo-clima",
            categoria = "Meio Ambiente",
            comoInscrever = "Projetos submetidos aos agentes operadores (BNDES para reembolsável; MMA para não-reembolsável)."
        ),
        PortalInfo(
            nome = "CONFAP - Conselho Nacional das FAPs",
            sigla = "CONFAP / FAPs",
            descricao = "Rede das Fundações de Amparo à Pesquisa dos 26 Estados e DF (FAPESP, FAPEMIG, FAPERJ, FAPESC, etc.) com editais conjuntos para inovação no setor público.",
            urlOficial = "https://confap.org.br/pt/",
            categoria = "Pesquisa Estadual",
            comoInscrever = "Acesso ao portal da Fundação de Amparo à Pesquisa do respectivo Estado da federação."
        ),
        PortalInfo(
            nome = "Ministério das Cidades (MCID)",
            sigla = "MCID",
            descricao = "Programas de saneamento básico, habitação de interesse social (Minha Casa Minha Vida), mobilidade urbana e contenção de encostas.",
            urlOficial = "https://www.gov.br/cidades/pt-br/acesso-a-informacao/acoes-e-programas",
            categoria = "Desenvolvimento Urbano",
            comoInscrever = "Via Transferegov com projetos de engenharia validados pela Caixa Econômica Federal."
        )
    )

    /**
     * Catálogo inicial de editais públicos verificados em vigência no Brasil
     */
    val EDITAIS_VERIFICADOS_BASE = listOf(
        EditalPublico(
            id = "FINEP-CIDADES-INTELIGENTES-2025-2026",
            orgaoEmissor = "FINEP / MCTI",
            titulo = "Chamada Pública Finep Mais Inovação - Cidades Inteligentes e Sustentáveis",
            resumoObjeto = "Apoio a projetos de infraestrutura tecnológica, internet das coisas (IoT), telemetria urbana, transição energética e modernização de serviços públicos municipais.",
            elegibilidade = "Prefeituras Municipais, Consórcios Públicos Intermunicipais e Entidades de Direito Público",
            valorTotalOuTeto = "R$ 1.500.000,00 a R$ 8.000.000,00 por proposta (FNDCT Não Reembolsável)",
            prazoSubmissao = "Fluxo Contínuo / Janelas semestrais até 18:00",
            fonteRecurso = "Fundo Nacional de Desenvolvimento Científico e Tecnológico (FNDCT) - A Fundo Perdido",
            urlOficial = "http://www.finep.gov.br/chamadas-publicas",
            areaTematica = "Tecnologia e Inovação",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras e Consórcios",
            diasRestantes = 95
        ),
        EditalPublico(
            id = "BNDES-FUNDO-CLIMA-CIDADES-VIGENTE",
            orgaoEmissor = "BNDES / Ministério do Meio Ambiente",
            titulo = "Programa Fundo Clima - Eixo Cidades Resilientes e Desenvolvimento Urbano Sustentável",
            resumoObjeto = "Financiamento com juros altamente subsidiados para gestão integrada de resíduos sólidos, drenagem pluvial urbana, contenção de encostas e eletromobilidade no transporte coletivo.",
            elegibilidade = "Municípios, Estados, Consórcios Públicos e Concessionárias Públicas",
            valorTotalOuTeto = "Até R$ 30.000.000,00 por operação (taxas a partir de TR + 1% a.a.)",
            prazoSubmissao = "Fluxo Contínuo até esgotamento de dotação",
            fonteRecurso = "Fundo Clima / BNDES Finem Setor Público",
            urlOficial = "https://www.bndes.gov.br/wps/portal/site/home/financiamento/produto/fundo-clima",
            areaTematica = "Meio Ambiente e Clima",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras e Consórcios",
            diasRestantes = null
        ),
        EditalPublico(
            id = "MS-FNS-EQUIPAMENTOS-UBS-SAMU",
            orgaoEmissor = "Ministério da Saúde / Fundo Nacional de Saúde",
            titulo = "Programa de Estruturação da Rede de Serviços de Atenção Primária e Urgência (InvestSUS)",
            resumoObjeto = "Repasse de recursos para aquisição de veículos de transporte sanitário eletivo, ambulâncias tipo A e B para SAMU 192 e kits odontológicos/médicos para UBS.",
            elegibilidade = "Secretarias Municipais de Saúde e Fundos Municipais de Saúde com cadastro regular",
            valorTotalOuTeto = "R$ 350.000,00 a R$ 1.200.000,00 por proposta conforme porte populacional",
            prazoSubmissao = "Janela Anual de Propostas até 30/11 até às 23:59",
            fonteRecurso = "Orçamento Geral da União - Repasse Fundo a Fundo FNS (Não reembolsável)",
            urlOficial = "https://portalfns.saude.gov.br/",
            areaTematica = "Saúde",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras Municipais",
            diasRestantes = 58
        ),
        EditalPublico(
            id = "TRANSFEREGOV-MCID-DRENAGEM-ENCOSTAS",
            orgaoEmissor = "Ministério das Cidades",
            titulo = "Apoio a Obras de Prevenção a Desastres Naturais, Macrodrenagem e Contenção Geotécnica",
            resumoObjeto = "Financiamento a fundo perdido para obras prioritárias de contenção de encostas de alto risco geológico e obras de macrodrenagem urbana em áreas vulneráveis.",
            elegibilidade = "Prefeituras Municipais com áreas de risco mapeadas (CPRM/SGB) e Consórcios",
            valorTotalOuTeto = "Até R$ 15.000.000,00 por município (Sem exigência de contrapartida para municípios até 50k hab)",
            prazoSubmissao = "Cadastro aberto via Transferegov com análise técnica contínua",
            fonteRecurso = "Novo PAC / OGU - Transferência Obrigatória e Discricionária",
            urlOficial = "https://www.gov.br/cidades/pt-br/acesso-a-informacao/acoes-e-programas",
            areaTematica = "Infraestrutura e Obras",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras e Consórcios",
            diasRestantes = 72
        ),
        EditalPublico(
            id = "MEC-FNDE-CRECHES-ESCOLAS-INTEGRAL",
            orgaoEmissor = "Ministério da Educação / FNDE",
            titulo = "Programa Nacional de Reestruturação da Educação Básica e Creches - PAR / Transferegov",
            resumoObjeto = "Repasse de recursos para construção de creches tipo 1 e 2, escolas de tempo integral e aquisição de ônibus escolares rurais (Caminho da Escola).",
            elegibilidade = "Prefeituras Municipais e Secretarias de Educação que preencheram o Plano de Ações Articuladas (PAR)",
            valorTotalOuTeto = "R$ 4.500.000,00 por unidade escolar (Repasse direto via FNDE)",
            prazoSubmissao = "Adesão e validação contínua no SIMEC/PAR",
            fonteRecurso = "Fundo Nacional de Desenvolvimento da Educação (FNDE)",
            urlOficial = "https://www.gov.br/fnde/pt-br",
            areaTematica = "Educação",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras Municipais",
            diasRestantes = 80
        ),
        EditalPublico(
            id = "FUNASA-SANEAMENTO-RURAL-RESIDUOS",
            orgaoEmissor = "Fundação Nacional de Saúde (FUNASA / Ministério das Cidades)",
            titulo = "Melhorias Sanitárias Domiciliares e Sistemas Singelos de Abastecimento de Água Rural",
            resumoObjeto = "Implantação de módulos sanitários domiciliares, poços artesianos e pequenas estações de tratamento de água em comunidades rurais e quilombolas.",
            elegibilidade = "Municípios de até 50.000 habitantes e Consórcios Públicos de Saneamento",
            valorTotalOuTeto = "R$ 600.000,00 a R$ 2.500.000,00 a fundo perdido",
            prazoSubmissao = "Conforme cronograma de abertura do Transferegov",
            fonteRecurso = "Repasse Orçamentário Federal - FUNASA",
            urlOficial = "https://www.gov.br/transferegov/pt-br",
            areaTematica = "Saneamento Básico",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras até 50 mil habitantes",
            diasRestantes = 45
        ),
        EditalPublico(
            id = "BNDES-MODERNA-ILUMINACAO-PPP",
            orgaoEmissor = "BNDES Estruturação de Parcerias",
            titulo = "Fábrica de Projetos BNDES - Estruturação de PPPs de Iluminação Pública e Resíduos Sólidos",
            resumoObjeto = "Apoio técnico e financiamento de estudos preliminares para consórcios públicos intermunicipais modelarem concessões e parcerias público-privadas de iluminação LED e aterros sanitários.",
            elegibilidade = "Consórcios Públicos Intermunicipais formalmente constituídos (Lei nº 11.107/2005)",
            valorTotalOuTeto = "Custeio 100% dos estudos de modelagem ressarcidos pelo concessionário vencedor",
            prazoSubmissao = "Fluxo Contínuo de manifestação de interesse",
            fonteRecurso = "BNDES FEP (Fundo de Estruturação de Parcerias)",
            urlOficial = "https://hubdeprojetos.bndes.gov.br/",
            areaTematica = "Consórcios e PPPs",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Consórcios Públicos Intermunicipais",
            diasRestantes = null
        ),
        EditalPublico(
            id = "MINC-POLITICA-ALDIR-BLANC-2",
            orgaoEmissor = "Ministério da Cultura (MinC)",
            titulo = "Política Nacional Aldir Blanc de Fomento à Cultura (PNAB) - Ciclo Anual de Execução",
            resumoObjeto = "Repasse de recursos para planos de ação municipais e estaduais para editais culturais locais, subsídios a espaços culturais e fomento a pontos de memória.",
            elegibilidade = "Municípios, Estados e DF que registraram seus Planos de Ação na Plataforma Transferegov",
            valorTotalOuTeto = "R$ 3 bilhões anuais partilhados proporcionalmente aos entes federados",
            prazoSubmissao = "Execução orçamentária anual obrigatória pelos municípios",
            fonteRecurso = "Fundo Nacional de Cultura (FNC) / Lei nº 14.399/2022",
            urlOficial = "https://www.gov.br/cultura/pt-br/assuntos/pnab",
            areaTematica = "Cultura e Turismo",
            estadoOuRegiao = "Nacional",
            tipoEnte = "Prefeituras e Estados",
            diasRestantes = 120
        ),
        EditalPublico(
            id = "FAPESP-INOVACAO-SETOR-PUBLICO-PITE",
            orgaoEmissor = "FAPESP (São Paulo)",
            titulo = "Programa de Pesquisa em Políticas Públicas (PPPP) - Parcerias Municípios e Pesquisadores",
            resumoObjeto = "Financiamento de soluções científicas e diagnósticos aplicados para secretarias de educação, saúde, segurança e trânsito em parceria com universidades.",
            elegibilidade = "Órgãos do Governo do Estado de SP e Prefeituras Paulistas conveniadas",
            valorTotalOuTeto = "Até R$ 500.000,00 por projeto de pesquisa aplicada",
            prazoSubmissao = "Chamadas periódicas ao longo do ano",
            fonteRecurso = "Fundação de Amparo à Pesquisa do Estado de SP (FAPESP)",
            urlOficial = "https://fapesp.br/politicaspublicas",
            areaTematica = "Tecnologia e Inovação",
            estadoOuRegiao = "São Paulo (SP)",
            tipoEnte = "Prefeituras de SP e Secretarias Estaduais",
            diasRestantes = 60
        )
    )
}
