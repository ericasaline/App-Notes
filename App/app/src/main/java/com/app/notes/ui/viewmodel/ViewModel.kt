package com.app.notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.notes.database.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notes.database.entity.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository): ViewModel() {

    private val _inserirStatus = MutableLiveData<Boolean>()
    val iserirStatus: LiveData<Boolean> get() = _inserirStatus

    private val _atualizarStatus = MutableLiveData<Boolean>()
    val atualizarStatus: LiveData<Boolean> get() = _atualizarStatus

    private val _deletarStatus = MutableLiveData<Boolean>()
    val deletarStatus: LiveData<Boolean> get() = _deletarStatus

    private val _notes = MutableLiveData<Flow<List<NoteModel>>>()
    val notes: LiveData<Flow<List<NoteModel>>> get() = _notes

    fun inserir(note: NoteModel) = viewModelScope.launch {
        val result = repository.inserir(note)
        _inserirStatus.postValue(result)
    }

    fun atualizar(id: String, titulo: String, conteudo: String) = viewModelScope.launch {
        val result = repository.atualizar(id, titulo, conteudo)
        _atualizarStatus.postValue(result)
    }

    fun deletar(id: String) = viewModelScope.launch {
        val result = repository.deletar(id)
        _deletarStatus.postValue(result)
    }

    fun listar() {
        val notes = repository.listar()
        _notes.postValue(notes)
    }

}