package com.checkout51.testapp.ui.offers

import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.network.RequestCallback
import com.checkout51.testapp.data.network.ResponseError
import com.checkout51.testapp.ui.base.BasePresenter

class OffersPresenter constructor(private val orderService: OrdersService = (OrdersService())) :
    BasePresenter<OffersContract.View>(), OffersContract.Presenter {

    var offerList: List<Offer>? = null

    override fun loadData() {
        view()?.showProgress()
        if (offerList.isNullOrEmpty()) {
            orderService.fetchOrders(call)
        } else {
            view()?.showOrders(offerList!!)
        }
    }

    override fun tryAgainClicked() {
        loadData()
    }

    override fun onReloadClicked() {
        orderService.fetchOrders(call)
    }

    private val call: RequestCallback<List<Offer>> = object : RequestCallback<List<Offer>> {
        override fun onSuccess(offers: List<Offer>) {
            offerList = offers
            view()?.showOrders(offers)
        }

        override fun onFailure(error: ResponseError) {
            view()?.showError()
        }
    }

    override fun filterBy(filter: SortOrders) {
        view()?.hideBottomSheet()
        view()?.filterAdapter(filter)
    }

    override fun onFilterClicked() {
        view()?.showBottomSheet()
    }
}