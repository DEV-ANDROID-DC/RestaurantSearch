package com.example.fueledrestaurantapp.model

data class Response(
        val groups: List<VenueGroup>? = null,
        val tips: Tips? = null,
        val photos: Photos? = null
)