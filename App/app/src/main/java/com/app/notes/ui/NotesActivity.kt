package com.app.notes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ActivityNotesBinding
import com.app.notes.ui.viewmodel.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private val viewModel: ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNota()

        teste()
        testeStatus()
    }

    private fun addNota() {
        binding.btnAdicionar.setOnClickListener {
            startActivity(Intent(this@NotesActivity, EditNoteActivity::class.java))
        }
    }

    fun teste() {
        var testeModel = NoteModel("2", "Titulo", "Conteudo")
        viewModel.inserir(testeModel)
    }

    fun testeStatus() {
        viewModel.iserirStatus.observe(this) {
            if (it) {
                Log.i("STATUS", "SUCESSO")
            } else {
                Log.i("STATUS", "ERRO")
            }
        }
    }
}