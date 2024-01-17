@file:Suppress("DEPRECATED_ANNOTATION")

package com.takehomechallenge.sulfa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "favorite")
@Parcelize
data class CharacterFavorite(

    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int?,

    @field:ColumnInfo(name = "name")
    val name: String?,

    @field:ColumnInfo(name = "species")
    val species: String?,

    @field:ColumnInfo(name = "gender")
    val gender: String?,

    @field:ColumnInfo(name = "origin")
    val origin: String? = null,

    @field:ColumnInfo(name = "location")
    val location: String? =null,

    @field:ColumnInfo(name = "image")
    val image: String? = null

) : Parcelable