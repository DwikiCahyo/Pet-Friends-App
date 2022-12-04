package com.example.petfriends.data.local.database

import android.content.Context
import androidx.room.*
import com.example.petfriends.data.local.database.dao.PetDao
import com.example.petfriends.data.local.database.dao.UserDao
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.entity.UserEntity
import com.example.petfriends.data.utils.DateConverter

@Database(
    entities = [Pet::class , UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
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
                    .addTypeConverter(DateConverter::class)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}