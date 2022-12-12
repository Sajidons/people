package com.dev.people.ui.main.view

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dev.people.R
import com.dev.people.data.model.User
import com.dev.people.ui.main.adapter.CarouselAdapter
import com.dev.people.ui.main.adapter.MainAdapter
import com.dev.people.ui.main.viewmodel.MainViewModel
import com.dev.people.utils.NetworkHelper
import com.dev.people.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created on 10 Dec 2022 by Sajid
 * Main Activity
 *
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkHelper = NetworkHelper(context = applicationContext) //network check

        setupUI()
        //setupObserver()
        setupViewPager()
        setupSearch()

    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

    }

    //using observer to retrieve the data from api response
    private fun setupObserver() {
        println("setupObserver")

        if(networkHelper.isNetworkConnected()) {
            mainViewModel.users.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        it.data?.let { users -> renderList(users) } //12 users from the mock API
                        recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    //final rendering of the user object to load the items
    private fun renderList(users: List<User>) {
        println("renderList $users")
        adapter.removeData() //remove data before reload
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    //set up the 3 pages in the view pager
    private fun setupViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                println("onPageSelected $position")
                if(position == 1){
                    //hard coded city list
                    val cities = listOf<User>(
                        User(1,"Doha", "https://tourscanner.com/blog/wp-content/uploads/2022/06/things-to-do-in-Doha-Qatar.jpg"),
                        User(2,"New York", "https://www.travelandleisure.com/thmb/3oPWFmA6fi9sjAyWzigwaUKD8P8=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/new-york-city-evening-NYCTG0221-52492d6ccab44f328a1c89f41ac02aea.jpg"),
                        User(3,"Colombo", "https://cdn.britannica.com/61/192661-050-997DE572/Colombo-Sri-Lanka.jpg?w=300&h=300"),
                        User(4,"Sydney","https://www.australia.com/content/australia/en/places/sydney-and-surrounds/guide-to-sydney/jcr:content/image.adapt.800.HIGH.jpg")
                    )
                    adapter.removeData()
                    renderList(cities)
                    recyclerView.visibility = View.VISIBLE

                }
                else if(position == 2){
                    //hard coded country list
                    val countries = listOf<User>(
                        User(1,"Argentina", "https://cdn.britannica.com/18/147118-050-7F820ED5/flag-Argentina-2010.jpg"),
                        User(2,"Portugal", "https://upload.wikimedia.org/wikipedia/commons/e/ea/Flag_of_Portugal_%281%29.jpg"),
                        User(3,"Morocco", "https://www.worldatlas.com/img/flag/ma-flag.jpg"),
                        User(5,"France", "https://static.vecteezy.com/system/resources/previews/004/313/578/original/france-country-flag-free-vector.jpg"),
                        User(6,"Brazil", "https://upload.wikimedia.org/wikipedia/en/thumb/0/05/Flag_of_Brazil.svg/640px-Flag_of_Brazil.svg.png"),
                        User(7,"Netherlands", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Flag_of_the_Netherlands.svg/1200px-Flag_of_the_Netherlands.svg.png"),
                        User(8,"Croatia", "https://cdn.britannica.com/06/6206-004-14730C28/Flag-Croatia.jpg")
                    )
                    adapter.removeData()
                    renderList(countries)
                    recyclerView.visibility = View.VISIBLE

                }
                else{
                    //load data from the /observer
                    adapter.removeData()
                    setupObserver()

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                println("onPageScrollStateChanged $state")
            }
        })

        //images for the carousel view from the drawable
        val carouselData = arrayListOf(
            R.drawable.people,
            R.drawable.city,
            R.drawable.countries
        )

        viewPager.adapter = CarouselAdapter(carouselData)

        //set up Page Transformer as user swipe
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.setPageTransformer(compositePageTransformer)
    }

    //search function
    private fun setupSearch() {
        val countrySearch = findViewById<SearchView>(R.id.country_search)

        countrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("onQueryTextChange $newText")
                adapter.filter.filter(newText) //filtering as user type
                return false
            }
        })
    }

    //clear data before close the fragment
    override fun onStop() {
        super.onStop()
        println("onStop")

        //clear the data from adapter
        adapter.let{
            adapter.removeData()
        }
    }
}