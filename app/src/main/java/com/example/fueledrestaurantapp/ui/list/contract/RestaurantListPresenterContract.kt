package com.example.fueledrestaurantapp.ui.list.contract

import com.example.fueledrestaurantapp.data.FueledApiInteractorContract
import com.example.fueledrestaurantapp.model.VenueItem


/**
 * Interface for the Place List presenter.
 */
interface RestaurantListPresenterContract {
    val view: RestaurantListViewContract
    val interactor: FueledApiInteractorContract

    fun onNewVenueQuery(query: String, near: String)
    fun onVenueItemClicked(venueItem: VenueItem)
    fun onVenuesLoaded(venues: List<VenueItem>)
    fun onVenuesRequestError(throwable: Throwable)
}