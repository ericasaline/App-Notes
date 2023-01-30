package com.app.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.notes.database.entity.NoteModel
import com.app.notes.databinding.ItemNoteBinding

class NoteAdapter(notes: List<NoteModel>)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val notes = notes.toMutableList()
    var onClickItem: (id: String) -> Unit = {}

    inner class ViewHolder(binding: ItemNoteBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val titulo = binding.txtTitulo
        private val conteudo = binding.txtConteudo

        fun bind(note: NoteModel) {
            titulo.text = note.titulo
            conteudo.text = note.conteudo

            itemView.setOnClickListener {
                onClickItem(note.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = notes[position]
        holder.bind(notes)
    }

    override fun getItemCount(): Int = notes.size

}