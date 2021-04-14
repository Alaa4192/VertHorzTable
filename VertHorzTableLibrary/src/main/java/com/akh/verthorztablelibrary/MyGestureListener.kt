package com.akh.verthorztablelibrary

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float
    ): Boolean {
        Log.d("MyGestureListener", "onScroll")
        return false
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d("MyGestureListener", "onSingleTapUp")
        return true
    }

}