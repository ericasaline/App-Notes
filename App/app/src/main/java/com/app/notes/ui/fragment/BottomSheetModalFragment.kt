package com.app.notes.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.app.notes.databinding.FragmentBottomSheetModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetModalFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetModalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetModalBinding.inflate(
            layoutInflater, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickClose()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun onClickClose() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomsheet = findViewById<View>(
                    com.google.android.material.R.id.design_bottom_sheet
                ) as FrameLayout

                bottomsheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}