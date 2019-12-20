package com.example.fueledrestaurantapp

import com.example.fueledrestaurantapp.model.Category
import com.example.fueledrestaurantapp.model.Location
import com.example.fueledrestaurantapp.model.Venue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

class RestaurantUnitTests {

    private val category1 = Category(name = "Burger")
    private val category2 = Category(name = "Coffee Shop")

    private val populatedCategories = listOf(category1, category2)
    private val emptyCategories = emptyList<Category?>()
    private val nullCategories = listOf(null, null, null)

    private val locationWithPopulatedAddress = Location(formattedAddress = listOf("Waterloo", "London, MN", "UK"))
    private val locationWithAddressOfNulls = Location(formattedAddress = listOf(null, null, null))
    private val locationWithNullAddress = Location(formattedAddress = null)
    private val locationWithEmptyAddress = Location(formattedAddress = emptyList())




    @Test
    fun getCategory_isCorrectForPopulatedList() {
        val venueWithPopulatedCategoryList = Venue(categories = populatedCategories)
        Assert.assertEquals("Burger", venueWithPopulatedCategoryList.getFirstCategoryOrNull())
    }

    @Test
    fun getCategory_isNullForListOfNulls() {
        val venueWithPopulatedCategoryList = Venue(categories = nullCategories)
        Assert.assertEquals(null, venueWithPopulatedCategoryList.getFirstCategoryOrNull())
    }

    @Test
    fun getCategory_isNullForNullList() {
        val venueWithPopulatedCategoryList = Venue(categories = null)
        Assert.assertEquals(null, venueWithPopulatedCategoryList.getFirstCategoryOrNull())
    }

    @Test
    fun getCategory_isNullForEmptyList() {
        val venueWithPopulatedCategoryList = Venue(categories = emptyCategories)
        Assert.assertEquals(null, venueWithPopulatedCategoryList.getFirstCategoryOrNull())
    }

    @Test
    fun getStreetAddress_isCorrectForPopulatedAddress() {
        val venueWithPopulatedAddress = Venue(location = locationWithPopulatedAddress)
        Assert.assertEquals("Waterloo", venueWithPopulatedAddress.getStreetAddressOrNull())
    }

    @Test
    fun getStreetAddress_isNullForAddressOfNulls() {
        val venueWithAddressOfNulls = Venue(location = locationWithAddressOfNulls)
        Assert.assertEquals(null, venueWithAddressOfNulls.getStreetAddressOrNull())
    }

    @Test
    fun getStreetAddress_isNullForNullAddress() {
        val venueWithNullAddress = Venue(location = locationWithNullAddress)
        Assert.assertEquals(null, venueWithNullAddress.getStreetAddressOrNull())
    }

    @Test
    fun getStreetAddress_isNullForEmptyAddress() {
        val venueWithEmptyAddress = Venue(location = locationWithEmptyAddress)
        Assert.assertEquals(null, venueWithEmptyAddress.getStreetAddressOrNull())
    }

    @Test
    fun getStreetAddress_isNullForNullLocation() {
        val venueWithNullLocation = Venue(location = null)
        Assert.assertEquals(null, venueWithNullLocation.getStreetAddressOrNull())
    }

}