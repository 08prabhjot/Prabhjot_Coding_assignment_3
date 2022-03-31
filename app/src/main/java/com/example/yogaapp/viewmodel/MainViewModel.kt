package com.example.yogaapp.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yogaapp.database.PoseDatabase
import com.example.yogaapp.model.Pose
import com.example.yogaapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sp = PreferenceManager.getDefaultSharedPreferences(application)
    private val database = PoseDatabase.getInstance(application).poseDao
    private val isAppOpenedFirstTime = sp.getBoolean(Constants.IS_APP_OPENED_FIRST_TIME_PREF, true)

    val allPosesInDatabase = database.getAllPoses()

    // add plant screen
    var currentSelectedEncodedImage: String? = null
    val poseName = MutableLiveData<String>()
    val poseDescription = MutableLiveData<String>()

    private val _isPoseAdded = MutableLiveData<Boolean>()
    val isPoseAdded: LiveData<Boolean>
        get() = _isPoseAdded

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String>
        get() = _snackBarText


    init {
        if (isAppOpenedFirstTime) {
            addInitialDataToDatabase()
        }
    }

    private fun addInitialDataToDatabase() {
        val poseList = Constants.getInitialPoses(getApplication())
        for (i in 0..8) {
            insert(poseList[i])
        }
        sp.edit().putBoolean(Constants.IS_APP_OPENED_FIRST_TIME_PREF, false).apply()
    }

    private fun insert(pose: Pose) = viewModelScope.launch(Dispatchers.IO) {
        database.insert(pose)
    }

    fun delete(pose: Pose) = viewModelScope.launch(Dispatchers.IO) {
        database.delete(pose.id)
    }

    private fun isValidPoseDetails(): Boolean {

        if (currentSelectedEncodedImage == null) {
            _snackBarText.value = "Select image"
            return false
        } else if (poseName.value == null || poseName.value!!.trim().isEmpty()) {
            _snackBarText.value = "Enter pose name"
            return false
        } else if (poseDescription.value == null || poseDescription.value!!.trim().isEmpty()) {
            _snackBarText.value = "Enter pose description"
            return false
        }

        return true
    }

    fun onDoneClick() {
        if (isValidPoseDetails()) {
            insert(
                Pose(
                    name = poseName.value!!,
                    description = poseDescription.value!!,
                    image = currentSelectedEncodedImage!!
                )
            )
            _isPoseAdded.value = true
        }
    }

}