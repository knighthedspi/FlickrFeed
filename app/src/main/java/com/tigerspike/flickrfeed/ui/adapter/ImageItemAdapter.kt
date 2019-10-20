package com.tigerspike.flickrfeed.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tigerspike.flickrfeed.R
import com.tigerspike.flickrfeed.databinding.ItemHeaderBinding
import com.tigerspike.flickrfeed.databinding.ItemImageBinding
import com.tigerspike.flickrfeed.databinding.ItemLastUpdatedBinding
import com.tigerspike.flickrfeed.domain.model.ImageItem
import com.tigerspike.flickrfeed.ui.view.BindingHolder
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ImageItemAdapter(
    context: Context,
    private val onItemClick: (ImageItem) -> Unit
) : RecyclerView.Adapter<BindingHolder<ViewDataBinding>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val list = mutableListOf<ImageItem>()
    private var title: String? = null
    private var lastUpdated: LocalDateTime? = null

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_LAST_UPDATED = 2
        private const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<ViewDataBinding> {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                BindingHolder.inflate(
                    layoutInflater,
                    R.layout.item_header,
                    parent,
                    false
                )
            }
            VIEW_TYPE_ITEM -> {
                BindingHolder.inflate(
                    layoutInflater,
                    R.layout.item_image,
                    parent,
                    false
                )
            }
            else -> {
                BindingHolder.inflate(
                    layoutInflater,
                    R.layout.item_last_updated,
                    parent,
                    false
                )
            }
        }
    }

    // include title as header, last updated item as footer
    override fun getItemCount(): Int = list.size + 2

    override fun onBindViewHolder(holder: BindingHolder<ViewDataBinding>, position: Int) {
        when (val binding = holder.dataBinding) {
            is ItemHeaderBinding -> {
                binding.title = title
            }
            is ItemImageBinding -> {
                list[position - 1].let { imageItem ->
                    binding.imageItem = imageItem
                    binding.root.setOnClickListener {
                        onItemClick.invoke(imageItem)
                    }
                }
            }
            is ItemLastUpdatedBinding -> {
                lastUpdated?.let {
                    binding.lastUpdated = it.format(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                VIEW_TYPE_HEADER
            }
            itemCount - 1 -> {
                VIEW_TYPE_LAST_UPDATED
            }
            else -> {
                VIEW_TYPE_ITEM
            }
        }
    }

    @UiThread
    fun reset(items: Collection<ImageItem>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun setTitle(title: String) {
        this.title = title
        notifyDataSetChanged()
    }

    fun setLastUpdated(lastUpdated: LocalDateTime) {
        this.lastUpdated = lastUpdated
        notifyDataSetChanged()
    }

}