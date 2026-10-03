import React, { useState, useEffect } from 'react';
import { Header } from './components/Header';
import { FilterBar } from './components/FilterBar';
import { EditalCard } from './components/EditalCard';
import { JsonModal } from './components/JsonModal';
import { NotesModal } from './components/NotesModal';
import { CaucView } from './components/CaucView';
import { PortaisView } from './components/PortaisView';
import { Edital, FilterParams, SearchResult } from './types';
import { searchEditais } from './services/geminiSearch';
import { 
  Radar, 
  Sparkles, 
  Code, 
  Copy, 
  Share2, 
  Globe, 
  CheckCircle, 
  Bookmark, 
  AlertCircle,
  FileText
} from 'lucide-react';

export const App: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'radar' | 'saved' | 'cauc' | 'portais'>('radar');
  
  const [filters, setFilters] = useState<FilterParams>({
    area_tematica: "Todas as Áreas",
    tipo_ente: "Prefeituras e Municípios",
    estado_regiao: "Nacional (Todos os Estados)",
    termo_busca: ""
  });

  const [isLoading, setIsLoading] = useState(false);
  const [searchResult, setSearchResult] = useState<SearchResult | null>(null);
  const [savedEditais, setSavedEditais] = useState<Edital[]>(() => {
    try {
      const saved = localStorage.getItem('capta_editais_saved');
      return saved ? JSON.parse(saved) : [];
    } catch {
      return [];
    }
  });

  const [activeJson, setActiveJson] = useState<string | null>(null);
  const [editalForNotes, setEditalForNotes] = useState<Edital | null>(null);
  const [toastMessage, setToastMessage] = useState<string | null>(null);
  const [showInfoModal, setShowInfoModal] = useState(false);

  // Sync saved editais to local storage
  useEffect(() => {
    localStorage.setItem('capta_editais_saved', JSON.stringify(savedEditais));
  }, [savedEditais]);

  // Initial search on mount
  useEffect(() => {
    handleSearch();
  }, []);

  const showToast = (msg: string) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(null), 3000);
  };

  const handleSearch = async () => {
    setIsLoading(true);
    try {
      const result = await searchEditais(filters);
      setSearchResult(result);
    } catch (err) {
      console.error(err);
      showToast("Erro ao realizar a busca.");
    } finally {
      setIsLoading(false);
    }
  };

  const toggleSaveEdital = (edital: Edital) => {
    const isAlreadySaved = savedEditais.some(item => item.id === edital.id);
    if (isAlreadySaved) {
      setSavedEditais(prev => prev.filter(item => item.id !== edital.id));
      showToast("Edital removido dos monitorados.");
    } else {
      setSavedEditais(prev => [edital, ...prev]);
      showToast("Edital salvo para acompanhamento!");
    }
  };

  const handleSaveNotes = (id: string, notes: string) => {
    setSavedEditais(prev => prev.map(item => {
      if (item.id === id) {
        return { ...item, anotacoes: notes };
      }
      return item;
    }));

    if (searchResult) {
      setSearchResult({
        ...searchResult,
        editais: searchResult.editais.map(item => item.id === id ? { ...item, anotacoes: notes } : item)
      });
    }

    showToast("Anotação salva com sucesso!");
  };

  const shareEdital = (edital: Edital) => {
    const text = `📢 OPORTUNIDADE DE CAPTAÇÃO PÚBLICA\n🏛️ Órgão: ${edital.orgao_emissor}\n📄 Título: ${edital.titulo}\n🎯 Objeto: ${edital.resumo_objeto}\n👥 Elegibilidade: ${edital.elegibilidade}\n💰 Valor/Teto: ${edital.valor_total_ou_teto}\n📅 Prazo: ${edital.prazo_submissao}\n🏦 Fonte: ${edital.fonte_recurso}\n🔗 Link Oficial: ${edital.url_oficial}`;
    
    if (navigator.share) {
      navigator.share({ title: edital.titulo, text }).catch(() => {});
    } else {
      navigator.clipboard.writeText(text);
      showToast("Resumo copiado para compartilhamento!");
    }
  };

  const shareBriefingAll = () => {
    if (savedEditais.length === 0) return;
    let briefing = `📋 RELATÓRIO EXECUTIVO - EDITAIS MONITORADOS (${savedEditais.length} oportunidades)\nGerado via CaptaEditais\n\n`;
    savedEditais.forEach((e, idx) => {
      briefing += `----------------------------------------\n${idx + 1}. [${e.orgao_emissor}] ${e.titulo}\nObjeto: ${e.resumo_objeto}\nPrazo: ${e.prazo_submissao}\nValor: ${e.valor_total_ou_teto}\nLink Oficial: ${e.url_oficial}\n`;
      if (e.anotacoes) briefing += `Notas: ${e.anotacoes}\n`;
      briefing += `\n`;
    });

    navigator.clipboard.writeText(briefing);
    showToast("Briefing executivo copiado para a área de transferência!");
  };

  return (
    <div className="min-h-screen bg-slate-50 flex flex-col selection:bg-federal-200">
      <Header
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        savedCount={savedEditais.length}
        onOpenInfo={() => setShowInfoModal(true)}
      />

      {/* Main Container */}
      <main className="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-6">
        {/* TAB 1: RADAR DE EDITAIS */}
        {activeTab === 'radar' && (
          <div>
            {/* Hero Subtitle Banner */}
            <div className="bg-gradient-to-r from-federal-900 to-federal-800 rounded-2xl p-6 text-white mb-6 shadow-md border border-federal-700/50 flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div className="space-y-1">
                <div className="inline-flex items-center gap-2 px-2.5 py-1 rounded-full bg-white/10 text-amber-300 text-xs font-semibold backdrop-blur-xs">
                  <Sparkles className="w-3.5 h-3.5" />
                  <span>Busca Web Ativa & Editais Vigentes</span>
                </div>
                <h1 className="text-xl md:text-2xl font-bold tracking-tight">
                  Rastreador Especialista de Recursos Públicos
                </h1>
                <p className="text-xs md:text-sm text-slate-300 max-w-2xl">
                  Editais, chamadas públicas e transferências vigentes para Municípios, Estados e Consórcios no Brasil (Transferegov, BNDES, FINEP, Novo PAC e Ministérios).
                </p>
              </div>

              {searchResult && (
                <div className="flex items-center gap-2">
                  <button
                    onClick={() => setActiveJson(searchResult.raw_json || JSON.stringify(searchResult, null, 2))}
                    className="py-2.5 px-4 rounded-xl bg-white/10 hover:bg-white/20 text-white font-bold text-xs border border-white/20 transition-all flex items-center gap-1.5 shadow-sm"
                  >
                    <Code className="w-4 h-4" />
                    <span>Ver JSON Estruturado</span>
                  </button>
                </div>
              )}
            </div>

            {/* Filter Bar */}
            <FilterBar
              filters={filters}
              setFilters={setFilters}
              onSearch={handleSearch}
              isLoading={isLoading}
            />

            {/* Status / Results Count Header */}
            {searchResult && (
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 mb-4 px-1">
                <div>
                  <div className="flex items-center gap-2">
                    <span className="text-base font-bold text-slate-800">
                      {searchResult.total_encontrados} editais encontrados
                    </span>
                    <span className="inline-flex items-center gap-1 text-[11px] font-semibold px-2 py-0.5 rounded-full bg-emerald-100 text-emerald-800 border border-emerald-200">
                      <Globe className="w-3 h-3 text-emerald-600" />
                      {searchResult.is_live_web ? 'Web em Tempo Real' : 'Base Oficial Verificada'}
                    </span>
                  </div>
                  <span className="text-xs text-slate-500">
                    Consulta em {searchResult.data_consulta} • Links oficiais validados
                  </span>
                </div>

                <div className="flex items-center gap-2">
                  <button
                    onClick={() => {
                      const jsonText = searchResult.raw_json || JSON.stringify(searchResult, null, 2);
                      navigator.clipboard.writeText(jsonText);
                      showToast("JSON completo copiado!");
                    }}
                    className="py-1.5 px-3 rounded-lg bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-semibold flex items-center gap-1 transition-colors"
                  >
                    <Copy className="w-3.5 h-3.5 text-slate-500" />
                    <span>Copiar JSON</span>
                  </button>
                </div>
              </div>
            )}

            {/* Editais Grid */}
            {searchResult && searchResult.editais.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
                {searchResult.editais.map((edital) => {
                  const isSaved = savedEditais.some(item => item.id === edital.id);
                  return (
                    <EditalCard
                      key={edital.id}
                      edital={edital}
                      isSaved={isSaved}
                      onToggleSave={() => toggleSaveEdital(edital)}
                      onOpenNotes={() => setEditalForNotes(edital)}
                      onViewJson={() => setActiveJson(JSON.stringify(edital, null, 2))}
                      onShare={() => shareEdital(edital)}
                    />
                  );
                })}
              </div>
            ) : !isLoading && searchResult ? (
              <div className="bg-white rounded-2xl p-12 text-center border border-slate-200">
                <Radar className="w-12 h-12 text-slate-300 mx-auto mb-3" />
                <h3 className="font-bold text-slate-700 text-base mb-1">Nenhum edital vigente encontrado</h3>
                <p className="text-xs text-slate-500 max-w-md mx-auto mb-4">
                  Tente alterar os termos ou selecionar "Todas as Áreas" para visualizar chamadas abertas no momento.
                </p>
                <button
                  onClick={() => {
                    setFilters({
                      area_tematica: "Todas as Áreas",
                      tipo_ente: "Prefeituras e Municípios",
                      estado_regiao: "Nacional (Todos os Estados)",
                      termo_busca: ""
                    });
                    handleSearch();
                  }}
                  className="py-2 px-4 rounded-xl bg-federal-800 text-white font-bold text-xs shadow-sm hover:bg-federal-900 transition-colors"
                >
                  Restaurar Filtros Padrão
                </button>
              </div>
            ) : null}
          </div>
        )}

        {/* TAB 2: MONITORADOS & SALVOS */}
        {activeTab === 'saved' && (
          <div className="space-y-6">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
              <div>
                <h2 className="text-xl font-bold text-slate-800">Editais Monitorados</h2>
                <p className="text-sm text-slate-500">
                  {savedEditais.length} oportunidades salvas para acompanhamento contínuo da equipe técnica
                </p>
              </div>

              {savedEditais.length > 0 && (
                <button
                  onClick={shareBriefingAll}
                  className="py-2.5 px-4 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-bold text-xs shadow-sm flex items-center justify-center gap-1.5 transition-all"
                >
                  <FileText className="w-4 h-4" />
                  <span>Gerar Briefing Executivo</span>
                </button>
              )}
            </div>

            {savedEditais.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
                {savedEditais.map((edital) => (
                  <EditalCard
                    key={edital.id}
                    edital={edital}
                    isSaved={true}
                    onToggleSave={() => toggleSaveEdital(edital)}
                    onOpenNotes={() => setEditalForNotes(edital)}
                    onViewJson={() => setActiveJson(JSON.stringify(edital, null, 2))}
                    onShare={() => shareEdital(edital)}
                  />
                ))}
              </div>
            ) : (
              <div className="bg-white rounded-2xl p-12 text-center border border-slate-200">
                <Bookmark className="w-12 h-12 text-slate-300 mx-auto mb-3" />
                <h3 className="font-bold text-slate-700 text-base mb-1">Nenhum edital monitorado ainda</h3>
                <p className="text-xs text-slate-500 max-w-md mx-auto mb-4">
                  No Radar de Editais, clique no ícone de marcador para salvar oportunidades para a sua prefeitura ou consórcio.
                </p>
                <button
                  onClick={() => setActiveTab('radar')}
                  className="py-2 px-4 rounded-xl bg-federal-800 text-white font-bold text-xs shadow-sm hover:bg-federal-900 transition-colors"
                >
                  Explorar Editais Vigentes
                </button>
              </div>
            )}
          </div>
        )}

        {/* TAB 3: CHECKLIST CAUC / SIAFI */}
        {activeTab === 'cauc' && <CaucView />}

        {/* TAB 4: FONTES & PORTAIS OFICIAIS */}
        {activeTab === 'portais' && <PortaisView />}
      </main>

      {/* JSON Viewer Modal */}
      <JsonModal
        jsonString={activeJson}
        onClose={() => setActiveJson(null)}
      />

      {/* Notes Modal */}
      <NotesModal
        edital={editalForNotes}
        onClose={() => setEditalForNotes(null)}
        onSaveNotes={handleSaveNotes}
      />

      {/* Info Modal */}
      {showInfoModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/60 backdrop-blur-xs animate-fadeIn">
          <div className="bg-white rounded-2xl shadow-2xl border border-slate-200 max-w-lg w-full p-6">
            <h3 className="text-lg font-bold text-slate-800 mb-2">Sobre o CaptaEditais</h3>
            <p className="text-xs text-slate-600 leading-relaxed mb-4">
              O <strong>CaptaEditais</strong> é uma plataforma especialista desenvolvida para captadores de recursos, prefeitos, secretários municipais e gestores de consórcios públicos no Brasil.
            </p>
            <ul className="text-xs text-slate-600 space-y-2 mb-6 list-disc pl-5">
              <li><strong>Zero Alucinação de Links:</strong> Retorna exclusivamente links oficiais diretos das páginas e chamamentos dos órgãos governamentais.</li>
              <li><strong>Formatos Padronizados:</strong> Exportação de dados estruturados em JSON estrito para integração e automação.</li>
              <li><strong>Fontes Monitoradas:</strong> Transferegov.br, BNDES, FINEP, Novo PAC, FNS, Fundo Clima e FAPs estaduais.</li>
              <li><strong>Checklist de Habilitação CAUC:</strong> Guia prático dos 14 itens para garantir que o município possa receber transferências voluntárias da União.</li>
            </ul>
            <div className="flex justify-end">
              <button
                onClick={() => setShowInfoModal(false)}
                className="py-2 px-5 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-bold text-xs shadow-sm"
              >
                Fechar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed bottom-6 right-6 z-50 bg-slate-900 text-white px-4 py-3 rounded-xl shadow-xl flex items-center gap-2.5 text-xs font-semibold animate-bounce">
          <CheckCircle className="w-4 h-4 text-emerald-400" />
          <span>{toastMessage}</span>
        </div>
      )}
    </div>
  );
};
