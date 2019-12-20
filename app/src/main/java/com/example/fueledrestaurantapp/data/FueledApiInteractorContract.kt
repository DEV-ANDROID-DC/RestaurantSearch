package com.example.fueledrestaurantapp.data

import com.example.fueledrestaurantapp.model.Tip
import com.example.fueledrestaurantapp.model.VenueItem
import io.reactivex.Single


/**
 * Contract for interactors with the Foursquare API.
 */
interface FueledApiInteractorContract {
    fun requestPlaces(query: String, near: String): Single<List<VenueItem>>

    fun requestVenueTips(venueId: String): Single<List<Tip>>

    fun requestVenuePhotos(venueId: String): Single<List<String>>
}