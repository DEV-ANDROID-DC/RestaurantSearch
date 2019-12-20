package com.example.fueledrestaurantapp.data

import com.example.fueledrestaurantapp.*
import com.example.fueledrestaurantapp.model.FoursquareResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * An interface for making requests from the Foursquare API.
 */
interface FueledRestaurantApi {
    @GET("venues/explore")
    fun requestVenues(@Query("client_id") clientId: String = CLIENT_ID,
                      @Query("client_secret") clientSecret: String = CLIENT_SECRET,
                      @Query("v") version: String = API_VERSION,
                      @Query("near") near: String,
                      @Query("query") query: String,
                      @Query("categoryId") categoryId: String = CATEGORY_ID,
                      @Query("limit") limit: Int = LIMIT): Single<FoursquareResponse>

    @GET("venues/{venue_id}/tips")
    fun requestVenueTips(@Path("venue_id") venueId: String,
                    @Query("client_id") clientId: String = CLIENT_ID,
                    @Query("client_secret") clientSecret: String = CLIENT_SECRET,
                    @Query("v") version: String = API_VERSION): Single<FoursquareResponse>

    @GET("venues/{venue_id}/photos")
    fun requestVenuePhotos(@Path("venue_id") venueId: String,
                    @Query("client_id") clientId: String = CLIENT_ID,
                    @Query("client_secret") clientSecret: String = CLIENT_SECRET,
                    @Query("v") version: String = API_VERSION): Single<FoursquareResponse>
}