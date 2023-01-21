package com.app.notes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.notes.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "Search"
        fun newInstance(): SearchResultFragment {
            return SearchResultFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(
            layoutInflater, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backHome()
    }

    private fun backHome() {
        binding.btnBackHome.setOnClickListener {
            // Voltar para Home
        }
    }
}