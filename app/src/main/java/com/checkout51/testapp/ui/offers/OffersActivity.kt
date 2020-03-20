package com.checkout51.testapp.ui.offers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.checkout51.testapp.R
import com.checkout51.testapp.data.models.Offer
import com.checkout51.testapp.ui.adapters.OffersAdapter
import com.checkout51.testapp.ui.base.PresenterProviders
import com.checkout51.testapp.utils.getSortingString
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_item_order_header.*
import java.util.*

class OffersActivity : AppCompatActivity(), OffersContract.View {
    private lateinit var mPresenter: OffersPresenter
    private var mAdapter: OffersAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpUi();

        mPresenter = PresenterProviders.of(this).get(OffersPresenter::class.java)
        mPresenter.attachView(this, lifecycle)
        mPresenter.loadData()
    }

    private fun setUpUi() {
        recycler_orders.apply {
            layoutManager = LinearLayoutManager(this@OffersActivity)
            mAdapter = OffersAdapter(ArrayList<Offer>())
            recycler_orders.adapter = mAdapter
        }

        try_again.setOnClickListener {
            mPresenter.tryAgainClicked()
        }

        fab.setOnClickListener {
            mPresenter.onFilterClicked()
        }


        filterByName.setOnClickListener {
            mPresenter.filterBy(SortOrders.NAME)
        }

        filterByCashback.setOnClickListener {
            mPresenter.filterBy(SortOrders.CASH_BACK)
        }

        closeBtn.setOnClickListener {
            hideBottomSheet()
        }

        from(bottom_sheet).addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        fab.hide()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fab.show()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_refresh) {
            mPresenter.onReloadClicked()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun showOrders(offers: List<Offer>) {
        fab.show()
        recycler_orders.visibility = View.VISIBLE
        offer_header.visibility = View.VISIBLE
        mAdapter?.addOrders(offers)
        hideProgress()
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        fab.isEnabled = true
    }

    override fun showProgress() {
        fab.hide()
        recycler_orders.visibility = View.GONE
        offer_header.visibility = View.GONE
        errorView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        fab.isEnabled = false
    }

    override fun showError() {
        fab.hide()
        hideProgress()
        errorView.visibility = View.VISIBLE
        recycler_orders.visibility = View.GONE
        offer_header.visibility = View.GONE
    }

    override fun showBottomSheet() {
        val bottomSheetBehavior = from(bottom_sheet)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun hideBottomSheet() {
        val bottomSheetBehavior = from(bottom_sheet)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun filterAdapter(sortType: SortOrders) {
        mAdapter?.sortItems(sortType)
        Toast.makeText(
            this,
            getSortingString(this, sortType),
            Toast.LENGTH_SHORT
        ).show()
    }
}
