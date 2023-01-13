package com.app.notes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: String,
    @ColumnInfo(name = "TITULO")
    val titulo: String,
    @ColumnInfo(name = "CONTEUDO")
    val conteudo: String
)