package com.example.retrofit_l2_t2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofit_l2_t2.model.CardItem

@Database(entities = [CardItem::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract val dao: CardDao

    companion object {
        @Volatile
        private var instance: CardDatabase? = null

        operator fun invoke(context: Context): CardDatabase {
            return instance ?: synchronized(Any()) {
                instance ?: createDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun createDatabase(context: Context): CardDatabase {
            return Room.databaseBuilder(
                context,
                CardDatabase::class.java,
                "Card.db"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }
    }
}