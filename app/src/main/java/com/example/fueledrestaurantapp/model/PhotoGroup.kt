package com.example.fueledrestaurantapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class PhotoGroup(
	val type: String? = null,
	val name: String? = null,
	val count: Int? = null,
	val items: List<PhotoItem>? = null
): Parcelable