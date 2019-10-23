package com.tigerspike.flickrfeed.idlingresource

import android.view.View
import androidx.test.espresso.IdlingResource

class ViewVisibilityIdlingResource(
    private val mView: View,
    private val mExpectedVisibility: Int
) : IdlingResource {

    private var mIdle: Boolean = false
    private var mResourceCallback: IdlingResource.ResourceCallback? = null

    init {
        this.mIdle = false
        this.mResourceCallback = null
    }

    override fun getName(): String {
        return ViewVisibilityIdlingResource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        mIdle = mIdle || mView.visibility == mExpectedVisibility

        if (mIdle) {
            mResourceCallback?.onTransitionToIdle()
        }

        return mIdle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        mResourceCallback = resourceCallback
    }

}