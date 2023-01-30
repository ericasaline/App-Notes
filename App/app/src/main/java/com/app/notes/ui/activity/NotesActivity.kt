package com.app.notes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.R
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ActivityNotesBinding
import com.app.notes.hideSoftKeyboard
import com.app.notes.showToast
import com.app.notes.ui.adapter.NotesAdapter
import com.app.notes.ui.dialog.BottomSheetModal
import com.app.notes.ui.viewmodel.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NotesAdapter
    private val viewModel: NotesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.showAll()
        createNote()
        showNotes()
        observeSearch()
        listenToSearchInput()
        onClickBack()
    }

    override fun onResume() {
        super.onResume()

        viewModel.showAll()
        clearScreen()
    }

    private fun createNote() {
        binding.btnAdicionar.setOnClickListener {
            startActivity(
                Intent(
                    this@NotesActivity, EditNoteActivity::class.java
                )
            )
        }
    }

    private fun createAdapter(notes: List<NoteModel>) {
        adapter = NotesAdapter(notes)
        binding.recyclerNotas.adapter = adapter
        binding.txtInfo.visibility = View.GONE
        binding.textInputLayout.visibility = View.VISIBLE
        binding.recyclerNotas.visibility = View.VISIBLE

        adapter.onClickItem = { id ->
            val intent = Intent(
                this@NotesActivity, EditNoteActivity::class.java
            )
            intent.putExtra("Note", id)
            startActivity(intent)
        }
    }

    private fun showNotes() {
        viewModel.notes.observe(this) { notes ->
            if(notes.isEmpty()) {
                binding.txtInfo.visibility = View.VISIBLE
                binding.btnAdicionar.visibility = View.VISIBLE
                binding.textInputLayout.visibility = View.GONE
                binding.recyclerNotas.visibility = View.GONE
                binding.btnBack.visibility = View.GONE
                binding.results.visibility = View.GONE
            } else {
                createAdapter(notes)
            }
        }
    }

    private fun listenToSearchInput() {
        binding.inputEditText.setOnKeyListener { _, keycode, keyevent ->
            if(keycode == KeyEvent.KEYCODE_ENTER && keyevent.action == KeyEvent.ACTION_UP) {
                hideSoftKeyboard()
                val query = binding.inputEditText.text.toString()
                if(query.isEmpty()) {
                    showToast(getString(R.string.info_campo_busca_vazio), this)
                } else {
                    viewModel.search(query)
                }
            }
            false
        }
    }

    private fun observeSearch() {
        viewModel.searchWithQuery.observe(this) { notes ->
            if(notes.isEmpty()) {
               binding.inputEditText.text?.clear()
               BottomSheetModal().show(supportFragmentManager, "EMPTY")
            } else {
                binding.btnAdicionar.visibility = View.GONE
                binding.btnBack.visibility = View.VISIBLE
                binding.results.visibility = View.VISIBLE
                binding.results.text = resources.getQuantityString(R.plurals.resultados, notes.size, notes.size)
                createAdapter(notes)
            }
        }
    }

    private fun onClickBack() {
        binding.btnBack.setOnClickListener {
            clearScreen()
        }
    }

    private fun clearScreen() {
        binding.inputEditText.text?.clear()
        binding.btnBack.visibility = View.GONE
        binding.results.visibility = View.GONE
        binding.btnAdicionar.visibility = View.VISIBLE
        viewModel.showAll()
        binding.inputEditText.text?.clear()
        showNotes()
    }
}