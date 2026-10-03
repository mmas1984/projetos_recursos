package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EditalDao {
    @Query("SELECT * FROM saved_editais ORDER BY savedAtTimestamp DESC")
    fun getAllSavedEditais(): Flow<List<EditalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEdital(edital: EditalEntity)

    @Query("DELETE FROM saved_editais WHERE id = :id")
    suspend fun deleteEditalById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_editais WHERE id = :id)")
    fun isEditalSavedFlow(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_editais WHERE id = :id)")
    suspend fun isEditalSaved(id: String): Boolean

    @Query("UPDATE saved_editais SET anotacoes = :anotacoes WHERE id = :id")
    suspend fun updateAnotacoes(id: String, anotacoes: String)

    // Search History
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 30")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(entry: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()
}
