package com.tigerspike.flickrfeed.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.atta.testrule.RxImmediateSchedulerRule
import com.tigerspike.flickrfeed.data.api.internal.FlickrApi
import com.tigerspike.flickrfeed.data.api.model.ImageItem
import com.tigerspike.flickrfeed.data.api.model.ImageMedia
import com.tigerspike.flickrfeed.data.api.model.ImagesResponse
import com.tigerspike.flickrfeed.domain.service.FlickrService
import com.tigerspike.flickrfeed.domain.translator.ImageItemTranslator
import com.tigerspike.flickrfeed.domain.translator.ImageMediaTranslator
import com.tigerspike.flickrfeed.domain.translator.ImagesResponseTranslator
import com.tigerspike.flickrfeed.extension.observeOnce
import com.tigerspike.flickrfeed.extension.toLocalDateTime
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var flickrApi: FlickrApi

    private lateinit var imageItemTranslator: ImageItemTranslator
    private lateinit var imagesResponseTranslator: ImagesResponseTranslator
    private lateinit var flickrService: FlickrService
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        imageItemTranslator = ImageItemTranslator(
            ImageMediaTranslator()
        )
        imagesResponseTranslator = ImagesResponseTranslator(
            imageItemTranslator
        )
        flickrService = FlickrService(
            flickrApi,
            imagesResponseTranslator
        )
        mainViewModel = MainViewModel(flickrService)
    }

    @Test
    fun loadData() {
        val response = ImagesResponse(
            "Uploads from everyone",
            "https://www.flickr.com/photos/",
            "",
            "2019-10-19T13:32:09Z",
            "https://www.flickr.com",
            listOf(
                ImageItem(
                    "Carcross.jpg",
                    "https://www.flickr.com/photos/rickosunaphotography/48923152258/",
                    ImageMedia(
                        "https://live.staticflickr.com/65535/48923152258_ebd6eefc2c_m.jpg"
                    ),
                    "2019-05-22T17:17:42-08:00",
                    " <p><a href=\"https://www.flickr.com/people/rickosunaphotography/\">Rick Osuna Photography</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/rickosunaphotography/48923152258/\" title=\"Carcross.jpg\"><img src=\"https://live.staticflickr.com/65535/48923152258_ebd6eefc2c_m.jpg\" width=\"240\" height=\"160\" alt=\"Carcross.jpg\" /></a></p> ",
                    "2019-10-19T13:32:09Z",
                    "nobody@flickr.com (\"Rick Osuna Photography\")",
                    "23470649@N06",
                    "osuna yukon 2019"
                ),
                ImageItem(
                    "Fright Night Event – October 2019",
                    "https://www.flickr.com/photos/carlwardarkartphoto/48923153543/",
                    ImageMedia("https://live.staticflickr.com/65535/48923153543_46458e9a99_m.jpg"),
                    "2019-10-19T12:22:12-08:00",
                    " <p><a href=\"https://www.flickr.com/people/carlwardarkartphoto/\">MediaSL.com</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/carlwardarkartphoto/48923153543/\" title=\"Fright Night Event – October 2019\"><img src=\"https://live.staticflickr.com/65535/48923153543_46458e9a99_m.jpg\" width=\"240\" height=\"136\" alt=\"Fright Night Event – October 2019\" /></a></p> <p><a href=\"https://media-sl.com/2019/10/19/fright-night-event-october-2019/\" rel=\"noreferrer nofollow\">media-sl.com/2019/10/19/fright-night-event-october-2019/</a><br /> <br /> <br /> Start Date: October&nbsp;19, 2019&nbsp;– End Date: November&nbsp;2, 2019 <br /> <br /> <br /> This year Fright Night returns&#8230;. Join us for our event with 38 Stores bringing you the best for Halloween. From Textures to Costumes, to Home and Garden. There is lots to be had. Free Gifts, 25% off Exclusives a...</p>",
                    "2019-10-19T13:32:29Z",
                    "nobody@flickr.com (\"MediaSL.com\")",
                    "96458685@N05",
                    "eventsl fashionsl frightnightsl mediasl mensl secondlife secondlifestyle sl"
                )
            )
        )

        Mockito.`when`(flickrApi.getImages(
            "json",
            1
        )).thenReturn(
            Single.create<ImagesResponse> {
                it.onSuccess(response)
            }
        )

        val datePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        mainViewModel.loadData()

        mainViewModel.isLoadingEvent.observeOnce {
            assertEquals(false, it)
        }
        mainViewModel.title.observeOnce {
            assertEquals(response.title, it)
        }
        mainViewModel.updatedTime.observeOnce {
            assertEquals(
                response.modified.toLocalDateTime(datePattern),
                it
            )
        }
        mainViewModel.items.observeOnce {
            assertEquals(
                response.items.map { item ->
                    imageItemTranslator.translateToDomain(item)
                },
                it
            )
        }
    }
}