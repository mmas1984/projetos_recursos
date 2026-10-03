import React, { useState } from 'react';
import { Search, Filter, Radar, ChevronDown, ChevronUp, MapPin, Building, Sparkles } from 'lucide-react';
import { FilterParams } from '../types';

interface FilterBarProps {
  filters: FilterParams;
  setFilters: React.Dispatch<React.SetStateAction<FilterParams>>;
  onSearch: () => void;
  isLoading: boolean;
}

const AREAS = [
  "Todas as Áreas",
  "Saúde",
  "Educação",
  "Infraestrutura e Obras",
  "Saneamento Básico",
  "Meio Ambiente e Clima",
  "Tecnologia e Inovação",
  "Cultura e Turismo",
  "Segurança Pública",
  "Agricultura e Abastecimento"
];

const ENTES = [
  "Prefeituras e Municípios",
  "Consórcios Públicos",
  "Governos Estaduais",
  "Entidades e Hospitais Públicos"
];

const REGIOES = [
  "Nacional (Todos os Estados)",
  "Região Sudeste (SP, RJ, MG, ES)",
  "Região Sul (PR, SC, RS)",
  "Região Nordeste (BA, PE, CE, etc.)",
  "Região Centro-Oeste (GO, MT, MS, DF)",
  "Região Norte (AM, PA, RO, etc.)",
  "São Paulo (SP)",
  "Minas Gerais (MG)",
  "Rio de Janeiro (RJ)",
  "Bahia (BA)",
  "Paraná (PR)",
  "Rio Grande do Sul (RS)"
];

export const FilterBar: React.FC<FilterBarProps> = ({ filters, setFilters, onSearch, isLoading }) => {
  const [showAdvanced, setShowAdvanced] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch();
  };

  return (
    <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-5 md:p-6 mb-6">
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Main Search Input */}
        <div className="relative flex items-center">
          <Search className="w-5 h-5 text-slate-400 absolute left-4 pointer-events-none" />
          <input
            type="text"
            value={filters.termo_busca}
            onChange={(e) => setFilters(prev => ({ ...prev, termo_busca: e.target.value }))}
            placeholder="Buscar por termo ou objeto estratégico (ex: pavimentação, telemedicina, resíduos, creches...)"
            className="w-full pl-12 pr-4 py-3.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-federal-500 focus:bg-white text-sm transition-all"
          />
          {filters.termo_busca && (
            <button
              type="button"
              onClick={() => setFilters(prev => ({ ...prev, termo_busca: '' }))}
              className="absolute right-4 text-xs font-semibold text-slate-400 hover:text-slate-600 bg-slate-200 hover:bg-slate-300 rounded-full px-2 py-0.5"
            >
              Limpar
            </button>
          )}
        </div>

        {/* Quick Area Chips */}
        <div>
          <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wider mb-2">
            Área Temática Prioritária:
          </label>
          <div className="flex flex-wrap gap-1.5 sm:gap-2">
            {AREAS.map((area) => {
              const selected = filters.area_tematica === area;
              return (
                <button
                  key={area}
                  type="button"
                  onClick={() => setFilters(prev => ({ ...prev, area_tematica: area }))}
                  className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                    selected
                      ? 'bg-federal-700 text-white shadow-sm ring-1 ring-federal-800'
                      : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
                  }`}
                >
                  {area}
                </button>
              );
            })}
          </div>
        </div>

        {/* Toggle Advanced Filters */}
        <div className="pt-1">
          <button
            type="button"
            onClick={() => setShowAdvanced(!showAdvanced)}
            className="flex items-center gap-1.5 text-xs font-semibold text-federal-700 hover:text-federal-900 transition-colors"
          >
            <Filter className="w-3.5 h-3.5" />
            <span>Filtros de Ente Público e Região</span>
            {showAdvanced ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
          </button>
        </div>

        {/* Advanced Filters Dropdowns */}
        {showAdvanced && (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 pt-2 border-t border-slate-100 animate-fadeIn">
            <div>
              <label className="block text-xs font-semibold text-slate-600 mb-1.5 flex items-center gap-1.5">
                <Building className="w-4 h-4 text-slate-400" />
                Proponente / Tipo de Ente:
              </label>
              <select
                value={filters.tipo_ente}
                onChange={(e) => setFilters(prev => ({ ...prev, tipo_ente: e.target.value }))}
                className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-700 text-sm focus:outline-none focus:ring-2 focus:ring-federal-500 focus:bg-white"
              >
                {ENTES.map(ente => (
                  <option key={ente} value={ente}>{ente}</option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-600 mb-1.5 flex items-center gap-1.5">
                <MapPin className="w-4 h-4 text-slate-400" />
                Estado / Região de Interesse:
              </label>
              <select
                value={filters.estado_regiao}
                onChange={(e) => setFilters(prev => ({ ...prev, estado_regiao: e.target.value }))}
                className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-700 text-sm focus:outline-none focus:ring-2 focus:ring-federal-500 focus:bg-white"
              >
                {REGIOES.map(reg => (
                  <option key={reg} value={reg}>{reg}</option>
                ))}
              </select>
            </div>
          </div>
        )}

        {/* Action Button */}
        <div className="pt-2">
          <button
            type="submit"
            disabled={isLoading}
            className="w-full py-3.5 px-6 rounded-xl bg-federal-800 hover:bg-federal-900 active:scale-[0.99] text-white font-bold text-sm shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2 disabled:opacity-60"
          >
            {isLoading ? (
              <>
                <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                <span>Rastreando Editais Vigentes na Web...</span>
              </>
            ) : (
              <>
                <Radar className="w-4 h-4 text-amber-400 animate-pulse" />
                <span>Rastrear Editais Vigentes</span>
              </>
            )}
          </button>
        </div>
      </form>
    </div>
  );
};
