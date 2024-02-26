package com.example.dot

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplicaion : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "905588f8a1643a958d523022c4b16d0a")
    }
}