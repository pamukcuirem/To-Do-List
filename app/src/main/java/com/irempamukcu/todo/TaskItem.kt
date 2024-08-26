package com.irempamukcu.todo

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "task_item_table")
data class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String? = null,
    @ColumnInfo(name = "completeDateString") var completeDateString: String? = null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {

    fun completeDate(): LocalDate? = completeDateString?.let {
        LocalDate.parse(it, dateFormatter)
    }

    fun dueTime(): LocalTime? = dueTimeString?.let {
        LocalTime.parse(it, timeFormatter)
    }

    fun isCompleted(): Boolean = completeDate() != null

    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24

    fun imageColor(context: Context): Int = ContextCompat.getColor(
        context, if (isCompleted()) R.color.blue else R.color.gray
    )

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}
