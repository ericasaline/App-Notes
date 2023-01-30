package com.app.notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notes.database.entity.NoteModel
import com.app.notes.database.repository.Repository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: Repository): ViewModel() {

    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes: LiveData<List<NoteModel>> get() = _notes

    private val _searchWithQuery = MutableLiveData<List<NoteModel>>()
    val searchWithQuery: LiveData<List<NoteModel>> get() = _searchWithQuery

    fun showAll() = viewModelScope.launch {
        val notes = repository.showAll()
        _notes.postValue(notes)
    }

    fun search(query: String) = viewModelScope.launch {
        val value = query.replace("\\s".toRegex(), "")
        val list = repository.showAll()
        val notes = list.toMutableList()
        notes.clear()
        notes.addAll(
            list.filter { note ->
                note.titulo.contains(value, true) ||
                note.conteudo.contains(value, true)
            }
        )
        _searchWithQuery.postValue(notes)
    }
}