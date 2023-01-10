package com.app.notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.notes.database.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notes.database.entity.NoteModel
import com.app.notes.di.viewModelModule
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository): ViewModel() {

    private val _inserirStatus = MutableLiveData<Boolean>()
    val iserirStatus: LiveData<Boolean> get() = _inserirStatus

    fun inserir(note: NoteModel) = viewModelScope.launch {
        runCatching { repository.inserir(note) }
            .onSuccess { _inserirStatus.postValue(true) }
            .onFailure { _inserirStatus.postValue(false) }
    }

    fun atualizar() {

    }

    fun deletar() {

    }

    fun listar() {

    }

}