package com.example.fueledrestaurantapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.example.fueledrestaurantapp.R
import com.example.fueledrestaurantapp.extension.*
import com.example.fueledrestaurantapp.model.Tip
import com.example.fueledrestaurantapp.model.VenueItem
import com.example.fueledrestaurantapp.ui.detail.contract.RestaurantDetailPresenterContract
import com.example.fueledrestaurantapp.ui.detail.contract.RestaurentDetailViewContract
import kotlinx.android.synthetic.main.activity_restaurant_detail.*


class RestaurantDetailActivity : RestaurentDetailViewContract, AppCompatActivity() {

    companion object {
        fun launch(fromActivity: Activity, venueItem: VenueItem) {
            val intent = Intent(fromActivity, RestaurantDetailActivity::class.java)
            intent.putExtra(EXTRA_VENUE_ITEM, venueItem)

            fromActivity.startActivityWithTransitionIfPossible(intent)
        }

        private const val EXTRA_VENUE_ITEM = "EXTRA_VENUE_ITEM"
        fun Intent.getVenueItem(): VenueItem? {
            return if (hasExtra(EXTRA_VENUE_ITEM)) {
                getParcelableExtra(EXTRA_VENUE_ITEM)
            } else {
                null
            }
        }
    }

    override val presenter: RestaurantDetailPresenterContract = RestaurantDetailPresenter(this)

    /**
     * The venue whose details will be displayed, passed in as an extra.
     */
    private val venueItem by lazy { intent.getVenueItem() }

    private val imageAdapter = RestaurantImageAdapter()
    private val tipAdapter = RestaurantTipAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        if (venueItem == null) {
            // If venueItem was not passed in as an extra, finish with error.
            showToast(R.string.there_was_an_error_loading_details_for_that_place)
            finish()
        } else {
            initializeUi()
            presenter.venueItem = venueItem
        }
    }

    /**
     * Initialize photo and tip RecyclerViews with layout managers
     *  and adapters.
     */
    private fun initializeUi() {
        photoRecyclerView.setHasFixedSize(true)
        photoRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        photoRecyclerView.adapter = imageAdapter

        tipsRecyclerView.layoutManager = LinearLayoutManager(this)
        tipsRecyclerView.adapter = tipAdapter
    }

    override fun setReason(reason: String) {
        reasonTextView.text = reason
    }

    override fun showReason() = reasonTextView.show()

    override fun hideReason() = reasonTextView.hide()

    override fun setVenueImages(urls: List<String>) {
        imageAdapter.imageUrls = urls
    }

    override fun showVenueImages() = photoRecyclerView.show()

    override fun hideVenueImages() = photoRecyclerView.hide()

    override fun setVenueTips(tips: List<Tip>) {
        tipAdapter.tips = tips
    }

    override fun showVenueTips() {
        tipsRecyclerView.show()
    }

    override fun hideVenueTips() {
        tipsRecyclerView.hide()
    }

    override fun setVenueName(name: String) {
        nameTextView.text = name
    }

    override fun showVenueName() = nameTextView.show()

    override fun hideVenueName() = nameTextView.hide()

    override fun setVenueAddress(address: String) {
        addressTextView.text = address
    }

    override fun showVenueAddress() = addressTextView.show()

    override fun hideVenueAddress() = addressTextView.hide()

    override fun setVenueCategory(category: String) {
        categoryTextView.text = category
    }

    override fun showVenueCategory() = categoryTextView.show()

    override fun hideVenueCategory() = categoryTextView.hide()

    override fun setRating(rating: Double) {
        ratingTextView.text = rating.toString()
    }

    override fun setRatingColor(color: String?) {
        color?.let {

            val colorString = "#$color"

            try {
                val backgroundColor = Color.parseColor(colorString)
                ratingTextView.setBackgroundColor(backgroundColor)
            } catch (exception: IllegalArgumentException) {
                // Color string was not parsed correctly, so we will
                //  just stick with the default background color.
            }

        }
    }

    override fun showRating() = ratingTextView.show()

    override fun hideRating() = ratingTextView.hide()

    override fun launchUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun launchDialer(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    override fun showMenuNotAvailableError() {
        rootView.showSnackbar(R.string.menu_not_available)
    }

    override fun showPhoneNumberNotAvailableError() {
        rootView.showSnackbar(R.string.phone_number_not_available)
    }

    override fun showWebsiteNotAvailableError() {
        rootView.showSnackbar(R.string.website_not_available)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Mimic back button when "up" button is pressed:
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
