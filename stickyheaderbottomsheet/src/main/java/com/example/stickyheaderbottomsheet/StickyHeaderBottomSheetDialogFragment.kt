package com.example.stickyheaderbottomsheet

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @author Kshitij Kumar
 *
 * Bottom Sheet Dialog Fragment with a sticky header and a recycler view content which expands while scrolling the
 * content recycler view and the header sticks on the top of the window when the bottom sheet expands completely
 */
abstract class StickyHeaderBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private val viewStub by lazy {
        view?.findViewById<ViewStub>(R.id.viewStub)
    }
    private lateinit var headerView: View
    private lateinit var contentRecyclerView: RecyclerView

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_sticky_header, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheetFrameLayout = (it as BottomSheetDialog).findViewById<FrameLayout>(R.id.design_bottom_sheet)
            bottomSheetFrameLayout?.let { fl ->
                bottomSheetBehavior = BottomSheetBehavior.from(fl)
                bottomSheetBehavior?.peekHeight = (0.5 * getWindowsHeight()).toInt()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceViewStubWithContentView()
        setupHeaderAndContent()
    }

    private fun replaceViewStubWithContentView() {
        viewStub?.layoutResource = getContentView()
        viewStub?.inflate()
    }

    private fun getWindowsHeight(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun setupHeaderAndContent() {
        headerView = getHeaderView()
        contentRecyclerView = getContentRecyclerView()
        updateBottomSheetHeight(true)
        setupBottomSheetScrollBehavior()
    }

    private fun setupBottomSheetScrollBehavior() {
        contentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                bottomSheetBehavior?.let {
                    if (it.state == STATE_EXPANDED) {
                        it.isDraggable = false
                    }
                    val layoutManager = recyclerView.layoutManager
                    val firstVisibleItemPosition = getFirstVisibleItemPosition(layoutManager)
                    if (firstVisibleItemPosition == 0) {
                        it.isDraggable = true
                    }
                }
            }
        })
        setupTouchListenerOnHeader()
    }

    private fun setupTouchListenerOnHeader() {
        headerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    bottomSheetBehavior?.isDraggable = true
                } else if (event?.action == MotionEvent.ACTION_UP) {
                    bottomSheetBehavior?.isDraggable = bottomSheetBehavior?.state != STATE_EXPANDED
                    v?.performClick()
                }
                return true
            }
        })
    }


    private fun getFirstVisibleItemPosition(layoutManager: RecyclerView.LayoutManager?): Int {
        return  when (layoutManager) {
            is GridLayoutManager -> layoutManager.findFirstCompletelyVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findFirstCompletelyVisibleItemPosition()
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * This enables/disables the bottom sheet to expand while scrolling the recycler view.
     * @param shouldMakeScrollable true, if you want the bottom sheet to expand while scrolling recyclerview,
     * false to keep the bottom sheet fixed
     */
    protected fun updateBottomSheetHeight(shouldMakeScrollable: Boolean) {
        handler.postDelayed({
            val recyclerViewNewHeight = if (shouldMakeScrollable) {
                val headerHeight = headerView.height
                getWindowsHeight() - headerHeight
            } else {
                RecyclerView.LayoutParams.WRAP_CONTENT
            }

            val layoutParams = contentRecyclerView.layoutParams
            layoutParams.height = recyclerViewNewHeight
            contentRecyclerView.layoutParams = layoutParams

        }, 100)
    }

    /**
     * Returns the root layout file for the bottom sheet.
     * The entire layout should be divided into 2 parts, header and content recycler view
     * @return layout resource file: Int
     */
    abstract fun getContentView() : Int

    /**
     * Returns the view acting as the header for the bottom sheet. This sticks on the top of the screen
     * @return view: View
     */
    abstract fun getHeaderView() : View

    /**
     * Returns the recycler view that acts as a content of the bottom sheet
     * @return recyclerview: RecyclerView
     */
    abstract fun getContentRecyclerView() : RecyclerView
}