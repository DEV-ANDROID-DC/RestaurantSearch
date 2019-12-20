package com.example.fueledrestaurantapp.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@SuppressLint("ParcelCreator")
@Parcelize
data class VenueItem (
        val reasons: Reasons? = null,
        val venue: Venue? = null,
        val tips: List<Tip>? = null
) : Parcelable
