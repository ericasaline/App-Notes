package com.app.notes.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    private fun addNota() {
        binding.btnAdicionar.setOnClickListener {
            startActivity(Intent(this@NotesActivity, EditNoteActivity::class.java))
        }
    }

    private fun listar() {
        viewModel.notes.observe(this) { notes ->

        }
    }
}