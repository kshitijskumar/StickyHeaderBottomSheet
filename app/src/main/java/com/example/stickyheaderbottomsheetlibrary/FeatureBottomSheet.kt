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
            (it as Button).text = (it.text.toString().toInt() + 1).toString()
        }
        view?.findViewById<ConstraintLayout>(R.id.header)?.setOnClickListener {
            Toast.makeText(requireContext(), "header", Toast.LENGTH_LONG).show()
        }

        val featureAdapter = FeatureAdapter()

        view?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = featureAdapter
        }

        featureAdapter.submitList(listOf(
            "One thing, I don't know why",
            "It doesn't even matter how hard you try",
            "Keep that in mind, I designed this rhyme",
            "To remind myself of a time when I tried so hard",
            "In spite of the way you were mockin' me",
            "Actin' like I was part of your property",
            "Remembering all the times you fought with me",
            "I'm surprised it got so far",
            "Things aren't the way they were before",
            "You wouldn't even recognize me anymore",
            "Not that you knew me back then",
            "But it all comes back to me in the end",
            "You kept everything inside",
            "And even though I tried, it all fell apart",
            "What it meant to me will eventually",
            "Be a memory of a time when I"
        ))
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