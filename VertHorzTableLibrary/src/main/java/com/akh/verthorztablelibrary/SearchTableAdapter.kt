package com.akh.verthorztablelibrary

import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.jnet.apollo.ui.table.Row
import com.jnet.apollo.ui.table.TableRow


class SearchTableAdapter(var adapterListener: AdapterListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var columnsCount: Int = 0
    var items = ArrayList<Row>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    companion object {
        const val ROW_CELLS = 1
        const val ROW_DATA = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TableRow -> ROW_CELLS
            else -> {
                super.getItemViewType(position)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return when (getItemViewType(position)) {
            ROW_CELLS -> (items[position] as TableRow).id.toLong()
            else -> super.getItemId(position)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_table_row_item, parent, false)

        initRow(itemView = itemView as LinearLayout, columnsCount)

        return RowViewHolder(itemView, adapterListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ROW_CELLS -> {
                (holder as RowViewHolder).bind(items[position] as TableRow, position)
            }
        }
        adapterListener?.onItemReveal(position)
    }

    private fun initRow(itemView: LinearLayout, columnsCount: Int) {
        itemView.removeAllViews()

        for (i in 0 until columnsCount) {
            itemView.addView(AppCompatTextView(itemView.context).apply {
                layoutParams =
                    LayoutParams(300, LayoutParams.MATCH_PARENT).apply {
                        setMargins(8)
                    }

                gravity = Gravity.CENTER
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                textSize = 12f
                ellipsize = TextUtils.TruncateAt.END
                maxLines = 2
            })
        }
    }

    class RowViewHolder(itemView: View, private val adapterListener: AdapterListener?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(tableRow: TableRow, position: Int) {
            (itemView as LinearLayout).apply {
//                if (position % 2 == 0) {
//                    setBackgroundResource(R.color.table_row_background_1)
//                } else {
//                    setBackgroundResource(R.color.table_row_background_2)
//                }

                val cells = tableRow.cells
                cells.forEachIndexed { index, tableCell ->
                    (this[index] as? AppCompatTextView)?.let {
                        it.text = tableCell.value?.let { cellValue ->
                            cellValue
                        } ?: run {
                            tableCell.emptyValueRes?.let { valueRes -> context.getString(valueRes) }
                                ?: ""
                        }

                        tableCell.value.string()
                        tableCell.iconRes?.let { iconRes ->
                            it.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconRes, 0)
                        } ?: run {
                            it.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        }

                        if (tableCell.isClickable) {
                            it.setOnClickListener {
                                adapterListener?.onCellClickListener(tableRow.id, tableCell.id)
                            }
                            it.setTextColor(context.getColor(R.color.blue))
                            it.typeface = Typeface.DEFAULT_BOLD
                        } else {
                            it.setOnClickListener(null)
                            it.setTextColor(context.getColor(R.color.black_A0))
                            it.typeface = Typeface.DEFAULT
                        }
                    }
                }

                setOnClickListener {
                    adapterListener?.onItemClick(tableRow.id)
                }
            }
        }
    }

}

interface AdapterListener {
    fun onItemReveal(position: Int)

    fun onItemClick(rowId: Int)

    fun onCellClickListener(rowId: Int, cellId: Int)
}