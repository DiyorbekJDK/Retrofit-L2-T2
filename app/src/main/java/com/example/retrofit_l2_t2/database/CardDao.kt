package com.example.retrofit_l2_t2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.retrofit_l2_t2.model.CardItem
import com.example.retrofit_l2_t2.model.CardResponse

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCardToDatabase(cardItem: CardItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCardListToDatabase(cardList: List<CardItem>)

    @Query("SELECT * FROM CardTable ORDER BY id DESC")
    fun getAllCardsFromDatabase(): List<CardItem>

    @Update
    fun updateCardFromDatabase(cardItem: CardItem)

    @Delete
    fun deleteCardFromDatabase(cardItem: CardItem)
}