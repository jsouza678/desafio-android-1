package com.picpay.agenda.presentation.home

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.R
import com.picpay.agenda.utils.RecyclerViewUtils
import com.picpay.agenda.utils.RecyclerViewUtils.getItemCount
import com.picpay.domain.entity.User
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import org.koin.test.KoinTest

@KoinApiExtension
class AgendaActivityTest: KoinTest {

    @get: Rule
    val activityTestRule = ActivityScenarioRule(AgendaActivity::class.java)
    var activity: AgendaActivity? = null
    private val gson: Gson by inject()
    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @After
    fun tearDown() {
        activity = null
    }

    @Test
    fun shouldDisplayTitle() {
        val expectedTitle = context.getString(R.string.title)

        onView(withId(R.id.container_nested_scroll_view)).check(matches(isDisplayed()))
        onView(withText(expectedTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayListItem() {
        // Getting data from mock server
        server.dispatcher = openMockServer()
        server.start(serverPort)

        // Checking if response data is the same as the expected one
        val response = gson.fromJson<List<User>>(body, object: TypeToken<List<User>>(){}.type)
        Assert.assertEquals(response, FAKE_CONTACTS)

        server.close()
    }

    @Test
    fun shouldCloseOnBack() {
        activityTestRule.scenario.onActivity {
            activity = it
        }

        pressBackUnconditionally()

        Assert.assertTrue(activity?.isDestroyed ?: true)
    }

    @Test
    fun shouldMaintainDataAfterRotation() {
        // Getting data from mock server
        server.dispatcher = openMockServer()
        server.start(serverPort)

        activityTestRule.scenario.onActivity {
            activity = it
        }

        val response = gson.fromJson<List<User>>(body, object: TypeToken<List<User>>(){}.type)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Testing if recyclerview has items after rotation
        val itemCount = getItemCount(R.id.recyclerView)
        Assert.assertEquals(response, FAKE_CONTACTS)
        Assert.assertTrue(itemCount != 0)

        server.close()
    }

    @Test
    fun shouldMaintainDataAfterRotationData() {
        // Getting data from mock server
        server.dispatcher = openMockServer()
        server.start(serverPort)

        activityTestRule.scenario.onActivity {
            activity = it
        }

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Testing if the first element visible is the same after the rotation
        RecyclerViewUtils.atPosition( 0, withText(FAKE_CONTACTS[0].name))

        server.close()
    }

    private fun openMockServer() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/users" -> successResponse
                else -> errorResponse
            }
        }
    }

    companion object {
        private const val serverPort = 8080
        val body =
            "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

        private val successResponse by lazy {
            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }

        val FAKE_CONTACTS = listOf(
            User(
                id = 1001,
                img = "https://randomuser.me/api/portraits/men/9.jpg",
                name = "Eduardo Santos",
                username = "@eduardo.santos"
            )
        )
    }
}