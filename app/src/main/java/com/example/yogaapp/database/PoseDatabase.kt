package com.example.yogaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yogaapp.model.Pose

@Database(entities = [Pose::class], version = 1, exportSchema = false)
abstract class PoseDatabase: RoomDatabase() {

    abstract val poseDao: PoseDao

    companion object {

        @Volatile
        private var INSTANCE: PoseDatabase? = null

        fun getInstance(context: Context): PoseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PoseDatabase::class.java,
                        "pose_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}