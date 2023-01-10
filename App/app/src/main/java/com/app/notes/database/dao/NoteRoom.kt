package com.app.notes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.notes.database.entity.NoteModel

@Dao
interface NoteRoom {
    // inserir
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(note: NoteModel)
    // atualizar
    // deletar
    // buscar todas
}