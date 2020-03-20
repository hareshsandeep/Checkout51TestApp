package com.checkout51.testapp.data.network

import com.checkout51.testapp.data.models.Offer

interface RequestCallback<T> {
    fun onSuccess(offers: List<Offer>)
    fun onFailure(error: ResponseError)
}