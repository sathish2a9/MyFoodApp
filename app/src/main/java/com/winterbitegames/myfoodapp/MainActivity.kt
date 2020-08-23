package com.winterbitegames.myfoodapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.winterbitegames.myfoodapp.adaptor.StoreAdapterJava
import com.winterbitegames.myfoodapp.data.Store
import com.winterbitegames.myfoodapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


class MainActivity : AppCompatActivity(), StoreAdapterJava.updateOrderItem {

    lateinit var listOfStores: ArrayList<Store>

    private val viewModel: MainViewModel by viewModels()

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tool_bar)
        setAppBar()
        setDummyList()

        viewModel.insertList(listOfStores)
        observeItems()

        frame_add_to_cart.setOnClickListener() {
            startActivity(Intent(this, CartActivity::class.java))
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }


    fun observeItems() {

        var adapter = StoreAdapterJava(this,this)

        var count: Int = 0;
        recycler_view.adapter = adapter

        viewModel.list.observe(this, Observer {
            if (it.isNotEmpty() && it != null) {


                adapter.setArrayList(it as ArrayList<Store>)

                count = 0
                for (store in it) {
                    if (store.count != 0) {
                        count += store.count;
                    }
                }
                if (count > 0) {

                    add_to_cart.text = resources.getString(R.string.add_to_cart_4, count)
                    frame_add_to_cart.visibility = View.VISIBLE

                } else
                    frame_add_to_cart.visibility = View.INVISIBLE
            }
        })

    }

    fun setDummyList() {

        listOfStores = ArrayList();
        listOfStores.add(
                Store(
                        "Paneer Butter masala",
                        "Amazing dish by amazing people",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "Omelete",
                        "A big amazing round omelete",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "Fried Chicken",
                        "Big big chicken all to devour",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "French Fries",
                        "Crispy water mouth with ketchup",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "Paneer2 Butter masala",
                        "Amazing dish by amazing people",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "Omelete2",
                        "A big amazing round omelete",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "Fried Chicken2",
                        "Big big chicken all to devour",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "French Fries3",
                        "Crispy water mouth with ketchup",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "French Fries7",
                        "Crispy water mouth with ketchup",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "French Fries5",
                        "Crispy water mouth with ketchup",
                        7
                )
        )
        listOfStores.add(
                Store(
                        "French Fries99",
                        "Crispy water mouth with ketchup",
                        7
                )
        )


    }

    fun setAppBar() {

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) {
                    //  Collapsed
                    collapasing_toolbar.title = "Doodle blue"
                    collapasing_toolbar.setCollapsedTitleTextColor(
                            resources.getColor(
                                    R.color.white_50,
                                    null
                            )
                    )

                } else {
                    //Expanded
                    collapasing_toolbar.title = ""

                }
            }

        })


    }


    //todo    temporary fix for updating the list with the latest content

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: called onstart")
        //viewModel.updateOrder("Omelete", 2)

    }


    override fun updateItemAtPos(store: Store?, count: Int) {
        viewModel.updateOrder(store!!.foodName, count)
    }
}