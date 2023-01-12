package com.app.notes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.notes.database.entity.NoteModel

@Dao
interface NoteRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteModel)

    @Query("UPDATE NoteModel SET titulo = :titulo AND conteudo = :conteudo WHERE id = :id")
    suspend fun update(id: String, titulo: String, conteudo: String)

    @Query("DELETE FROM NoteModel WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    suspend fun showNote(id: String): NoteModel?

    @Query("SELECT * FROM NoteModel")
    suspend fun showAll(): List<NoteModel>
}