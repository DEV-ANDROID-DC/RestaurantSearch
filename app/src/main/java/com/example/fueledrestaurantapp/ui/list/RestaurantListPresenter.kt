package com.example.fueledrestaurantapp.ui.list

import com.example.fueledrestaurantapp.R
import com.example.fueledrestaurantapp.data.FueledApiInteractor
import com.example.fueledrestaurantapp.data.FueledApiInteractorContract
import com.example.fueledrestaurantapp.model.VenueItem
import com.example.fueledrestaurantapp.ui.list.contract.RestaurantListPresenterContract
import com.example.fueledrestaurantapp.ui.list.contract.RestaurantListViewContract
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class RestaurantListPresenter(override val view: RestaurantListViewContract,
                              override val interactor: FueledApiInteractorContract
                            = FueledApiInteractor(),
                              private val observationScheduler: Scheduler = AndroidSchedulers.mainThread()) :
    RestaurantListPresenterContract {

    /**
     * When a new query is performed, request data from the interactor
     *  and show it in the view.
     */
    override fun onNewVenueQuery(query: String, near: String) {
        view.setSearchBarGravityToBottom()

        view.hideStatusText()
        view.hideListItems()
        
        if (near.isEmpty()) {
            view.hideProgressBar()
            view.setStatusText(R.string.you_need_to_specify_a_location_for_your_search)
            view.showStatusText()
        } else {
            view.showProgressBar()
            interactor.requestPlaces(query, near)
                    .subscribeOn(Schedulers.io())
                    .observeOn(observationScheduler)
                    .subscribeBy(
                            onSuccess = this::onVenuesLoaded,
                            onError = this::onVenuesRequestError
                    )
        }
    }

    override fun onVenuesLoaded(venues: List<VenueItem>) {
        view.hideListItems()
        view.hideProgressBar()

        if (venues.isEmpty()) {
            view.setStatusText(R.string.no_places_found)
            view.showStatusText()
        } else {
            view.setListItems(venues)
            view.showListItems()
            view.hideStatusText()
        }
    }

    override fun onVenuesRequestError(throwable: Throwable) {
        showErrorStatus()
    }

    override fun onVenueItemClicked(venueItem: VenueItem) {
        view.launchVenueDetailView(venueItem)
    }

    private fun showErrorStatus() {
        view.hideProgressBar()
        view.hideListItems()

        view.setStatusText(R.string.could_not_load_places_error)
        view.showStatusText()
    }
}

