package com.app.notes.database.repository

import com.app.notes.database.entity.NoteModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun inserir(note: NoteModel): Boolean
    suspend fun atualizar(id: String, titulo: String, conteudo: String): Boolean
    suspend fun deletar(id: String): Boolean
    fun listar(): Flow<List<NoteModel>>
}