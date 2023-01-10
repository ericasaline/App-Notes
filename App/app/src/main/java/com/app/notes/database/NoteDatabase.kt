package com.app.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.notes.database.dao.NoteRoom
import com.app.notes.database.entity.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun dao(): NoteRoom
}