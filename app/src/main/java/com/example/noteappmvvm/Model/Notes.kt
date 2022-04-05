package com.example.noteappmvvm.Model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Notes_Database")
class Notes {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @JvmField
    @ColumnInfo(name = "notes_title")
    var notesTitle: String? = null

    @JvmField
    @ColumnInfo(name = "notes_subtitle")
    var notesSubtitle: String? = null

    @JvmField
    @ColumnInfo(name = "notes_date")
    var notesDate: String? = null

    @JvmField
    @ColumnInfo(name = "notes")
    var notes: String? = null

    @JvmField
    @ColumnInfo(name = "notes_priority")
    var notesPriority: String? = null
}