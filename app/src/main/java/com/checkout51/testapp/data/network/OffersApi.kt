package com.checkout51.testapp.data.network

import com.checkout51.testapp.data.models.OfferResponse
import retrofit2.Call
import retrofit2.http.GET

interface OffersApi {
    @GET("checkout51/c51-coding-challenge/master/c51.json")
    fun getOrders(): Call<OfferResponse>
}