package com.checkout51.testapp.utils

import android.content.Context
import com.checkout51.testapp.R
import com.checkout51.testapp.ui.offers.SortOrders

fun Double.formatToCurrency(): String = "$".plus(this)

fun getSortingString(context: Context, sortType: SortOrders) =
    if (sortType == SortOrders.NAME) context.getString(R.string.name_sort)
    else context.getString(R.string.cash_back_sort)


