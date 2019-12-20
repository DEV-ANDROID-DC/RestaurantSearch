package com.example.fueledrestaurantapp.ui.detail

import com.example.fueledrestaurantapp.data.FueledApiInteractor
import com.example.fueledrestaurantapp.data.FueledApiInteractorContract
import com.example.fueledrestaurantapp.model.Tip
import com.example.fueledrestaurantapp.model.VenueItem
import com.example.fueledrestaurantapp.ui.detail.contract.RestaurantDetailPresenterContract
import com.example.fueledrestaurantapp.ui.detail.contract.RestaurentDetailViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


/**
 * Presenter implementation for the Venue detail view.
 */
class RestaurantDetailPresenter(override val view: RestaurentDetailViewContract,
                                private val interactor: FueledApiInteractorContract
                                = FueledApiInteractor()
) : RestaurantDetailPresenterContract {

    override var venueItem: VenueItem? = null
        set(item) {
            field = item
            onVenueItemSet()
        }

    private val reason get() = venueItem?.reasons?.items?.firstOrNull()
    private val name get() = venueItem?.venue?.name
    private val address get() = venueItem?.venue?.getStreetAddressOrNull()
    private val category get() = venueItem?.venue?.getFirstCategoryOrNull()
    private val rating get() = venueItem?.venue?.rating
    private val ratingColor get() = venueItem?.venue?.ratingColor
    private val menuUrl get() = venueItem?.venue?.menu?.url
    private val phoneNumber get() = venueItem?.venue?.contact?.phone
    private val website get() = venueItem?.venue?.url

    /**
     * When the venue item is set, display the venue
     *  information in the UI (or hide views when
     *  info isn't available).
     */
    override fun onVenueItemSet() {
        val venueId = venueItem?.venue?.id
        if (venueId != null) {
            loadTips(venueId)
            loadPhotos(venueId)
        } else {
            view.hideVenueTips()
            view.hideVenueImages()
        }

        reason?.summary?.let { summary ->
            view.setReason(summary)
            view.showReason()
        } ?: view.hideReason()

        name?.let { name ->
            view.setVenueName(name)
            view.showVenueName()
        } ?: view.hideVenueName()

        address?.let { address ->
            view.setVenueAddress(address)
            view.showVenueAddress()
        } ?: view.hideVenueAddress()

        category?.let { category ->
            view.setVenueCategory(category)
            view.showVenueCategory()
        } ?: view.hideVenueCategory()

        rating?.let { rating ->
            view.setRating(rating)
            view.setRatingColor(ratingColor)
            view.showRating()
        } ?: view.hideRating()

    }

    override fun loadTips(venueId: String) {
        interactor.requestVenueTips(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = this::onTipsLoaded,
                    onError = this::onTipsRequestError
                )
    }

    override fun onTipsLoaded(tips: List<Tip>) {
        if (tips.isNotEmpty()) {
            view.setVenueTips(tips)
            view.showVenueTips()
        } else {
            view.hideVenueTips()
        }
    }

    override fun onTipsRequestError(throwable: Throwable) {
        view.hideVenueTips()
    }

    override fun loadPhotos(venueId: String) {
        interactor.requestVenuePhotos(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = this::onPhotosLoaded,
                    onError = this::onPhotosRequestError
                )
    }

    override fun onPhotosLoaded(photoUrls: List<String>) {
        if (photoUrls.isNotEmpty()) {
            view.setVenueImages(photoUrls)
            view.showVenueImages()
        } else {
            view.hideVenueImages()
        }
    }

    override fun onPhotosRequestError(throwable: Throwable) {
        view.hideVenueImages()
    }
}