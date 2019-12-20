package com.example.fueledrestaurantapp.ui.list.contract

import android.support.annotation.StringRes
import com.example.fueledrestaurantapp.model.VenueItem


/**
 * Interface for the Place List view.
 */
interface RestaurantListViewContract {
    val presenter: RestaurantListPresenterContract

    fun showListItems()
    fun hideListItems()
    fun setListItems(venues: List<VenueItem>)

    fun showStatusText()
    fun hideStatusText()
    fun setStatusText(@StringRes stringRes: Int)
    fun setStatusText(text: String)

    fun onSearchButtonClick()

    fun showProgressBar()
    fun hideProgressBar()

    fun setSearchBarGravityToCenter()
    fun setSearchBarGravityToBottom()

    fun launchVenueDetailView(venueItem: VenueItem)
}