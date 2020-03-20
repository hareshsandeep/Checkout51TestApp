package com.checkout51.testapp.ui.offers

import androidx.lifecycle.Lifecycle
import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.data.network.RequestCallback
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer


class OffersPresenterTest {
    @Mock
    lateinit var offersPresenter: OffersPresenter
    @Mock
    lateinit var mockView: OffersContract.View
    @Mock
    lateinit var lifecycle: Lifecycle
    @Mock
    lateinit var orderService: OrdersService
    @Mock
    lateinit var callback: RequestCallback<List<Offer>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        offersPresenter = OffersPresenter(orderService)
        offersPresenter.attachView(mockView, lifecycle)
    }

    @Test
    fun when_loadData_show_spinnerTest() {
        //when
        offersPresenter.loadData()
        //then
        then(mockView).should().showProgress()
    }

    @Test
    fun when_onReloadClicked_show_spinnerTest() {
        //when
        offersPresenter.onReloadClicked()
        //then
        then(mockView).should().showProgress()
    }

    @Test
    fun when_onReloadClicked_make_service_callTest() {
        //when
        Mockito.`when`(orderService.fetchOrders(any()))
            .thenAnswer(Answer<Any?> { invocation ->
                val arguments = invocation.arguments
                if (arguments != null && arguments.size > 0 && arguments[0] != null) {
                    val list = listOf<Offer>()
                    return@Answer list
                }
                null
            })

        //when
        offersPresenter.onReloadClicked()
        //then
        verify(orderService, times(1)).fetchOrders(any())
    }

    @Test
    fun when_tryAgainClicked_make_service_call_Test() {
        Mockito.`when`(orderService.fetchOrders(any()))
            .thenAnswer(Answer<Any?> { invocation ->
                val arguments = invocation.arguments
                if (arguments != null && arguments.size > 0 && arguments[0] != null) {
                    val list = listOf<Offer>()
                    return@Answer list
                }
                null
            })

        //when
        offersPresenter.tryAgainClicked()
        //then
        verify(orderService, times(1)).fetchOrders(any())
    }

    @Test
    fun when_fab_clicked_should_show_bottom_sheet_Test() {
        //when
        offersPresenter.onFilterClicked()
        //then
        then(mockView).should().showBottomSheet()
    }

    @Test
    fun when_sort_option_clicked_should_hide_bottom_sheet_Test() {
        //when
        offersPresenter.sortBy(SortOrders.CASH_BACK)
        //then
        then(mockView).should().hideBottomSheet()
    }

    @Test
    fun when_sort_option_clicked_should_sort_list_Test() {
        //mocking list
        offersPresenter.offerList = listOf(
            Offer("40408", "Buy 2: Select", "http://abc.com", .5),
            Offer("40408", "TRISCUIT ", "http://abc.com", 1.5),
            Offer("40408", "TRISCUIT", "", .75),
            Offer("40408", "Crackers", "", 3.5)
        )

        mockView.showOrders(offersPresenter.offerList!!)
        //when
        offersPresenter.sortBy(SortOrders.CASH_BACK)
        //then
        then(mockView).should().hideBottomSheet()
    }

}
