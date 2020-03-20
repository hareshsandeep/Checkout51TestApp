package com.checkout51.testapp.ui.offers

import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.network.RequestCallback
import com.checkout51.testapp.data.network.ResponseError
import com.checkout51.testapp.ui.base.BaseMvpPresenterImpl

class OffersPresenter(private val orderService: OrdersService) :
    BaseMvpPresenterImpl<OffersContract.View>(), OffersContract.Presenter {

    override fun onViewLoaded() {
        loadData()
    }

    private fun loadData() {
        mView?.showProgress()
        orderService.fetchOrders(call)
    }

    override fun tryAgainClicked() {
        loadData()
    }

    override fun onReloadClicked() {
        loadData()
    }

    private val call: RequestCallback<List<Offer>> = object : RequestCallback<List<Offer>> {
        override fun onSuccess(offers: List<Offer>) {
            mView?.showOrders(offers)
        }

        override fun onFailure(error: ResponseError) {
            mView?.showError()
        }
    }

    override fun detachView() {
        super.detachView()
        mView = null
    }

    override fun filterBy(filter: SortOrders) {
        mView?.hideBottomSheet()
        mView?.filterAdapter(filter)
    }

    override fun onFilterClicked() {
        mView?.showBottomSheet()
    }
}