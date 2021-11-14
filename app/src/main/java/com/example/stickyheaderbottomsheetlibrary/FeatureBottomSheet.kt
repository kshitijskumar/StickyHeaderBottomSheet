package com.example.stickyheaderbottomsheetlibrary

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stickyheaderbottomsheet.StickyHeaderBottomSheetDialogFragment
import java.lang.IllegalStateException

class FeatureBottomSheet : StickyHeaderBottomSheetDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        view?.findViewById<Button>(R.id.someBtn)?.setOnClickListener {
            Toast.makeText(requireContext(), "some button", Toast.LENGTH_LONG).show()
        }
        view?.findViewById<ConstraintLayout>(R.id.header)?.setOnClickListener {
            Toast.makeText(requireContext(), "header", Toast.LENGTH_LONG).show()
        }

        val featureAdapter = FeatureAdapter()

        view?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = featureAdapter
        }

        featureAdapter.submitList(listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16", "Item 17", "Item 18", "Item 19", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9"))
//        featureAdapter.submitList(listOf("Item 1", "Item 2"))
//        updateBottomSheetHeight(false)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_feature_bottom_sheet
    }

    override fun getHeaderView(): View {
        return view?.findViewById<ConstraintLayout>(R.id.header) ?: throw IllegalStateException("Header view not found")
    }

    override fun getContentRecyclerView(): RecyclerView {
        return view?.findViewById<RecyclerView>(R.id.recyclerView) ?: throw IllegalStateException("Recyclerview not found")
    }
}