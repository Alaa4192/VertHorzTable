package com.akh.verthorztable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akh.verthorztablelibrary.Row
import com.akh.verthorztablelibrary.Table
import com.akh.verthorztablelibrary.TableCell
import com.akh.verthorztablelibrary.TableRow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadTable()
    }

    private fun loadTable() {
        val headers: ArrayList<String?> =
            arrayListOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K")
        table.loadHeaders(headers)

        val size = headers.size

        val rows = arrayListOf<Row>()
        for (rowIndex in 1..30) {
            val cells = arrayListOf<TableCell>()

            for (cellIndex in 1 until size) {
                cells.add(
                    TableCell(
                        id = cellIndex,
                        value = cellIndex.toString()
                    )
                )
            }

            val row = TableRow(
                id = rowIndex,
                cell = TableCell(id = 0, value = "0", isClickable = true),
                cells = cells
            )

            rows.add(row)
        }

        table.loadTable(
            Table(
                columnsCount = headers.size,
                rows = rows,
                offset = 0,
                total = rows.size
            )
        )

        table.setTableListeners(
            onItemClick = { i ->
                Log.d("koko", "onItemClick: $i")
            },
            onCellClick = { i, j ->
                Log.d("koko", "onCellClick: $i $j")
            }
        )
    }
}