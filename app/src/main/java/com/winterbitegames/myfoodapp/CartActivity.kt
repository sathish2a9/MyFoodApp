package com.winterbitegames.myfoodapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.winterbitegames.myfoodapp.adaptor.StoreAdapterJava
import com.winterbitegames.myfoodapp.data.Store
import com.winterbitegames.myfoodapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import java.util.*
import kotlin.math.abs

class CartActivity : AppCompatActivity(), StoreAdapterJava.updateOrderItem {

    val viewModel: MainViewModel by viewModels()
    var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(tool_bar)
        setAppBar()

        tool_bar.setNavigationOnClickListener{onBackPressed()}

        val adapter = StoreAdapterJava(this,this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        viewModel.list.observe(this, Observer {

            var filteredList: MutableList<Store> = mutableListOf<Store>()
            for (item in it) {
                if (item.count > 0) filteredList.add(item)
                total += item.count*item.price
            }

            total_price.text = resources.getString(R.string.cart_total, total)
            adapter.setArrayList(filteredList as ArrayList<Store>)

            if (filteredList.size <= 2) show_more.visibility = View.INVISIBLE

            if (firstTime)
                adapter.showTwoItems(false)
            else
                adapter.showTwoItems(true)
        })

        show_more.setOnClickListener {

            adapter.showTwoItems(true)
            show_more.visibility = View.INVISIBLE

        }


    }
    var firstTime = true;

    override fun updateItemAtPos(store: Store?, count: Int) {
        firstTime = false
        viewModel.updateOrder(store!!.foodName, count)
        total = 0
    }

    fun setAppBar() {

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) {
                    tool_bar.setNavigationIcon(R.drawable.ic_action_back)
                    //  Collapsed
                    collapasing_toolbar.title = "My Cart"
                    collapasing_toolbar.setCollapsedTitleTextColor(
                        resources.getColor(
                            R.color.white_50,
                            null
                        )
                    )

                } else {
                    tool_bar.setNavigationIcon(R.color.transparent)
                    //Expanded
                    collapasing_toolbar.title = ""

                }
            }

        })


    }
}