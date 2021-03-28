package com.jnet.apollo.ui.table

import java.io.Serializable

data class Table(
    var offset: Int = 0,
    val rows: ArrayList<Row> = ArrayList(),
    var columnsCount: Int = 0,
    var total: Int = 0
) : Serializable

interface Row

data class TableRow(
    val id: Int,
    val cell: TableCell,
    val cells: ArrayList<TableCell>
) : Row, Serializable

data class DataTableRow(
    val id: Int,
    val key: String,
    val value: String
) : Row, Serializable

data class TableCell(
    val id: Int,
    val value: String? = null,
    val isClickable: Boolean = false,
    val iconRes: Int? = null,
    val emptyValueRes: Int? = null
)