package com.app.notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notes.database.entity.NoteModel
import com.app.notes.database.repository.Repository
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository): ViewModel() {

    private val _insertStatus = MutableLiveData<Boolean>()
    val insertStatus: LiveData<Boolean> get() = _insertStatus

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> get() = _updateStatus

    private val _deleteStatus = MutableLiveData<Boolean>()
    val deleteStatus: LiveData<Boolean> get() = _deleteStatus

    private val _note = MutableLiveData<NoteModel?>()
    val note: LiveData<NoteModel?> get() = _note

    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes: LiveData<List<NoteModel>> get() = _notes

    private val _notesResult = MutableLiveData<List<NoteModel>>()
    val notesResult: LiveData<List<NoteModel>> get() = _notesResult

    private val _newList = MutableLiveData<List<NoteModel>>()
    val newList: LiveData<List<NoteModel>> get() = _newList

    fun insert(note: NoteModel) = viewModelScope.launch {
        val result = repository.insert(note)
        _insertStatus.postValue(result)
    }

    fun update(note: NoteModel) = viewModelScope.launch {
        val result = repository.update(note)
        _updateStatus.postValue(result)
    }

    fun delete(id: String) = viewModelScope.launch {
        val result = repository.delete(id)
        _deleteStatus.postValue(result)
    }

    fun showNote(id: String) = viewModelScope.launch {
        val note = repository.showNote(id)
        _note.postValue(note)
    }

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
                note.titulo.contains(query, true) || note.conteudo.contains(query, true)
            }
        )
        _notesResult.postValue(newlist)
    }
}