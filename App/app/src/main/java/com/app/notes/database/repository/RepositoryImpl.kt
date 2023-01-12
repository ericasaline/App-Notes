package com.app.notes.database.repository

import com.app.notes.database.dao.NoteRoom
import com.app.notes.database.entity.NoteModel

class RepositoryImpl(private val room: NoteRoom): Repository {
    override suspend fun insert(note: NoteModel): Boolean {
        return try {
            room.insert(note)
            true
        } catch(e: Exception) {
            false
        }
    }

    override suspend fun update(id: String, titulo: String, conteudo: String): Boolean {
        return try {
            room.update(id, titulo, conteudo)
            true
        } catch(e: Exception) {
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            room.delete(id)
            true
        } catch(e: Exception) {
            false
        }
    }

    override suspend fun showAll(): List<NoteModel> {
        return try {
            room.showAll()
        } catch(e: Exception) {
            emptyList()
        }
    }

    override suspend fun showNote(id: String): NoteModel? {
        return room.showNote(id)
    }
}