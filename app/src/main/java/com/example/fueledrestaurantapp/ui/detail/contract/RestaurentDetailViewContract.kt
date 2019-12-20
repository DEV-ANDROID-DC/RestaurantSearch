package com.example.fueledrestaurantapp.ui.detail.contract

import com.example.fueledrestaurantapp.model.Tip


/**
 * Interface for the Place Detail view.
 */
interface RestaurentDetailViewContract {
    val presenter: RestaurantDetailPresenterContract

    fun setReason(reason: String)
    fun showReason()
    fun hideReason()

    fun setVenueImages(urls: List<String>)
    fun showVenueImages()
    fun hideVenueImages()

    fun setVenueTips(tips: List<Tip>)
    fun showVenueTips()
    fun hideVenueTips()

    fun setVenueName(name: String)
    fun showVenueName()
    fun hideVenueName()

    fun setVenueAddress(address: String)
    fun showVenueAddress()
    fun hideVenueAddress()

    fun setVenueCategory(category: String)
    fun showVenueCategory()
    fun hideVenueCategory()

    fun setRating(rating: Double)
    fun setRatingColor(color: String?)
    fun showRating()
    fun hideRating()

    fun launchUrl(url: String)
    fun launchDialer(phoneNumber: String)

    fun showMenuNotAvailableError()
    fun showPhoneNumberNotAvailableError()
    fun showWebsiteNotAvailableError()
}