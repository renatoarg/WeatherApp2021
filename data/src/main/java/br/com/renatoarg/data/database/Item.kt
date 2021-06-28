package br.com.renatoarg.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "item_table")
class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null
) : Parcelable