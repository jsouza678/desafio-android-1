package com.picpay.agenda.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

object RecyclerViewUtils {

    fun atPosition(
        position: Int,
        itemMatcher: Matcher<View>
    ) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            val viewHolder = item?.findViewHolderForAdapterPosition(position) ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }

    fun checkRecyclerViewItem(
        resId: Int,
        position: Int,
        withMatcher: Matcher<View>
    ) {
        Espresso.onView(ViewMatchers.withId(resId)).check(
            ViewAssertions.matches(
                atPosition(
                    position,
                    ViewMatchers.hasDescendant(withMatcher)
                )
            )
        )
    }

    fun getItemCount(resId: Int): Int {
        var itemCount = 0

        val matcher = object : TypeSafeMatcher<View?>() {
            override fun describeTo(description: Description?) {
                description?.appendText("with list size: $itemCount")
            }
            override fun matchesSafely(item: View?): Boolean {
                itemCount = (item as? RecyclerView)?.adapter?.itemCount ?: 0
                return true
            }
        }

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(resId), ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(matcher))

        return itemCount
    }
}
