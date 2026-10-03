package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SearchHistoryEntity
import com.example.data.repository.EditalRepository
import com.example.model.EditalPublico
import com.example.model.FilterParams
import com.example.model.SearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data class Scanning(val stepMessage: String) : SearchUiState
    data class Success(val result: SearchResult) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

class EditalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EditalRepository

    private val _filterParams = MutableStateFlow(FilterParams())
    val filterParams: StateFlow<FilterParams> = _filterParams.asStateFlow()

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _activeJsonView = MutableStateFlow<String?>(null)
    val activeJsonView: StateFlow<String?> = _activeJsonView.asStateFlow()

    private val _editalForNotes = MutableStateFlow<EditalPublico?>(null)
    val editalForNotes: StateFlow<EditalPublico?> = _editalForNotes.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    val savedEditais: StateFlow<List<EditalPublico>>
    val searchHistory: StateFlow<List<SearchHistoryEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = EditalRepository(db.editalDao())

        savedEditais = repository.savedEditais.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        searchHistory = repository.searchHistory.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Perform initial search to show active notices immediately
        performSearch()
    }

    fun setSelectedTab(index: Int) {
        _selectedTab.value = index
    }

    fun updateFilters(newFilters: FilterParams) {
        _filterParams.value = newFilters
    }

    fun updateAreaTematica(area: String) {
        _filterParams.value = _filterParams.value.copy(areaTematica = area)
    }

    fun updateTipoEnte(ente: String) {
        _filterParams.value = _filterParams.value.copy(tipoEnte = ente)
    }

    fun updateEstadoRegiao(regiao: String) {
        _filterParams.value = _filterParams.value.copy(estadoRegiao = regiao)
    }

    fun updateTermoBusca(termo: String) {
        _filterParams.value = _filterParams.value.copy(termoBusca = termo)
    }

    fun performSearch() {
        viewModelScope.launch {
            _searchUiState.value = SearchUiState.Scanning("Iniciando varredura em tempo real...")
            delay(400)
            _searchUiState.value = SearchUiState.Scanning("Consultando Transferegov, BNDES, FINEP e Ministérios...")

            try {
                val result = repository.searchEditais(_filterParams.value)
                delay(300)
                _searchUiState.value = SearchUiState.Success(result)
            } catch (e: Exception) {
                _searchUiState.value = SearchUiState.Error("Falha na consulta: ${e.localizedMessage ?: "Erro desconhecido"}")
            }
        }
    }

    fun toggleSaveEdital(edital: EditalPublico) {
        viewModelScope.launch {
            val isNowSaved = repository.toggleSaveEdital(edital)
            // Update in current search results if present
            val currentState = _searchUiState.value
            if (currentState is SearchUiState.Success) {
                val updatedList = currentState.result.editais.map {
                    if (it.id == edital.id) it.copy(isSaved = isNowSaved) else it
                }
                _searchUiState.value = SearchUiState.Success(
                    currentState.result.copy(editais = updatedList)
                )
            }
        }
    }

    fun openNotesDialog(edital: EditalPublico) {
        _editalForNotes.value = edital
    }

    fun closeNotesDialog() {
        _editalForNotes.value = null
    }

    fun saveNotes(editalId: String, notes: String) {
        viewModelScope.launch {
            repository.updateAnotacoes(editalId, notes)
            closeNotesDialog()
        }
    }

    fun showJson(json: String) {
        _activeJsonView.value = json
    }

    fun dismissJson() {
        _activeJsonView.value = null
    }
}
