package com.akh.verthorztablelibrary

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val detector by lazy { GestureDetector(context, MyGestureListener()) }

//    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
////        return super.dispatchTouchEvent(ev)
//        super.dispatchTouchEvent(e)
//
//        val onTouchEvent = detector.onTouchEvent(e)
//        Log.d("MyRecyclerView", "dispatchTouchEvent: $onTouchEvent")
//        return onTouchEvent // true
//
//    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)

        Log.d("MyRecyclerView", "dispatchTouchEvent: $dispatchTouchEvent")
        return dispatchTouchEvent
    }


    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
//        return super.onInterceptTouchEvent(e)

        val onTouchEvent = detector.onTouchEvent(e)
        Log.d("MyRecyclerView", "onInterceptTouchEvent: $onTouchEvent")
        return onTouchEvent // true

    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
//        return super.onTouchEvent(e)

        val onTouchEvent = detector.onTouchEvent(e)
        Log.d("MyRecyclerView", "onTouchEvent: $onTouchEvent")
        return onTouchEvent // true
    }


    class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float
        ): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

    }
}