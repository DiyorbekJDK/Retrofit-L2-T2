package com.example.retrofit_l2_t2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CardTable")
data class CardItem(
    val bankName: String,
    val cardHolderName: String,
    val cvv: String,
    val data1: String,
    val data2: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: String
) : Parcelable