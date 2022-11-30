package com.example.petfriends.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petfriends.data.local.database.dao.PetDao
import com.example.petfriends.data.local.database.dao.UserDao
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.entity.UserEntity

@Database(
    entities = [Pet::class , UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PetRoomDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: PetRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PetRoomDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PetRoomDatabase::class.java, "pet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}