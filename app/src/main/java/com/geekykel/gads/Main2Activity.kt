package com.geekykel.gads

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekykel.gads.model.DataModel
import com.geekykel.gads.network.GetDataService
import com.geekykel.gads.network.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initializeData()
    }

    private fun initializeData() {
        val request = RetrofitClientInstance.buildService(GetDataService::class.java)
        //val callGetHours = request.getHours()
        val callGetSkillIQ = request.getSkilliq()

//        callGetHours.enqueue(object: Callback<List<DataModel>> {
//            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
//                if (response.isSuccessful) {
//                    Log.i(TAG, "onResponse")
//                    progress_bar.visibility = View.GONE
//                    recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@Main2Activity, LinearLayoutManager.VERTICAL, false)
//                        adapter = CustomAdapter(response.body()!!)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
//                Log.i(TAG, "onFailure " + t.message)
//                Toast.makeText(this@Main2Activity, "${t.message}", Toast.LENGTH_LONG).show()
//                progress_bar.visibility = View.GONE
//            }
//        })

        callGetSkillIQ.enqueue(object: Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse")
                    progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@Main2Activity)
                        adapter = CustomAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                Log.i(TAG, "onFailure " + t.message)
                Toast.makeText(this@Main2Activity, "${t.message}", Toast.LENGTH_SHORT).show()
                progress_bar.visibility = View.GONE
            }
        })
    }

    companion object {
        private val TAG = "Main2Activity Data "
    }
}
