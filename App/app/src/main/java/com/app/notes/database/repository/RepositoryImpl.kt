package com.app.notes.database.repository

import com.app.notes.database.dao.NoteRoom
import com.app.notes.database.entity.NoteModel
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val room: NoteRoom): Repository {
    override suspend fun inserir(note: NoteModel): Boolean {
        return try {
            room.inserir(note)
            true
        } catch(e: Exception) {
            false
        }
    }

    override suspend fun atualizar(id: String, titulo: String, conteudo: String): Boolean {
        return try {
            room.atualizar(id, titulo, conteudo)
            true
        } catch(e: Exception) {
            false
        }
    }

    override suspend fun deletar(id: String): Boolean {
        return try {
            room.deletar(id)
            true
        } catch(e: Exception) {
            false
        }
    }

    override fun listar(): Flow<List<NoteModel>> {
        return room.listar()
    }
}