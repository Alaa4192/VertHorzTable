package com.akh.verthorztablelibrary

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class MyHorizontalScrollView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) :
    HorizontalScrollView(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context) : this(context, null, 0, 0)

    private val detector by lazy { GestureDetector(context, MyGestureListener()) }

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
//        return super.dispatchTouchEvent(ev)

        return detector.onTouchEvent(e) // true
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
//        return super.onInterceptTouchEvent(ev)

        return detector.onTouchEvent(e) // true
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