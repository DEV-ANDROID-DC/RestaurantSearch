package com.example.fueledrestaurantapp.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.example.fueledrestaurantapp.R
import com.example.fueledrestaurantapp.extension.hide
import com.example.fueledrestaurantapp.extension.hideSoftKeyboard
import com.example.fueledrestaurantapp.extension.setElevation
import com.example.fueledrestaurantapp.extension.show
import com.example.fueledrestaurantapp.model.VenueItem
import com.example.fueledrestaurantapp.ui.detail.RestaurantDetailActivity
import com.example.fueledrestaurantapp.ui.list.contract.RestaurantListPresenterContract
import com.example.fueledrestaurantapp.ui.list.contract.RestaurantListViewContract
import kotlinx.android.synthetic.main.activity_restaurant_list.*
import kotlinx.android.synthetic.main.search_bar.*


/**
 * The RestaurantListActivity allows the user to search for places
 *  using a query term and a location term. A search will return
 *  a list of [Venue]s, provided the search has results.
 */
class RestaurantListActivity : RestaurantListViewContract, AppCompatActivity() {

    companion object {
        const val QUERY_TEXT = "QUERY_TEXT"
        const val LOCATION_TEXT = "LOCATION_TEXT"

        const val GRID_SPAN_COUNT = 2
    }

    override val presenter: RestaurantListPresenterContract = RestaurantListPresenter(this)
    private val adapter = VenueAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)
        initializeUi()

        val query = "East Ham"
        val location = "London"

        hideSoftKeyboard()
        presenter.onNewVenueQuery(query, location)
    }

    private fun initializeUi() {
        searchBar.setElevation(R.dimen.search_elevation)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
       // recyclerView.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        recyclerView.adapter = this.adapter

        searchButton.setOnClickListener { onSearchButtonClick() }

        // When the soft keyboard's search button is clicked, trigger a search:
        val keyboardSearchButtonListener = TextView.OnEditorActionListener { _, _, _ ->
            onSearchButtonClick()
            true
        }

        queryEditText.setOnEditorActionListener(keyboardSearchButtonListener)
        locationEditText.setOnEditorActionListener(keyboardSearchButtonListener)

        // Show search bar in the center when no search is performed. Otherwise, bottom
        if (adapter.isEmpty()) {
            setSearchBarGravityToCenter()
        } else {
            setSearchBarGravityToBottom()
        }

        setInitialSearchTerms()
    }

    private fun setInitialSearchTerms() {
        queryEditText.setText(R.string.eastham)
        locationEditText.setText(R.string.location)
    }

    override fun showListItems() {
        recyclerView.show()
    }

    override fun hideListItems() {
        recyclerView.hide()
    }

    override fun setListItems(venues: List<VenueItem>) {
        adapter.venues = venues
        recyclerView.scrollToPosition(0)
        adapter.setOnVenueClickListener { venueItem ->
            presenter.onVenueItemClicked(venueItem)
        }
    }

    override fun launchVenueDetailView(venueItem: VenueItem) {
        RestaurantDetailActivity.launch(this, venueItem)
    }

    override fun showStatusText() {
        statusTextView.show()
    }

    override fun hideStatusText() {
        statusTextView.hide()
    }

    override fun setStatusText(stringRes: Int) {
        val text = getString(stringRes)
        setStatusText(text)
    }

    override fun setStatusText(text: String) {
        statusTextView.text = text
    }

    override fun onSearchButtonClick() {
        val query = getQueryText()
        val location = getLocationText()

        hideSoftKeyboard()
        presenter.onNewVenueQuery(query, location)
    }

    override fun showProgressBar() {
        progressBar.show()
    }

    override fun hideProgressBar() {
        progressBar.hide()
    }

    override fun setSearchBarGravityToCenter() {
        setSearchBarGravity(Gravity.CENTER)
    }

    override fun setSearchBarGravityToBottom() {
        setSearchBarGravity(Gravity.BOTTOM)
    }

    private fun setSearchBarGravity(gravity: Int) {
        val layoutParams = searchBar.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = gravity
        searchBar.layoutParams = layoutParams
    }

    private fun getQueryText(): String = queryEditText.text.toString()

    private fun getLocationText(): String = locationEditText.text.toString()

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(QUERY_TEXT, queryEditText.text.toString())
        outState?.putString(LOCATION_TEXT, locationEditText.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val queryText = savedInstanceState?.getString(QUERY_TEXT)
        val locationText = savedInstanceState?.getString(LOCATION_TEXT)

        queryEditText.setText(queryText)
        locationEditText.setText(locationText)

        super.onRestoreInstanceState(savedInstanceState)
    }
}

