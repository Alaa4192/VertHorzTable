package com.akh.verthorztablelibrary

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout

class MyFrameLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context) : this(context, null, 0, 0)

    private val detector by lazy { GestureDetector(context, MyGestureListener()) }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val onTouchEvent = detector.onTouchEvent(ev)
        return if (!onTouchEvent) {
            super.dispatchTouchEvent(ev)
            true
        } else {
            false
        }
    }
}