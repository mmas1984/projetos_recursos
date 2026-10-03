import React from 'react';
import { Radar, Bookmark, CheckSquare, Globe, Info, ExternalLink } from 'lucide-react';

interface HeaderProps {
  activeTab: 'radar' | 'saved' | 'cauc' | 'portais';
  setActiveTab: (tab: 'radar' | 'saved' | 'cauc' | 'portais') => void;
  savedCount: number;
  onOpenInfo: () => void;
}

export const Header: React.FC<HeaderProps> = ({ activeTab, setActiveTab, savedCount, onOpenInfo }) => {
  return (
    <header className="bg-federal-800 text-white shadow-lg sticky top-0 z-40 border-b border-federal-700">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo & Brand */}
          <div className="flex items-center gap-3 cursor-pointer" onClick={() => setActiveTab('radar')}>
            <div className="w-10 h-10 rounded-xl bg-white/10 flex items-center justify-center border border-white/20 shadow-inner">
              <Radar className="w-6 h-6 text-amber-400 animate-pulse" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <span className="text-xl font-bold tracking-tight">CaptaEditais</span>
                <span className="text-[11px] font-semibold uppercase tracking-wider px-2 py-0.5 rounded-full bg-emerald-500/20 text-emerald-300 border border-emerald-500/30">
                  Setor Público
                </span>
              </div>
              <p className="text-xs text-slate-300 hidden sm:block">
                Rastreador e Analista de Editais e Recursos Públicos no Brasil
              </p>
            </div>
          </div>

          {/* Desktop Navigation Tabs */}
          <nav className="hidden md:flex items-center space-x-1">
            <button
              onClick={() => setActiveTab('radar')}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-sm font-semibold transition-all ${
                activeTab === 'radar'
                  ? 'bg-white/15 text-white shadow-sm'
                  : 'text-slate-300 hover:text-white hover:bg-white/5'
              }`}
            >
              <Radar className="w-4 h-4 text-amber-400" />
              Radar de Editais
            </button>

            <button
              onClick={() => setActiveTab('saved')}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-sm font-semibold transition-all relative ${
                activeTab === 'saved'
                  ? 'bg-white/15 text-white shadow-sm'
                  : 'text-slate-300 hover:text-white hover:bg-white/5'
              }`}
            >
              <Bookmark className="w-4 h-4 text-blue-400" />
              Monitorados
              {savedCount > 0 && (
                <span className="ml-1 px-1.5 py-0.5 text-xs font-bold rounded-full bg-amber-500 text-slate-900">
                  {savedCount}
                </span>
              )}
            </button>

            <button
              onClick={() => setActiveTab('cauc')}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-sm font-semibold transition-all ${
                activeTab === 'cauc'
                  ? 'bg-white/15 text-white shadow-sm'
                  : 'text-slate-300 hover:text-white hover:bg-white/5'
              }`}
            >
              <CheckSquare className="w-4 h-4 text-emerald-400" />
              Checklist CAUC
            </button>

            <button
              onClick={() => setActiveTab('portais')}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-sm font-semibold transition-all ${
                activeTab === 'portais'
                  ? 'bg-white/15 text-white shadow-sm'
                  : 'text-slate-300 hover:text-white hover:bg-white/5'
              }`}
            >
              <Globe className="w-4 h-4 text-cyan-400" />
              Fontes Oficiais
            </button>
          </nav>

          {/* Right Action */}
          <div className="flex items-center gap-2">
            <button
              onClick={onOpenInfo}
              className="p-2 text-slate-300 hover:text-white hover:bg-white/10 rounded-lg transition-colors"
              title="Sobre o CaptaEditais"
            >
              <Info className="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Nav Bar */}
      <div className="md:hidden border-t border-federal-700 bg-federal-900/90 backdrop-blur px-2 py-1 flex justify-around">
        <button
          onClick={() => setActiveTab('radar')}
          className={`flex flex-col items-center py-1.5 px-3 rounded text-xs font-medium ${
            activeTab === 'radar' ? 'text-amber-400' : 'text-slate-400'
          }`}
        >
          <Radar className="w-5 h-5 mb-0.5" />
          Radar
        </button>

        <button
          onClick={() => setActiveTab('saved')}
          className={`flex flex-col items-center py-1.5 px-3 rounded text-xs font-medium relative ${
            activeTab === 'saved' ? 'text-amber-400' : 'text-slate-400'
          }`}
        >
          <Bookmark className="w-5 h-5 mb-0.5" />
          Salvos
          {savedCount > 0 && (
            <span className="absolute top-1 right-2 px-1 text-[10px] font-bold rounded-full bg-amber-500 text-slate-900">
              {savedCount}
            </span>
          )}
        </button>

        <button
          onClick={() => setActiveTab('cauc')}
          className={`flex flex-col items-center py-1.5 px-3 rounded text-xs font-medium ${
            activeTab === 'cauc' ? 'text-amber-400' : 'text-slate-400'
          }`}
        >
          <CheckSquare className="w-5 h-5 mb-0.5" />
          CAUC
        </button>

        <button
          onClick={() => setActiveTab('portais')}
          className={`flex flex-col items-center py-1.5 px-3 rounded text-xs font-medium ${
            activeTab === 'portais' ? 'text-amber-400' : 'text-slate-400'
          }`}
        >
          <Globe className="w-5 h-5 mb-0.5" />
          Fontes
        </button>
      </div>
    </header>
  );
};
