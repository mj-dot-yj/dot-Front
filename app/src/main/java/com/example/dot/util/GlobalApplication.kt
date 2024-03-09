package com.example.dot.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "905588f8a1643a958d523022c4b16d0a")
        prefs = PreferenceUtil(applicationContext)
    }
}