package com.app.notes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.R
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ActivityEditNoteBinding
import com.app.notes.databinding.DialogConfirmBinding
import com.app.notes.ui.viewmodel.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private var id: String = UUID.randomUUID().toString()
    private val viewModel: ViewModel by viewModel()
    private var checkNote = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra("Note")?.let { noteId ->
            id = noteId
        }

        viewModel.showNote(id)

        observeNote()
        onClickBack()
        menu()
        checkDelete()
        checkUpdate()
        checkInsert()
    }

    private fun menu() {
        binding.toolbar.setOnMenuItemClickListener { option ->
            when(option.itemId) {
                R.id.menu_salvar -> {
                    if(checkNote) {
                        // update()
                    } else {
                        insert()
                    }
                    true
                }

                R.id.menu_excluir -> {
                    showDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun onClickBack() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showDialog() {
        val dialog: AlertDialog
        val view = DialogConfirmBinding.inflate(layoutInflater)
        val build = AlertDialog.Builder(this, R.style.AlertDialog)

        build.setView(view.root)
        dialog = build.create()
        dialog.show()

        view.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        view.btnDeletar.setOnClickListener {
            viewModel.delete(id)
            dialog.dismiss()
            finish()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun checkDelete() {
        viewModel.deleteStatus.observe(this) { status ->
            if(status) {
                toast(getString(R.string.info_delecao_sucesso))
            } else {
                toast(getString(R.string.info_delecao_falha))
            }
        }
    }

    private fun observeNote() {
        viewModel.note.observe(this) { note ->
            if (note != null) {
                checkNote = true

                binding.editTitulo.setText(note.titulo)
                binding.editConteudo.setText(note.conteudo)
            }
        }
    }

    private fun insert() {
        viewModel.insert(
            NoteModel(id, binding.editTitulo.text.toString(), binding.editConteudo.text.toString())
        )
    }

    private fun checkInsert() {
        viewModel.insertStatus.observe(this) { status ->
            if(status) {
                toast(getString(R.string.info_insercao_sucesso))
            } else {
                toast(getString(R.string.info_insercao_falha))
            }
        }
    }

    private fun update() {
        viewModel.update(
            id, binding.editTitulo.text.toString(), binding.editConteudo.text.toString()
        )
    }

    private fun checkUpdate() {
        viewModel.updateStatus.observe(this) { status ->
            if(status) {
                toast(getString(R.string.info_atualizacao_sucesso))
            } else {
                toast(getString(R.string.info_atualizacao_falha))
            }
        }
    }
}
