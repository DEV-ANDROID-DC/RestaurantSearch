package com.example.fueledrestaurantapp

import com.example.fueledrestaurantapp.model.*
import com.example.fueledrestaurantapp.ui.detail.RestaurantDetailPresenter
import com.example.fueledrestaurantapp.ui.detail.contract.RestaurentDetailViewContract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(org.mockito.junit.MockitoJUnitRunner::class)
class RestaurantDetailUnitTests {

    private val mockedView = Mockito.mock(RestaurentDetailViewContract::class.java)
    private val presenter = RestaurantDetailPresenter(mockedView)


    private var reasonIsVisible = false
    private var venueTipsAreVisible = false
    private var venueNameIsVisible = false
    private var venueAddressIsVisible = false
    private var venueCategoryIsVisible = false



    @Before
    fun ViewMethods() {
        Mockito.`when`(mockedView.showReason()).then {
            reasonIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideReason()).then {
            reasonIsVisible = false
            null
        }

        Mockito.`when`(mockedView.showVenueTips()).then {
            venueTipsAreVisible = true
            null
        }

        Mockito.`when`(mockedView.hideVenueTips()).then {
            venueTipsAreVisible = false
            null
        }


        Mockito.`when`(mockedView.showVenueName()).then {
            venueNameIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideVenueName()).then {
            venueNameIsVisible = false
            null
        }

        Mockito.`when`(mockedView.showVenueAddress()).then {
            venueAddressIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideVenueAddress()).then {
            venueAddressIsVisible = false
            null
        }

        Mockito.`when`(mockedView.showVenueCategory()).then {
            venueCategoryIsVisible = true
            null
        }

        Mockito.`when`(mockedView.hideVenueCategory()).then {
            venueCategoryIsVisible = false
            null
        }

    }

    @Test
    fun showsReasonIfHasReason() {
        reasonIsVisible = false

        presenter.venueItem = VenueItem(
            reasons = Reasons(count = 1,
                items = listOf(ReasonItem(summary = "Here's a reason")))
        )

        Mockito.verify(mockedView).setReason("Here's a reason")
        Mockito.verify(mockedView).showReason()

        Assert.assertTrue("Reason is visible when it exists.", reasonIsVisible)
    }

    @Test
    fun hidesReasonIfNoReason() {
        reasonIsVisible = true

        presenter.venueItem = VenueItem(
            reasons = Reasons(count = 0,
                items = emptyList()))

        Mockito.verify(mockedView).hideReason()

        Assert.assertFalse("Reason is not visible when it doesn't exist.", reasonIsVisible)
    }

    @Test
    fun showsTipsIfHasTips() {
        venueTipsAreVisible = false

        val tipList = listOf(Tip(text = "Good coffee!"))
        presenter.onTipsLoaded(tipList)

        Mockito.verify(mockedView).setVenueTips(tipList)
        Mockito.verify(mockedView).showVenueTips()

        Assert.assertTrue("Tips are visible when they exist.", venueTipsAreVisible)
    }

    @Test
    fun hidesTipsIfNoTips() {
        venueTipsAreVisible = true

        presenter.onTipsLoaded(emptyList())

        Mockito.verify(mockedView).hideVenueTips()

        Assert.assertFalse("Tips are not visible when there aren't any.", venueTipsAreVisible)
    }

    @Test
    fun hidesTipsIfRequestFails() {
        venueTipsAreVisible = true

        presenter.onTipsRequestError(Exception("Error getting venue tips!"))
        Mockito.verify(mockedView).hideVenueTips()

        Assert.assertFalse("Tips are not visible when request fails.", venueTipsAreVisible)
    }


    @Test
    fun showsNameIfHasName() {
        venueNameIsVisible = false
        presenter.venueItem = VenueItem(venue = Venue(name = "Asha Dining Hall"))

        Mockito.verify(mockedView).setVenueName("Asha Dining Hall")
        Mockito.verify(mockedView).showVenueName()

        Assert.assertTrue("Name is visible when it exists.", venueNameIsVisible)
    }

    @Test
    fun hideNameIfNoName() {
        venueNameIsVisible = true
        presenter.venueItem = VenueItem(venue = Venue())

        Mockito.verify(mockedView).hideVenueName()

        Assert.assertFalse("Name is not visible when it doesn't exist.", venueNameIsVisible)
    }

    @Test
    fun showsAddressIfHasAddress() {
        venueAddressIsVisible = false

        val location = Location(formattedAddress = listOf("address", "city"))
        presenter.venueItem = VenueItem(venue = Venue(location = location))

        Mockito.verify(mockedView).setVenueAddress("address")
        Mockito.verify(mockedView).showVenueAddress()

        Assert.assertTrue("Address is visible when it exists.", venueAddressIsVisible)
    }

    @Test
    fun hideAddressIfNoAddress() {
        venueAddressIsVisible = true

        val location = Location()
        presenter.venueItem = VenueItem(venue = Venue(location = location))

        Mockito.verify(mockedView).hideVenueAddress()

        Assert.assertFalse("Address is not visible when it doesn't exist.", venueAddressIsVisible)
    }

    @Test
    fun showsCategoryIfHasCategory() {
        venueCategoryIsVisible = false

        val category = Category(name = "Coffee Shop")
        val categoryList = listOf(category)
        presenter.venueItem = VenueItem(venue = Venue(categories = categoryList))

        Mockito.verify(mockedView).setVenueCategory("Coffee Shop")
        Mockito.verify(mockedView).showVenueCategory()

        Assert.assertTrue("Category is visible when it exists.", venueCategoryIsVisible)
    }

    @Test
    fun hideCategoryIfNoCategory() {
        venueCategoryIsVisible = true

        presenter.venueItem = VenueItem(venue = Venue(categories = emptyList()))

        Mockito.verify(mockedView).hideVenueCategory()

        Assert.assertFalse("Category is not visible when it doesn't exist.", venueCategoryIsVisible)
    }

}