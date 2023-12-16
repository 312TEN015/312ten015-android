package com.fourleafclover.tarot

import android.app.Application
import com.fourleafclover.tarot.data.PreferenceUtil

class MyApplication: Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
//        prefs.deleteAllTarotResults()
        super.onCreate()
    }
}
