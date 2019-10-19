package com.tigerspike.flickrfeed.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.tigerspike.flickrfeed.R
import com.tigerspike.flickrfeed.databinding.ActivityMainBinding
import com.tigerspike.flickrfeed.ui.adapter.ImageItemAdapter
import com.tigerspike.flickrfeed.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObserver()
        init()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar = binding.headerView.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        adapter = ImageItemAdapter(this) {

        }
        val imagesView = binding.imagesView
        imagesView.layoutManager?.let {
            if (it is GridLayoutManager) {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter.getItemViewType(position)) {
                            ImageItemAdapter.VIEW_TYPE_ITEM -> {
                                1
                            }
                            ImageItemAdapter.VIEW_TYPE_HEADER,
                            ImageItemAdapter.VIEW_TYPE_LAST_UPDATED -> {
                                2
                            }
                            else -> {
                                1
                            }
                        }
                    }
                }
            }
        }
        imagesView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.isLoadingEvent.observe(this, Observer {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
        viewModel.title.observe(this, Observer {
            adapter.setTitle(it)
        })
        viewModel.updatedTime.observe(this, Observer {
            adapter.setLastUpdated(it)
        })
        viewModel.items.observe(this, Observer {
            adapter.reset(it)
        })
    }

    private fun init() {
        viewModel.loadData()
    }

}