package com.example.fueledrestaurantapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class Hours(
	val status: String? = null
): Parcelable