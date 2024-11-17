package com.example.petfactorybd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class AppViewModel(application: Application) : AndroidViewModel(application) {
    var data = MutableLiveData<Int>()

    var showLevel: Boolean = false
}
