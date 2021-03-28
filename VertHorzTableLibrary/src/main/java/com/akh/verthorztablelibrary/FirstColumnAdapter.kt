package com.akh.verthorztablelibrary

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jnet.apollo.ui.table.Row
import com.jnet.apollo.ui.table.TableRow
import kotlinx.android.synthetic.main.layout_table_cell_item.view.*
import java.util.*

class FirstColumnAdapter(var adapterListener: AdapterListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<Row> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        const val ROW_CELLS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_table_cell_item, parent, false)

        return CellViewHolder(itemView, adapterListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CellViewHolder).bind(items[position] as TableRow)
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

    override fun getItemCount() = items.size

    class CellViewHolder(itemView: View, private val adapterListener: AdapterListener?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(row: TableRow) {
            itemView.cellTextView.text = row.cell.value

            if (row.cell.isClickable) {
                itemView.cellTextView.setOnClickListener {
                    Toast.makeText(itemView.context, "Clickkkkk", Toast.LENGTH_SHORT).show()
                    adapterListener?.onCellClickListener(row.id, row.cell.id)
                }
                itemView.cellTextView.setTextColor(itemView.context.getColor(R.color.blue))
                itemView.cellTextView.typeface = Typeface.DEFAULT_BOLD
            } else {
                itemView.setOnClickListener(null)
                itemView.cellTextView.setTextColor(itemView.context.getColor(R.color.black_A0))
                itemView.cellTextView.typeface = Typeface.DEFAULT
            }
        }
    }
}