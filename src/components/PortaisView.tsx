import React from 'react';
import { ExternalLink, Building2, BookOpen, ShieldCheck, ArrowUpRight } from 'lucide-react';
import { PORTAIS_OFICIAIS, PortalInfo } from '../data/portalSources';

export const PortaisView: React.FC = () => {
  return (
    <div className="max-w-5xl mx-auto space-y-6 animate-fadeIn">
      {/* Banner */}
      <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
        <h2 className="text-xl font-bold text-slate-800 mb-1">Diretório de Fontes & Portais Oficiais</h2>
        <p className="text-sm text-slate-500">
          Acesso direto às plataformas do Governo Federal, Bancos de Desenvolvimento e Agências de Fomento
        </p>
      </div>

      {/* Grid of Portals */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {PORTAIS_OFICIAIS.map((portal) => (
          <div
            key={portal.sigla}
            className="bg-white rounded-2xl border border-slate-200/90 p-5 shadow-sm hover:shadow-md transition-all flex flex-col justify-between"
          >
            <div>
              <div className="flex items-center justify-between gap-2 mb-3">
                <span className="font-mono font-bold text-xs bg-federal-100 text-federal-800 px-2.5 py-1 rounded-lg">
                  {portal.sigla}
                </span>
                <span className="text-[11px] font-semibold text-slate-500 bg-slate-100 px-2.5 py-0.5 rounded-full">
                  {portal.categoria}
                </span>
              </div>

              <h3 className="font-bold text-slate-800 text-base mb-2">
                {portal.nome}
              </h3>

              <p className="text-xs text-slate-600 leading-relaxed mb-4">
                {portal.descricao}
              </p>

              <div className="p-3 bg-slate-50 rounded-xl border border-slate-100 mb-4 text-xs">
                <span className="font-bold text-slate-700 block mb-1">Como Ingressar / Submeter:</span>
                <p className="text-slate-600">{portal.como_inscrever}</p>
              </div>
            </div>

            <a
              href={portal.url_oficial}
              target="_blank"
              rel="noopener noreferrer"
              className="w-full py-2.5 px-4 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-semibold text-xs transition-colors flex items-center justify-center gap-1.5 shadow-sm"
            >
              <span>Acessar Portal Oficial</span>
              <ArrowUpRight className="w-4 h-4" />
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};
