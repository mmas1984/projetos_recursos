import React, { useState } from 'react';
import { CheckSquare, AlertTriangle, ExternalLink, ShieldCheck, HelpCircle } from 'lucide-react';
import { CAUC_ITEMS, CaucItem } from '../data/portalSources';

export const CaucView: React.FC = () => {
  const [checkedItems, setCheckedItems] = useState<Record<string, boolean>>({});

  const toggleCheck = (id: string) => {
    setCheckedItems(prev => ({
      ...prev,
      [id]: !prev[id]
    }));
  };

  const checkedCount = Object.values(checkedItems).filter(Boolean).length;
  const totalCount = CAUC_ITEMS.length;
  const isAllValid = checkedCount === totalCount;

  return (
    <div className="max-w-4xl mx-auto space-y-6 animate-fadeIn">
      {/* Overview Banner */}
      <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div className="flex items-start gap-3.5">
            <div className={`p-3 rounded-2xl ${isAllValid ? 'bg-emerald-100 text-emerald-800' : 'bg-amber-100 text-amber-800'}`}>
              {isAllValid ? <ShieldCheck className="w-7 h-7" /> : <AlertTriangle className="w-7 h-7" />}
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-800">Checklist CAUC / SIAFI</h2>
              <p className="text-sm text-slate-500">
                Auditoria prévia dos 14 requisitos de regularidade fiscal do Tesouro Nacional para convênios
              </p>
            </div>
          </div>

          <a
            href="https://cauc.tesouro.gov.br/"
            target="_blank"
            rel="noopener noreferrer"
            className="py-2.5 px-4 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-bold text-xs shadow-sm flex items-center justify-center gap-1.5 transition-all shrink-0"
          >
            <span>Consultar CAUC Oficial (STN)</span>
            <ExternalLink className="w-4 h-4" />
          </a>
        </div>

        {/* Progress Bar */}
        <div className="mt-6 pt-4 border-t border-slate-100">
          <div className="flex justify-between items-center mb-2 text-xs font-bold">
            <span className="text-slate-600">
              Requisitos Validados pela Equipe: {checkedCount} de {totalCount}
            </span>
            <span className={isAllValid ? 'text-emerald-600' : 'text-federal-700'}>
              {Math.round((checkedCount / totalCount) * 100)}%
            </span>
          </div>
          <div className="w-full bg-slate-100 h-2.5 rounded-full overflow-hidden">
            <div
              className={`h-full transition-all duration-300 ${isAllValid ? 'bg-emerald-500' : 'bg-federal-700'}`}
              style={{ width: `${(checkedCount / totalCount) * 100}%` }}
            />
          </div>
        </div>
      </div>

      {/* Checklist Items */}
      <div className="space-y-3">
        {CAUC_ITEMS.map((item) => {
          const checked = !!checkedItems[item.id];
          return (
            <div
              key={item.id}
              onClick={() => toggleCheck(item.id)}
              className={`p-4 rounded-2xl border transition-all cursor-pointer flex items-start gap-4 ${
                checked
                  ? 'bg-emerald-50/50 border-emerald-300/80 shadow-xs'
                  : 'bg-white border-slate-200/80 hover:border-slate-300'
              }`}
            >
              <input
                type="checkbox"
                checked={checked}
                onChange={() => {}} // Controlled by parent div
                className="mt-1 w-5 h-5 rounded text-federal-700 focus:ring-federal-500 cursor-pointer"
              />

              <div className="flex-1">
                <div className="flex flex-wrap items-center justify-between gap-2 mb-1">
                  <div className="flex items-center gap-2">
                    <span className="font-mono text-xs font-bold text-federal-800 bg-federal-100 px-2 py-0.5 rounded">
                      {item.id}
                    </span>
                    <h4 className="font-bold text-slate-800 text-sm">{item.titulo}</h4>
                  </div>
                  <span className="text-[11px] font-semibold text-slate-500 bg-slate-100 px-2.5 py-0.5 rounded-full">
                    {item.orgao_validador}
                  </span>
                </div>

                <p className="text-xs text-slate-600 mb-1">{item.descricao}</p>
                <span className="text-[11px] font-medium text-federal-600 block">
                  {item.grupo}
                </span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
