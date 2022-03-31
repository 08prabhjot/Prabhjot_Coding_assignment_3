package com.example.yogaapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.example.yogaapp.model.Pose

@Dao
interface PoseDao {

    @Insert
    fun insert(pose: Pose): Long

    @Update(onConflict = ABORT)
    fun update(pose: Pose)

    @Query("select * from pose_table where id == :id")
    fun getPose(id: Long): LiveData<Pose>

    @Query("delete from pose_table where id == :id")
    fun delete(id: Long)

    @Query("select * from pose_table order by id desc")
    fun getAllPoses(): LiveData<List<Pose>>

    @Query("delete from pose_table")
    fun deleteAll()
}