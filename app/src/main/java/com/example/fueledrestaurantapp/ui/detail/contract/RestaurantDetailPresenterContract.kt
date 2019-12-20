package com.example.fueledrestaurantapp.ui.detail.contract

import com.example.fueledrestaurantapp.model.Tip
import com.example.fueledrestaurantapp.model.VenueItem


/**
 * Interface for the Place Detail presenter.
 */
interface RestaurantDetailPresenterContract {
    val view: RestaurentDetailViewContract
    var venueItem: VenueItem?

    fun onVenueItemSet()

    fun loadTips(venueId: String)
    fun onTipsLoaded(tips: List<Tip>)
    fun onTipsRequestError(throwable: Throwable)

    fun loadPhotos(venueId: String)
    fun onPhotosLoaded(photoUrls: List<String>)
    fun onPhotosRequestError(throwable: Throwable)
}