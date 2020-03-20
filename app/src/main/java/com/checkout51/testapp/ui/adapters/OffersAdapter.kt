package com.checkout51.testapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.checkout51.testapp.R
import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.ui.offers.SortOrders
import com.checkout51.testapp.utils.formatToCurrency
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_item_order.view.*

class OffersAdapter(private val offers: MutableList<Offer>) :
    RecyclerView.Adapter<OffersAdapter.OrderViewHolder>() {

    fun addOrders(offers: List<Offer>) {
        this.offers.addAll(offers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_order, parent, false)
            .let {
                OrderViewHolder(it)
            }
    }

    override fun getItemCount(): Int = offers.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    fun sortItems(sortType: SortOrders) {
        if (sortType == SortOrders.NAME) {
            this.offers.sortBy { it.name }
        } else if (sortType == SortOrders.CASH_BACK) {
            this.offers.sortBy { it.cash_back }
        }

        notifyDataSetChanged()
    }

    class OrderViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(offer: Offer) {
            containerView.item_name.text = offer.name
            containerView.item_cash_back.text = offer.cash_back.formatToCurrency()
            Picasso.get()
                .load(offer.image_url)
                .resize(100, 100)
                .placeholder(R.drawable.ic_image_place_holder_24dp)
                .into(containerView.item_image)
        }
    }
}