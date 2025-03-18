package com.example.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class FavCharDatabase : RoomDatabase() {
    abstract fun charDao(): CharactersDao

    companion object {
        @Volatile
        private var INSTANCE: FavCharDatabase? = null

        fun getInstance(context: Context): FavCharDatabase {
            return INSTANCE ?: synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes("rnmmobile".toCharArray())
                val factory = SupportFactory(passphrase)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavCharDatabase::class.java,
                    "favorite.db"
                ).fallbackToDestructiveMigration()
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}