package com.example.data.repository

import com.example.data.local.EditalDao
import com.example.data.local.EditalEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.remote.GeminiWebSearchService
import com.example.model.EditalPublico
import com.example.model.FilterParams
import com.example.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditalRepository(
    private val editalDao: EditalDao,
    private val webSearchService: GeminiWebSearchService = GeminiWebSearchService()
) {

    val savedEditais: Flow<List<EditalPublico>> = editalDao.getAllSavedEditais()
        .map { list -> list.map { it.toEditalPublico() } }

    val searchHistory: Flow<List<SearchHistoryEntity>> = editalDao.getSearchHistory()

    suspend fun searchEditais(filters: FilterParams): SearchResult {
        val result = webSearchService.searchEditais(filters)

        // Mark which editais are already saved in DB
        val updatedEditais = result.editais.map { edital ->
            val isSaved = editalDao.isEditalSaved(edital.id)
            edital.copy(isSaved = isSaved)
        }

        // Save search in history
        val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        editalDao.insertSearchHistory(
            SearchHistoryEntity(
                areaTematica = filters.areaTematica,
                tipoEnte = filters.tipoEnte,
                estadoRegiao = filters.estadoRegiao,
                termoBusca = filters.termoBusca,
                totalResultados = updatedEditais.size,
                dataHora = dateStr
            )
        )

        return result.copy(editais = updatedEditais)
    }

    suspend fun toggleSaveEdital(edital: EditalPublico): Boolean {
        val currentlySaved = editalDao.isEditalSaved(edital.id)
        if (currentlySaved) {
            editalDao.deleteEditalById(edital.id)
            return false
        } else {
            editalDao.insertEdital(EditalEntity.fromEditalPublico(edital))
            return true
        }
    }

    suspend fun saveEdital(edital: EditalPublico) {
        editalDao.insertEdital(EditalEntity.fromEditalPublico(edital))
    }

    suspend fun removeEdital(id: String) {
        editalDao.deleteEditalById(id)
    }

    suspend fun updateAnotacoes(id: String, notes: String) {
        editalDao.updateAnotacoes(id, notes)
    }

    fun isEditalSavedFlow(id: String): Flow<Boolean> {
        return editalDao.isEditalSavedFlow(id)
    }
}
