package com.akh.verthorztablelibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_pagination_table.view.*

class PaginationTableView(
    context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    constructor(context: Context?) : this(context, null, 0, 0)

    private val adapter by lazy {
        SearchTableAdapter()
    }

    private val firstColumnAdapter by lazy {
        FirstColumnAdapter()
    }

    private var table: Table? = null

    companion object {
        private const val LOADING_OFFSET = 12
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_pagination_table, this)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PaginationTableView.adapter
        }

        firstColumnRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PaginationTableView.firstColumnAdapter
        }

        val detector =
            GestureDetector(context, MyGestureListener(object : MyGestureListener.Listener {
                override fun onScroll(distanceX: Float, distanceY: Float) {
                    recyclerView.scrollBy(0, distanceY.toInt())
                    firstColumnRecyclerView.scrollBy(0, distanceY.toInt())
                    horizontalView.scrollBy(distanceX.toInt(), 0)
                }
            }))

//        focusView.setOnTouchListener { v, event ->
//            // Log.d("koko", "setOnTouchListener: focusView")
//            detector.onTouchEvent(event)
//        }

//        recyclerView.setOnTouchListener { v, event ->
//            // Log.d("koko", "setOnTouchListener: recyclerView")
//            false
//        }


//        horizontalView.postDelayed({
//            horizontalView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
//        }, 10)


        myFrameLayout.setOnTouchListener { _, event ->
            Log.d("koko", "setOnTouchListener: myFrameLayout")
            detector.onTouchEvent(event)
        }

    }

    fun loadHeaders(headers: ArrayList<Int?>) {
        headersContainer.removeAllViews()

        headers.subList(1, headers.size).forEach {
            headersContainer.addView(AppCompatTextView(context).apply {
                layoutParams = LayoutParams(300, LayoutParams.MATCH_PARENT).apply {
                    setPadding(8)
                }
                text = it?.let { context.getString(it) } ?: ""
                gravity = Gravity.CENTER_VERTICAL
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                typeface = Typeface.DEFAULT_BOLD
                textSize = 12f
            })
        }

        firstColumnTextView.text = headers.first()?.let { context.getString(it) } ?: run { "" }
    }


    @JvmName("loadHeaders1")
    fun loadHeaders(headers: ArrayList<String?>) {
        headersContainer.removeAllViews()

        headers.subList(1, headers.size).forEach {
            headersContainer.addView(AppCompatTextView(context).apply {
                layoutParams = LayoutParams(300, LayoutParams.MATCH_PARENT).apply {
                    setMargins(8)
                }
                text = it ?: ""
                gravity = Gravity.CENTER_VERTICAL
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                typeface = Typeface.DEFAULT_BOLD
                textSize = 12f
            })
        }

        firstColumnTextView.text = headers.first() ?: ""
    }

    fun loadTable(table: Table) {
        this.table = table
        adapter.columnsCount = table.columnsCount - 1
        adapter.items = table.rows

        firstColumnAdapter.items = table.rows

        totalTextView.text = table.total.toString()
        currentTextView.text = table.rows.size.toString()
    }

    fun setTableListeners(
        onPage: ((Int) -> Unit)? = null,
        onItemClick: ((Int) -> Unit)? = null,
        onCellClick: ((Int, Int) -> Unit)? = null
    ) {
        val adapterListener = object : AdapterListener {
            override fun onItemReveal(position: Int) {
                if ((position + LOADING_OFFSET) == table?.rows?.size) {
                    onPage?.invoke(table?.offset ?: 0)
                }
            }

            override fun onItemClick(rowId: Int) = onItemClick?.invoke(rowId) ?: Unit

            override fun onCellClickListener(rowId: Int, cellId: Int) =
                onCellClick?.invoke(rowId, cellId) ?: Unit
        }
        adapter.adapterListener = adapterListener
        firstColumnAdapter.adapterListener = adapterListener
    }

    fun isFooterEnabled(isFooterEnabled: Boolean) {
        tableFooter.showOrGone(isFooterEnabled)
    }


    class MyGestureListener(val listener: Listener) : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            // Log.d("PaginationTableView", "onScroll: distanceX = $distanceX, distanceY = $distanceY")
            listener.onScroll(distanceX, distanceY)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return super.onSingleTapUp(e)
        }

        interface Listener {
            fun onScroll(distanceX: Float, distanceY: Float)
        }
    }
}