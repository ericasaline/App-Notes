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

    private val _newList = MutableLiveData<List<NoteModel>>()
    val newList: LiveData<List<NoteModel>> get() = _newList

    private val _notesResult = MutableLiveData<List<NoteModel>>()
    val notesResult: LiveData<List<NoteModel>> get() = _notesResult

    fun showAll() = viewModelScope.launch {
        val notes = repository.showAll()
        _notes.postValue(notes)
    }

    fun reload() = viewModelScope.launch {
        val newList = repository.showAll()
        _newList.postValue(newList)
    }

    fun search(query: String) = viewModelScope.launch {
        val list = repository.showAll()
        val newlist = list.toMutableList()
        newlist.clear()
        newlist.addAll(
            list.filter { note ->
                note.titulo.contains(query, true) ||
                note.conteudo.contains(query, true)
            }
        )
        _notesResult.postValue(newlist)
    }
}