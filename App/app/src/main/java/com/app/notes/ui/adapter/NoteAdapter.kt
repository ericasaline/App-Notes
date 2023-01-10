package com.app.notes.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ItemNoteBinding

class NoteAdapter(private val notes: List<NoteModel>)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

        inner class ViewHolder(binding: ItemNoteBinding)
            : RecyclerView.ViewHolder(binding.root) {

                private val titulo = binding.txtTitulo
                private val conteudo = binding.txtConteudo

                fun bind(note: NoteModel) {
                    titulo.text = note.titulo
                    conteudo.text = note.conteudo
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}