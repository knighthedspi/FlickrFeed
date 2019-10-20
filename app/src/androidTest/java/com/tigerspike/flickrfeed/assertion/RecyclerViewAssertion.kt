package com.tigerspike.flickrfeed.assertion

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import org.junit.Assert.*

object RecyclerViewAssertions {

    /**
     * Provides a RecyclerView assertion based on a view matcher. This allows you to
     * validate whether a RecyclerView contains a row in memory without scrolling the list.
     *
     * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
     * @return an Espresso ViewAssertion to check against a RecyclerView.
     */
    fun itemViewMatches(
        position: Int, @IdRes resId: Int,
        viewMatcher: Matcher<View>
    ): ViewAssertion {
        assertNotNull(viewMatcher)

        return ViewAssertion { view, noViewException ->
            if (noViewException != null) {
                throw noViewException
            }

            assertTrue("View is RecyclerView", view is RecyclerView)

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            val itemType = adapter!!.getItemViewType(position)
            val viewHolder = adapter.createViewHolder(recyclerView, itemType)
            adapter.bindViewHolder(viewHolder, position)

            val targetView = if (resId == -1) {
                viewHolder.itemView
            } else {
                viewHolder.itemView.findViewById(resId)
            }

            if (viewMatcher.matches(targetView)) {
                return@ViewAssertion  // Found a matching view
            }

            fail("No match found")
        }
    }
}