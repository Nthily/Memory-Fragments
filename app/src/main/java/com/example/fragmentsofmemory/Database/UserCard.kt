package com.example.fragmentsofmemory.Database

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class UserCard(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "time") val time: String,
)
