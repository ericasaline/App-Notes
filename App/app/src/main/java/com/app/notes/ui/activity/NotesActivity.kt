package com.app.notes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.R
import com.app.notes.databinding.ActivityNotesBinding
import com.app.notes.hideSoftKeyboard
import com.app.notes.showToast
import com.app.notes.ui.adapter.NoteAdapter
import com.app.notes.ui.viewmodel.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.showAll()
        createNote()
        showNotes()
        observeSearch()
        listenToSearchInput()
    }

    override fun onResume() {
        super.onResume()

        //Teste
        viewModel.showAll()
        showNotes()
    }

    private fun createNote() {
        binding.btnAdicionar.setOnClickListener {
            startActivity(Intent(this@NotesActivity, EditNoteActivity::class.java))
        }
    }

    private fun showNotes() {
        viewModel.notes.observe(this) { notes ->
            if(notes.isEmpty()) {
                binding.txtInfo.visibility = View.VISIBLE
                binding.textInputLayout.visibility = View.GONE
                binding.recyclerNotas.visibility = View.GONE
            } else {
                adapter = NoteAdapter(notes)
                binding.recyclerNotas.adapter = adapter
                binding.txtInfo.visibility = View.GONE
                binding.textInputLayout.visibility = View.VISIBLE
                binding.recyclerNotas.visibility = View.VISIBLE

                adapter.onClickItem = { id ->
                    val intent = Intent(this@NotesActivity, EditNoteActivity::class.java)
                    intent.putExtra("Note", id)
                    startActivity(intent)
                }
            }
        }
    }

    private fun listenToSearchInput() {
        binding.inputEditText.setOnKeyListener { _, keycode, keyevent ->
            if(keycode == KeyEvent.KEYCODE_ENTER && keyevent.action == KeyEvent.ACTION_UP) {
                hideSoftKeyboard()
                viewModel.search(binding.inputEditText.text.toString())
                binding.inputEditText.text?.clear()
            }
            false
        }
    }

    private fun observeSearch() {
        viewModel.notesResult.observe(this) { notes ->
            if(notes.isEmpty()) {
                binding.recyclerNotas.visibility = View.GONE
                showToast(getString(R.string.info_nenhum_resultado), this)
            } else {
                adapter = NoteAdapter(notes)
                binding.recyclerNotas.adapter = adapter
                binding.recyclerNotas.visibility = View.VISIBLE

                adapter.onClickItem = { id ->
                    val intent = Intent(this@NotesActivity, EditNoteActivity::class.java)
                    intent.putExtra("Note", id)
                    startActivity(intent)
                }
            }
        }
    }
}