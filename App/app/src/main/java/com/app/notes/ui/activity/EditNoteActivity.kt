package com.app.notes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.R
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ActivityEditNoteBinding
import com.app.notes.databinding.DialogInfoBinding
import com.app.notes.hideSoftKeyboard
import com.app.notes.showToast
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

        init()
        observeNote()
        onClickBack()
        menu()
        checkDelete()
        checkUpdate()
        checkInsert()
    }

    private fun init() {
        intent.getStringExtra("Note")?.let { noteId -> id = noteId }
        viewModel.showNote(id)
        binding.editTitulo.requestFocus()
    }

    private fun menu() {
        binding.toolbar.setOnMenuItemClickListener { option ->
            when(option.itemId) {
                R.id.menu_salvar -> {
                    if(checkNote) {
                        update()
                    } else {
                        insert()
                    }
                    clearFocus()
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
        val view = DialogInfoBinding.inflate(layoutInflater)
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

    private fun checkDelete() {
        viewModel.deleteStatus.observe(this) { status ->
            if(status) {
                showToast(getString(R.string.info_delecao_sucesso), this)
            } else {
                showToast(getString(R.string.info_delecao_falha), this)
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

    private fun emptyVerify(): Boolean {
        return (binding.editTitulo.text.toString().isEmpty() &&
                binding.editConteudo.text.toString().isEmpty())
    }

    private fun insert() {
        val emptyField = emptyVerify()
        if(emptyField) {
            showToast(getString(R.string.campo_vazio), this)
        } else {
            viewModel.insert(
                NoteModel(
                    id,
                    binding.editTitulo.text.toString(),
                    binding.editConteudo.text.toString()
                )
            )
        }
    }

    private fun checkInsert() {
        viewModel.insertStatus.observe(this) { status ->
            if(status) {
                checkNote = true
                showToast(getString(R.string.info_insercao_sucesso), this)
            } else {
                showToast(getString(R.string.info_insercao_falha), this)
            }
        }
    }

    private fun update() {
        val emptyField = emptyVerify()
        if(emptyField) {
            showToast(getString(R.string.campo_vazio), this)
        } else {
            viewModel.update(
                NoteModel(
                    id,
                    binding.editTitulo.text.toString(),
                    binding.editConteudo.text.toString()
                )
            )
        }
    }

    private fun checkUpdate() {
        viewModel.updateStatus.observe(this) { status ->
            if(status) {
                showToast(getString(R.string.info_atualizacao_sucesso), this)
            } else {
                showToast(getString(R.string.info_atualizacao_falha), this)
            }
        }
    }

    private fun clearFocus() {
        hideSoftKeyboard()
        binding.editTitulo.clearFocus()
        binding.editConteudo.clearFocus()
    }
}