package com.example.fueledrestaurantapp.data

import android.util.Log
import com.example.fueledrestaurantapp.API_BASE_URL
import com.example.fueledrestaurantapp.model.Tip
import com.example.fueledrestaurantapp.model.VenueItem
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Makes calls to the Foursquare API, implementing the
 *    Foursquare Interactor contract.
 */
class FueledApiInteractor : FueledApiInteractorContract {


    //to execute the network requests. and excection happens in background thread
//    val requestIntIterator = Interceptor {chain ->
//        val request = chain.request()
//        return@Interceptor chain.proceed(request)
//    }
    //.addInterceptor(requestIntIterator)

    //to execute the network requests. and excection happens in background thread
    val okHttpClient = OkHttpClient.Builder()

            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()



    private val foursquareRetrofit by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(FueledRestaurantApi::class.java)
    }

    /**
     * Request a list of places near a location for a given query.
     */
    override fun requestPlaces(query: String, near: String): Single<List<VenueItem>> {
        val placesSingle = foursquareRetrofit.requestVenues(near = near,
                query = query) // 1 => include photos

        return placesSingle.map {
            Log.e("Respose of Venue ::",it.response.toString());
            it.response.groups?.flatMap { it.items.orEmpty() }


        }
    }

    /**
     * Request a list of tips for a venue.
     */
    override fun requestVenueTips(venueId: String): Single<List<Tip>> {
        val tipsSingle = foursquareRetrofit.requestVenueTips(venueId = venueId)

        return tipsSingle.map {
            it.response.tips?.items
        }
    }

    /**
     * Request a list of photos for a venue.
     */
    override fun requestVenuePhotos(venueId: String): Single<List<String>> {
        val photosSingle = foursquareRetrofit.requestVenuePhotos(venueId = venueId)

        return photosSingle.map {
            it.response.photos?.items?.map { it.getUrl() }
        }
    }

}