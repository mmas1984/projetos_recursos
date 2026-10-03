import React from 'react';
import { 
  Building2, 
  Calendar, 
  ExternalLink, 
  Share2, 
  Code, 
  Bookmark, 
  BookmarkCheck, 
  Edit3, 
  Coins, 
  Users, 
  Landmark,
  CheckCircle2
} from 'lucide-react';
import { Edital } from '../types';

interface EditalCardProps {
  edital: Edital;
  isSaved: boolean;
  onToggleSave: () => void;
  onOpenNotes: () => void;
  onViewJson: () => void;
  onShare: () => void;
}

export const EditalCard: React.FC<EditalCardProps> = ({
  edital,
  isSaved,
  onToggleSave,
  onOpenNotes,
  onViewJson,
  onShare
}) => {
  const isFluxoContinuo = edital.prazo_submissao.toLowerCase().includes('fluxo contínuo') ||
    edital.prazo_submissao.toLowerCase().includes('fluxo continuo');

  return (
    <div className="bg-white rounded-2xl shadow-sm hover:shadow-md transition-all border border-slate-200/90 overflow-hidden flex flex-col justify-between">
      <div className="p-5 md:p-6">
        {/* Header: Emissor Badge, ID & Bookmark */}
        <div className="flex items-start justify-between gap-3 mb-3">
          <div className="flex items-center gap-2.5">
            <div className="w-9 h-9 rounded-xl bg-federal-50 text-federal-700 flex items-center justify-center border border-federal-100 font-bold text-xs">
              <Building2 className="w-5 h-5 text-federal-700" />
            </div>
            <div>
              <span className="font-bold text-slate-800 text-sm block">
                {edital.orgao_emissor}
              </span>
              <span className="text-[11px] font-mono text-slate-400">
                ID: {edital.id}
              </span>
            </div>
          </div>

          <button
            onClick={onToggleSave}
            className={`p-2 rounded-xl border transition-all ${
              isSaved
                ? 'bg-amber-50 border-amber-300 text-amber-600'
                : 'bg-slate-50 border-slate-200 text-slate-400 hover:text-slate-600 hover:bg-slate-100'
            }`}
            title={isSaved ? "Remover dos salvos" : "Salvar para monitorar"}
          >
            {isSaved ? <BookmarkCheck className="w-5 h-5" /> : <Bookmark className="w-5 h-5" />}
          </button>
        </div>

        {/* Title */}
        <h3 className="font-bold text-slate-900 text-base md:text-lg leading-snug mb-2.5">
          {edital.titulo}
        </h3>

        {/* Objeto Resumido */}
        <div className="p-3.5 bg-slate-50 rounded-xl text-slate-700 text-sm leading-relaxed mb-4 border border-slate-100">
          <p className="line-clamp-3">{edital.resumo_objeto}</p>
        </div>

        {/* Meta Chips: Valor, Elegibilidade, Fonte */}
        <div className="flex flex-wrap gap-2 mb-4">
          {/* Valor */}
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-lg bg-emerald-50 text-emerald-800 text-xs font-bold border border-emerald-200/60">
            <Coins className="w-3.5 h-3.5 text-emerald-600" />
            <span>{edital.valor_total_ou_teto}</span>
          </div>

          {/* Elegibilidade */}
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-lg bg-slate-100 text-slate-700 text-xs font-medium border border-slate-200/60">
            <Users className="w-3.5 h-3.5 text-slate-500" />
            <span className="truncate max-w-[200px]" title={edital.elegibilidade}>
              {edital.elegibilidade}
            </span>
          </div>

          {/* Fonte */}
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-lg bg-blue-50 text-blue-800 text-xs font-medium border border-blue-200/60">
            <Landmark className="w-3.5 h-3.5 text-blue-600" />
            <span className="truncate max-w-[200px]" title={edital.fonte_recurso}>
              {edital.fonte_recurso}
            </span>
          </div>
        </div>

        {/* Prazo Banner */}
        <div className={`p-3 rounded-xl flex items-center justify-between text-xs font-semibold mb-4 border ${
          isFluxoContinuo 
            ? 'bg-purple-50 text-purple-900 border-purple-200/70' 
            : 'bg-amber-50 text-amber-900 border-amber-200/70'
        }`}>
          <div className="flex items-center gap-2">
            <Calendar className="w-4 h-4 text-amber-600" />
            <span>Prazo: {edital.prazo_submissao}</span>
          </div>
          <span className="px-2 py-0.5 rounded text-[11px] font-bold bg-white/80 border border-current shadow-xs">
            {isFluxoContinuo ? 'Fluxo Contínuo' : 'Vigente'}
          </span>
        </div>

        {/* Anotações da Equipe */}
        {edital.anotacoes && (
          <div className="p-3 bg-blue-50/70 border border-blue-200/70 rounded-xl mb-4 text-xs text-blue-900">
            <div className="flex items-center gap-1.5 font-bold mb-1">
              <Edit3 className="w-3.5 h-3.5 text-blue-700" />
              <span>Anotação da Equipe:</span>
            </div>
            <p className="italic">{edital.anotacoes}</p>
          </div>
        )}
      </div>

      {/* Card Actions Footer */}
      <div className="px-5 py-3.5 bg-slate-50 border-t border-slate-100 flex items-center justify-between gap-2">
        <a
          href={edital.url_oficial}
          target="_blank"
          rel="noopener noreferrer"
          className="flex-1 py-2 px-3.5 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-semibold text-xs transition-colors flex items-center justify-center gap-1.5 shadow-sm"
        >
          <span>Acessar Edital Oficial</span>
          <ExternalLink className="w-3.5 h-3.5" />
        </a>

        <div className="flex items-center gap-1">
          <button
            onClick={onShare}
            className="p-2 rounded-lg text-slate-500 hover:text-slate-800 hover:bg-slate-200/70 transition-colors"
            title="Compartilhar resumo"
          >
            <Share2 className="w-4 h-4" />
          </button>

          <button
            onClick={onViewJson}
            className="p-2 rounded-lg text-slate-500 hover:text-slate-800 hover:bg-slate-200/70 transition-colors"
            title="Inspecionar JSON estruturado"
          >
            <Code className="w-4 h-4" />
          </button>

          <button
            onClick={onOpenNotes}
            className="p-2 rounded-lg text-slate-500 hover:text-slate-800 hover:bg-slate-200/70 transition-colors"
            title="Adicionar anotação técnica"
          >
            <Edit3 className="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>
  );
};
