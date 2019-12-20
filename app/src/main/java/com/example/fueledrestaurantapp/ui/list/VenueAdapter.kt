package com.example.fueledrestaurantapp.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fueledrestaurantapp.R
import com.example.fueledrestaurantapp.extension.hide
import com.example.fueledrestaurantapp.extension.loadImage
import com.example.fueledrestaurantapp.extension.show
import com.example.fueledrestaurantapp.model.VenueItem


/**
 * Adapter for displaying lists of Venue cards.
 */
class VenueAdapter : RecyclerView.Adapter<VenueViewHolder>() {

    var venues: List<VenueItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClicked: (VenueItem) -> Unit = {}

    override fun getItemCount(): Int = venues.size

    fun isEmpty(): Boolean = venues.isEmpty()

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venueItem = venues[position]
        val venue = venueItem.venue

        holder?.apply {
            val photo = venue?.getFirstFeaturedPhotoOrNull()

            photo?.let {
                venueImageView.loadImage(it.getUrl())
                venueImageView.show()
            } ?: venueImageView.hide()

            titleTextView.text = venue?.name
            addressTextView.text = venue?.getStreetAddressOrNull()
            categoryTextView.text = venue?.getFirstCategoryOrNull()

            itemView.setOnClickListener { onItemClicked(venueItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_venue, parent, false)
        return VenueViewHolder(itemView)
    }

    fun setOnVenueClickListener(onItemClicked: (VenueItem) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}

