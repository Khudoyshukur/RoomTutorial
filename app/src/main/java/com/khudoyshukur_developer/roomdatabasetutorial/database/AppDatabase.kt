package com.khudoyshukur_developer.roomdatabasetutorial.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): StudentDao

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}