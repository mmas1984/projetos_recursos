import React, { useState } from 'react';
import { X, Edit3, Save } from 'lucide-react';
import { Edital } from '../types';

interface NotesModalProps {
  edital: Edital | null;
  onClose: () => void;
  onSaveNotes: (id: string, notes: string) => void;
}

export const NotesModal: React.FC<NotesModalProps> = ({ edital, onClose, onSaveNotes }) => {
  if (!edital) return null;

  const [notes, setNotes] = useState(edital.anotacoes || '');

  const handleSave = () => {
    onSaveNotes(edital.id, notes);
    onClose();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-2xl shadow-2xl border border-slate-200 max-w-lg w-full overflow-hidden">
        {/* Header */}
        <div className="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50">
          <div className="flex items-center gap-2">
            <div className="p-2 rounded-lg bg-blue-100 text-blue-800">
              <Edit3 className="w-5 h-5" />
            </div>
            <div>
              <h3 className="font-bold text-slate-800 text-base">Anotações da Equipe Técnica</h3>
              <p className="text-xs text-slate-500 truncate max-w-xs">{edital.titulo}</p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-200/60 transition-colors"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Body */}
        <div className="p-6">
          <label className="block text-xs font-semibold text-slate-600 mb-2">
            Notas de acompanhamento, responsáveis internos e pendências:
          </label>
          <textarea
            value={notes}
            onChange={(e) => setNotes(e.target.value)}
            rows={5}
            placeholder="Ex: Secretaria de Obras verificando projeto técnico. Requer contrapartida de 5%. Prazo de inserção de proposta no Transferegov até 20 do próximo mês..."
            className="w-full p-3.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-800 text-sm focus:outline-none focus:ring-2 focus:ring-federal-500 focus:bg-white placeholder-slate-400"
          />
        </div>

        {/* Footer */}
        <div className="px-6 py-3.5 bg-slate-50 border-t border-slate-100 flex items-center justify-end gap-2">
          <button
            onClick={onClose}
            className="py-2 px-4 rounded-xl bg-slate-200 hover:bg-slate-300 text-slate-700 font-semibold text-xs transition-colors"
          >
            Cancelar
          </button>
          <button
            onClick={handleSave}
            className="py-2 px-4 rounded-xl bg-federal-800 hover:bg-federal-900 text-white font-bold text-xs shadow-sm flex items-center gap-1.5 transition-all"
          >
            <Save className="w-4 h-4" />
            <span>Salvar Anotação</span>
          </button>
        </div>
      </div>
    </div>
  );
};
