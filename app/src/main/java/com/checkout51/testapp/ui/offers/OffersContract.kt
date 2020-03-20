package com.checkout51.testapp.ui.offers

import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.network.RequestCallback
import com.checkout51.testapp.ui.base.BasePresenter
import com.checkout51.testapp.ui.base.BaseView

object OffersContract {
    interface View : BaseView {
        fun showProgress()
        fun hideProgress()
        fun showOrders(offers: List<Offer>)
        fun showError()
        fun showBottomSheet()
        fun hideBottomSheet()
        fun filterAdapter(sortType: SortOrders)
    }

    interface Presenter : BasePresenter<View> {
        fun onViewLoaded()
        fun tryAgainClicked()
        fun onReloadClicked()
        fun filterBy(filter: SortOrders)
        fun onFilterClicked()
    }

    interface Service {
        fun fetchOrders(callback: RequestCallback<List<Offer>>)
    }
}