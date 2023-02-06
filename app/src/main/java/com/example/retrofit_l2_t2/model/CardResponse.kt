package com.example.retrofit_l2_t2.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "CardListTable")
data class CardResponse(
    @Embedded
    val card: List<CardItem>
)