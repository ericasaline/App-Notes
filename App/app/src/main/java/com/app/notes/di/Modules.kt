package com.app.notes.di

import androidx.room.Room
import com.app.notes.database.NoteDatabase
import com.app.notes.database.repository.Repository
import com.app.notes.database.repository.RepositoryImpl
import com.app.notes.ui.viewmodel.ViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            "DATABASE"
        ).build()
    }

    single {
        get<NoteDatabase>().dao()
    }
}

val dataModule = module {
    single<Repository> {
        RepositoryImpl(get())
    }
}

val viewModelModule = module {
    viewModel {
        ViewModel(get())
    }
}