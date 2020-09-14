package com.geekykel.gads.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.geekykel.gads.R
//import com.geekykel.gads.network.RetrofitClientInstance
import com.geekykel.gads.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val submitBtn: Button = findViewById(R.id.submit_btn)
        submitBtn.setOnClickListener {
            val intent = Intent(this, SubmitActivity::class.java)
            startActivity(intent)
        }
        //initializeData()

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }
//
//    private fun initializeData() {
//        val request = RetrofitClientInstance.buildService(GetDataService::class.java)
//        val callGetHours = request.getLearnersHours()
//        val callGetSkillIQ = request.getLearnersSkilliq()
//
//        callGetHours.enqueue(object: Callback<List<DataModel>> {
//            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
//                if (response.isSuccessful) {
//                    recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@MainActivity)
//                        adapter = CustomAdapter(response.body()!!)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        callGetSkillIQ.enqueue(object: Callback<List<DataModel>> {
//            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
//                if (response.isSuccessful) {
//                    recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@MainActivity)
//                        adapter = CustomAdapter(response.body()!!)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}