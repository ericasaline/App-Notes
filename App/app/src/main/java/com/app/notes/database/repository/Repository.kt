package com.app.notes.database.repository

import com.app.notes.database.entity.NoteModel

interface Repository {
    suspend fun insert(note: NoteModel): Boolean
    suspend fun update(id: String, titulo: String, conteudo: String): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun showNote(id: String): NoteModel?
    suspend fun showAll(): List<NoteModel>
}