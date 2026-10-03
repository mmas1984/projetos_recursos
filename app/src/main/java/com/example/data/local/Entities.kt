package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.EditalPublico

@Entity(tableName = "saved_editais")
data class EditalEntity(
    @PrimaryKey val id: String,
    val orgaoEmissor: String,
    val titulo: String,
    val resumoObjeto: String,
    val elegibilidade: String,
    val valorTotalOuTeto: String,
    val prazoSubmissao: String,
    val fonteRecurso: String,
    val urlOficial: String,
    val areaTematica: String,
    val estadoOuRegiao: String,
    val tipoEnte: String,
    val anotacoes: String = "",
    val savedAtTimestamp: Long = System.currentTimeMillis()
) {
    fun toEditalPublico(): EditalPublico {
        return EditalPublico(
            id = id,
            orgaoEmissor = orgaoEmissor,
            titulo = titulo,
            resumoObjeto = resumoObjeto,
            elegibilidade = elegibilidade,
            valorTotalOuTeto = valorTotalOuTeto,
            prazoSubmissao = prazoSubmissao,
            fonteRecurso = fonteRecurso,
            urlOficial = urlOficial,
            areaTematica = areaTematica,
            estadoOuRegiao = estadoOuRegiao,
            tipoEnte = tipoEnte,
            isSaved = true,
            anotacoes = anotacoes
        )
    }

    companion object {
        fun fromEditalPublico(edital: EditalPublico): EditalEntity {
            return EditalEntity(
                id = edital.id,
                orgaoEmissor = edital.orgaoEmissor,
                titulo = edital.titulo,
                resumoObjeto = edital.resumoObjeto,
                elegibilidade = edital.elegibilidade,
                valorTotalOuTeto = edital.valorTotalOuTeto,
                prazoSubmissao = edital.prazoSubmissao,
                fonteRecurso = edital.fonteRecurso,
                urlOficial = edital.urlOficial,
                areaTematica = edital.areaTematica,
                estadoOuRegiao = edital.estadoOuRegiao,
                tipoEnte = edital.tipoEnte,
                anotacoes = edital.anotacoes
            )
        }
    }
}

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val areaTematica: String,
    val tipoEnte: String,
    val estadoRegiao: String,
    val termoBusca: String,
    val totalResultados: Int,
    val dataHora: String,
    val timestamp: Long = System.currentTimeMillis()
)
