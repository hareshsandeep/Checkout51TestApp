package com.checkout51.testapp.ui.offers

import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.network.RequestCallback

object OffersContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun showOrders(offers: List<Offer>)
        fun showError()
        fun showBottomSheet()
        fun hideBottomSheet()
        fun filterAdapter(sortType: SortOrders)
    }

    interface Presenter {
        fun loadData()
        fun tryAgainClicked()
        fun onReloadClicked()
        fun filterBy(filter: SortOrders)
        fun onFilterClicked()
    }

    interface Service {
        fun fetchOrders(callback: RequestCallback<List<Offer>>)
    }
}