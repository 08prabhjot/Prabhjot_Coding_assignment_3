package com.example.yogaapp.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yogaapp.adapter.PoseAdapter
import com.example.yogaapp.model.Pose

@BindingAdapter("listAllPoses")
fun listAllPoses(recyclerView: RecyclerView, poses: List<Pose>?){
    if (poses != null){
        val adapter = recyclerView.adapter as PoseAdapter
        adapter.setData(poses)
    }
}