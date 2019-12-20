package com.example.fueledrestaurantapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Icon(
	val prefix: String? = null,
	val suffix: String? = null
): Parcelable