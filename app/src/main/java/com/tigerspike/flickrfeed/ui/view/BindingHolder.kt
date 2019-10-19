package com.tigerspike.flickrfeed.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BindingHolder<T : ViewDataBinding>(itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val dataBinding: T = DataBindingUtil.bind(itemView)!!

    companion object {
        fun <T : ViewDataBinding> inflate(
            inflater: LayoutInflater, @LayoutRes layoutId: Int,
            parent: ViewGroup, attachToParent: Boolean
        ): BindingHolder<T> {
            val view = inflater.inflate(layoutId, parent, attachToParent)
            return BindingHolder(view)
        }
    }
}