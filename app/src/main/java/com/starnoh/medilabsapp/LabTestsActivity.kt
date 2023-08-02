package com.starnoh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import com.starnoh.medilabsapp.adapters.LabTestsAdapter
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.models.Lab
import com.starnoh.medilabsapp.models.LabTests
import org.json.JSONArray
import org.json.JSONObject

class LabTestsActivity : AppCompatActivity() {

    lateinit var itemList: List<LabTests>
    lateinit var labTestsAdapter: LabTestsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_tests)
        progress = findViewById(R.id.progress)
        recyclerView = findViewById((R.id.recycler))
        labTestsAdapter = LabTestsAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        post_fetch()
        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener {
            post_fetch()//fetch data again
        }

        val etsearch = findViewById<EditText>(R.id.etsearch)
        etsearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }// end oncreate

    fun post_fetch(){
        val api = "${Constants.BASE_URL}/lab_tests"
        //Above API needs a body , so we have to build it
        val body = JSONObject()
        val id = intent.extras?.getString("Lab_id")
        body.put("lab_id",id) //NB: 4 is static
        val helper = ApiHelper(applicationContext)
        helper.post(api, body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {
                progress.visibility = View.GONE
                swiperefresh.isRefreshing = false

                val gson = GsonBuilder().create()
                itemList = gson.fromJson(
                    result.toString(),
                    Array<LabTests>::class.java
                ).toList()

                //Finally , our adapter has the data
                labTestsAdapter.setListItems(itemList)
                recyclerView.adapter = labTestsAdapter
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                progress.visibility = View.GONE

            }

        })
    }//end fetch

    //Filter
    private fun filter(text: String) {



        // creating a new array list to filter our data.
        val filteredlist: ArrayList<LabTests> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.test_name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            labTestsAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            labTestsAdapter.filterList(filteredlist)
        }
    }
}