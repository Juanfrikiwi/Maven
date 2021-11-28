package com.example.marvelcharacters


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.marvelcharacters.ui.Splash.SplashActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MainActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(SplashActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun mainActivityTest() {
        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.character_list),
                withParent(withParent(IsInstanceOf.instanceOf(ViewGroup::class.java))),
                isDisplayed()
            )
        )
        recyclerView.check(ViewAssertions.matches(isDisplayed()))

        val recyclerView2 = onView(
            Matchers.allOf(
                withId(R.id.character_list),
                childAtPosition(
                    withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        val button = onView(
            Matchers.allOf(
                withId(R.id.btn_favorite), withText("Añadir a favoritos"),
                withParent(withParent(withId(R.id.detail_scrollview))),
                isDisplayed()
            )
        )
        button.check(ViewAssertions.matches(isDisplayed()))

        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.btn_favorite), withText("Añadir a favoritos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.detail_scrollview),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatImageButton = onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.toolbar_layout),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val tabView = onView(
            Matchers.allOf(
                withContentDescription("Favoritos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.iv_delete), withText("Borrar"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.ly_item_list),
                        childAtPosition(
                            withClassName(Matchers.`is`("com.example.marvelcharacters.ui.MaskedCardView")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            Matchers.allOf(
                withId(R.id.btn_accept), withText("Aceptar"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.cl_dialog_body_custody),
                        childAtPosition(
                            withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
