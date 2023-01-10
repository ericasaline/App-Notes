package com.app.notes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class NoteModel(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "TITULO")
    val titulo: String,
    @ColumnInfo(name = "CONTEUDO")
    val conteudo: String
)