package com.app.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.notes.R
import com.app.notes.databinding.ActivityEditNoteBinding
import com.app.notes.databinding.DialogConfirmBinding

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickBack()
        menu()
    }

    private fun menu() {
        binding.toolbar.setOnMenuItemClickListener { option ->
            when(option.itemId) {
                R.id.menu_salvar -> {
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
            // Deletar
            dialog.dismiss()
        }
    }
}
