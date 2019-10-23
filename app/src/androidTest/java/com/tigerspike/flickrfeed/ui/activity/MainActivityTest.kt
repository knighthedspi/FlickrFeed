package com.tigerspike.flickrfeed.ui.activity

import android.content.Intent
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tigerspike.flickrfeed.R
import com.tigerspike.flickrfeed.assertion.RecyclerViewAssertions
import com.tigerspike.flickrfeed.data.api.model.ImageItem
import com.tigerspike.flickrfeed.data.api.model.ImageMedia
import com.tigerspike.flickrfeed.data.api.model.ImagesResponse
import com.tigerspike.flickrfeed.di.AppModule
import com.tigerspike.flickrfeed.domain.translator.ImageItemTranslator
import com.tigerspike.flickrfeed.domain.translator.ImageMediaTranslator
import com.tigerspike.flickrfeed.extension.toLocalDateTime
import com.tigerspike.flickrfeed.idlingresource.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var imageItemTranslator: ImageItemTranslator
    private lateinit var mockWebServer: MockWebServer
    private val portNumber = 8080
    private lateinit var viewVisibilityIdlingResource: ViewVisibilityIdlingResource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        imageItemTranslator = ImageItemTranslator(
            ImageMediaTranslator()
        )
        mockWebServer = MockWebServer()
        mockWebServer.start(portNumber)
        AppModule.API_BASE_URL = mockWebServer.url("/").toString()
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(viewVisibilityIdlingResource)
    }

    @Test
    fun testShowUI() {
        // mock response
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

        // use moshi to convert response to json string
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val imagerResponseAdapter: JsonAdapter<ImagesResponse> =
            moshi.adapter(Types.newParameterizedType(ImagesResponse::class.java))

        val mockResponse = MockResponse()
            .setBody(
                imagerResponseAdapter.toJson(response)
            )
            .setBodyDelay(1, TimeUnit.SECONDS)
        mockWebServer.enqueue(mockResponse)

        val intent = Intent()
        testRule.launchActivity(intent)

        val activity = testRule.activity

        viewVisibilityIdlingResource = ViewVisibilityIdlingResource(
            activity.findViewById(R.id.progress_bar),
            View.GONE
        )

        IdlingRegistry.getInstance().register(viewVisibilityIdlingResource)

        withId(R.id.progress_bar).matches(
            withEffectiveVisibility(Visibility.GONE)
        )
        val imagesViewInteraction = onView(withId(R.id.images_view))
        imagesViewInteraction
            .check(
                matches(
                    hasDescendant(withText(response.title))
                )
            )
        imagesViewInteraction
            .check(
                matches(
                    hasDescendant(
                        withText(
                            activity.getString(
                                R.string.last_updated,
                                response.modified
                                    .toLocalDateTime("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                            )
                        )
                    )
                )
            )
        imagesViewInteraction
            .check(
                matches(
                    hasChildCount(response.items.size + 2)
                )
            )

        // check image item views properly or not
        val imageItems = response.items.map {
            imageItemTranslator.translateToDomain(it)
        }

        // check title is correct, 0 is header so start from 1
        val firstItem = imageItems.first()
        RecyclerViewAssertions
            .itemViewMatches(
                1,
                R.id.title_text,
                withText(
                    firstItem.title
                )
            )
        // check published text is correct
        RecyclerViewAssertions
            .itemViewMatches(
                1,
                R.id.published_text,
                withText(
                    firstItem.getPublishedText()
                )
            )
    }

}