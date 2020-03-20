package com.checkout51.testapp.ui.offers

import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.models.OfferResponse
import com.checkout51.testapp.data.network.OffersApi
import com.checkout51.testapp.data.network.RequestCallback
import com.checkout51.testapp.data.network.ResponseError
import com.checkout51.testapp.data.network.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class OrdersService : OffersContract.Service {

    private var orderApi: OffersApi = RetrofitManager.INSTANCE.create(OffersApi::class.java)

    override fun fetchOrders(callback: RequestCallback<List<Offer>>) =
        orderApi.getOrders().enqueue(object : Callback<OfferResponse> {
            override fun onResponse(call: Call<OfferResponse>, res: Response<OfferResponse>) {
                if (res.isSuccessful && !res.body()?.offers.isNullOrEmpty()) {
                    callback.onSuccess(res.body()?.offers!!)
                } else {
                    callback.onFailure(ResponseError.GENERIC)
                }
            }

            override fun onFailure(call: Call<OfferResponse>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onFailure(ResponseError.TIME_OUT)
                } else {
                    callback.onFailure(ResponseError.GENERIC)
                }
            }
        })
}
