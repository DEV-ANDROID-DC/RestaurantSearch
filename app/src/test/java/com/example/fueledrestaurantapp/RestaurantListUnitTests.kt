package com.example.fueledrestaurantapp

import com.example.fueledrestaurantapp.model.Venue
import com.example.fueledrestaurantapp.model.VenueItem
import com.example.fueledrestaurantapp.ui.list.RestaurantListPresenter
import com.example.fueledrestaurantapp.ui.list.contract.RestaurantListViewContract
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(org.mockito.junit.MockitoJUnitRunner::class)
class RestaurantListUnitTests{
    private val mockedView = Mockito.mock(RestaurantListViewContract::class.java)
    private val presenter = RestaurantListPresenter(mockedView, observationScheduler = TestScheduler())

    private val dummyVenue = Venue(name = "Bargain World")
    private val dummyVenueItem = VenueItem(venue = dummyVenue)

    private var progressBarIsVisible = false
    private var listItemsAreVisible = true
    private var statusTextIsVisible = false

    @Before
    fun ViewMethods() {
        Mockito.`when`(mockedView.hideListItems()).then {
            listItemsAreVisible = false
            null
        }

        Mockito.`when`(mockedView.showListItems()).then {
            listItemsAreVisible = true
            null
        }

        Mockito.`when`(mockedView.showStatusText()).then {
            statusTextIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideStatusText()).then {
            statusTextIsVisible = false
            null
        }

        Mockito.`when`(mockedView.showProgressBar()).then {
            progressBarIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideProgressBar()).then {
            progressBarIsVisible = false
            null
        }
    }

    @Test
    fun showsErrorForEmptyNearField() {
        statusTextIsVisible = false
        progressBarIsVisible = true
        listItemsAreVisible = true

        presenter.onNewVenueQuery(query = "east ham", near = "")

        Mockito.verify(mockedView).setStatusText(R.string.you_need_to_specify_a_location_for_your_search)
        Mockito.verify(mockedView, Mockito.never())
            .setStatusText(Mockito.intThat {
                it != R.string.you_need_to_specify_a_location_for_your_search
            })

        Assert.assertTrue(
            "Status text is visible when no near field is specified.",
            statusTextIsVisible
        )
        Assert.assertFalse(
            "Item list is not visible when no near field is specified.",
            listItemsAreVisible
        )
        Assert.assertFalse(
            "Progress bar is not visible when no near field is specified.",
            progressBarIsVisible
        )
    }

    @Test
    fun showsProgressBarForQueryWithLocation() {
        progressBarIsVisible = false
        statusTextIsVisible = true
        listItemsAreVisible = true

        presenter.onNewVenueQuery(query = "eastham", near = "london")
        Assert.assertTrue(
            "Progress bar is visible when search is performed (with location included).",
            progressBarIsVisible
        )
        Assert.assertFalse(
            "Status text is not visible when search is performed (with location included).",
            statusTextIsVisible
        )
        Assert.assertFalse(
            "Item list is not visible when search is performed (with location included).",
            listItemsAreVisible
        )
    }

    @Test
    fun showsErrorMessageOnVenueRequestError() {
        statusTextIsVisible = false
        progressBarIsVisible = true
        listItemsAreVisible = true

        presenter.onVenuesRequestError(Exception("Error getting places."))

        Mockito.verify(mockedView).setStatusText(R.string.could_not_load_places_error)
        Mockito.verify(mockedView).showStatusText()

        Assert.assertTrue("Status text is visible when request fails.", statusTextIsVisible)
        Assert.assertFalse("Progress bar is not visible when request fails.", progressBarIsVisible)
        Assert.assertFalse("Item list is not visible when request fails.", listItemsAreVisible)
    }

    @Test
    fun showsItemsOnNonEmptyResponse() {
        listItemsAreVisible = false
        statusTextIsVisible = true
        progressBarIsVisible = true

        presenter.onVenuesLoaded(listOf(dummyVenueItem))

        Mockito.verify(mockedView).setListItems(Mockito.anyList())
        Mockito.verify(mockedView).showListItems()

        Assert.assertTrue("Item list is visible when response is successful.", listItemsAreVisible)
        Assert.assertFalse(
            "Status text is not visible when response is successful.",
            statusTextIsVisible
        )
        Assert.assertFalse(
            "Progress bar is not visible when response is successful.",
            progressBarIsVisible
        )
    }

    @Test
    fun showsEmptyStatusTextForEmptyResponse() {
        statusTextIsVisible = false
        listItemsAreVisible = true
        progressBarIsVisible = true

        presenter.onVenuesLoaded(emptyList())

        Mockito.verify(mockedView).setStatusText(R.string.no_places_found)
        Mockito.verify(mockedView).showStatusText()

        Assert.assertTrue("Status text not visible when response is empty.", statusTextIsVisible)
        Assert.assertFalse("Item list is not visible when response is empty.", listItemsAreVisible)
        Assert.assertFalse(
            "Progress bar is not visible when response is empty.",
            progressBarIsVisible
        )
    }
}