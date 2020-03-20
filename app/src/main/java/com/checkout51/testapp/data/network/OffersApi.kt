package com.checkout51.testapp.data.network

import com.checkout51.testapp.data.models.OfferResponse
import retrofit2.Call
import retrofit2.http.GET

interface OffersApi {
    @GET("bins/95a8c")
    fun getOrders(): Call<OfferResponse>
}