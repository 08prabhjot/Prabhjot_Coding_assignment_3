package com.example.yogaapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pose_table")
data class Pose(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var name: String,
    var image: String,
    var description: String
)
