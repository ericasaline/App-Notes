package com.app.notes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.app.notes.R
import com.app.notes.databinding.ActivityNotesBinding
import com.app.notes.hideSoftKeyboard
import com.app.notes.showToast
import com.app.notes.ui.adapter.NoteAdapter
import com.app.notes.ui.dialog.BottomSheetModalFragment
import com.app.notes.ui.fragment.SearchResultFragment
import com.app.notes.ui.fragment.SearchResultFragment.Companion.TAG
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

//        //Teste
//        adapter.adapterUpdate(viewModel.reloadNotes())

        viewModel.reload()
        realodNotes()
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
                binding.fragmentContainer.visibility = View.GONE
            } else {
                adapter.adapterUpdate(notes)
                binding.txtInfo.visibility = View.GONE
                binding.fragmentContainer.visibility = View.GONE
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

                var query = binding.inputEditText.text.toString()

                if(query.isEmpty()) {
                    showToast(getString(R.string.info_campo_busca_vazio), this)
                } else {
                    query = query.replace("\\s".toRegex(), "")
                    viewModel.search(query)
                    binding.inputEditText.text?.clear()
                }
            }

            false
        }
    }

    private fun observeSearch() {
        viewModel.notesResult.observe(this) { notes ->
            if(notes.isEmpty()) {
               BottomSheetModalFragment().show(supportFragmentManager, "EMPTY")
            } else {

                //Fragment

                println("========================================NOTAS: $notes")
//                adapter = NoteAdapter(notes)
//                binding.recyclerNotas.adapter = adapter
                binding.textInputLayout.visibility = View.GONE
                binding.recyclerNotas.visibility = View.GONE
                binding.btnAdicionar.visibility = View.GONE
                binding.txtInfo.visibility = View.GONE
                binding.fragmentContainer.visibility = View.VISIBLE


                supportFragmentManager.commit {
                    replace(R.id.fragment_container, SearchResultFragment.newInstance(), TAG)
                }

//                adapter.onClickItem = { id ->
//                    val intent = Intent(this@NotesActivity, EditNoteActivity::class.java)
//                    intent.putExtra("Note", id)
//                    startActivity(intent)
//                }


            }
        }
    }

    private fun realodNotes() {
        viewModel.newList.observe(this) { notes ->
            if(notes.isEmpty()) {
                binding.txtInfo.visibility = View.VISIBLE
                binding.textInputLayout.visibility = View.GONE
                binding.recyclerNotas.visibility = View.GONE
                binding.fragmentContainer.visibility = View.GONE
            } else {
                adapter = NoteAdapter(notes)

                binding.recyclerNotas.adapter = adapter
                binding.txtInfo.visibility = View.GONE
                binding.fragmentContainer.visibility = View.GONE
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
}