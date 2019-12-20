package com.example.fueledrestaurantapp.model

data class VenueGroup(
		val type: String? = null,
		val name: String? = null,
		val items: List<VenueItem>? = null
)