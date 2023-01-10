package com.app.notes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.notes.database.entity.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(note: NoteModel)

    @Query("UPDATE NoteModel SET titulo = :titulo AND conteudo = :conteudo WHERE id = :id")
    suspend fun atualizar(id: String, titulo: String, conteudo: String)

    @Query("DELETE FROM NoteModel WHERE id = :id")
    suspend fun deletar(id: String)

    @Query("SELECT * FROM NoteModel")
    fun listar(): Flow<List<NoteModel>>
}