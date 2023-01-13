package com.app.notes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.notes.database.entity.NoteModel

@Dao
interface NoteRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteModel)

    @Update
    suspend fun update(note: NoteModel)

    @Query("DELETE FROM NoteModel WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    suspend fun showNote(id: String): NoteModel?

    @Query("SELECT * FROM NoteModel")
    suspend fun showAll(): List<NoteModel>
}