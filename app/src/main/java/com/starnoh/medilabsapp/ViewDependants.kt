package com.starnoh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import com.starnoh.medilabsapp.adapters.DependantAdapter
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.helpers.PrefsHelper
import com.starnoh.medilabsapp.models.Dependant
import com.starnoh.medilabsapp.models.LabTests
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale.filter

class ViewDependants : AppCompatActivity() {
    private lateinit var itemList : List<Dependant>
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout
    lateinit var dependantadapter: DependantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dependants)

        val recyclerview:RecyclerView = findViewById(R.id.recyclerview)
         dependantadapter = DependantAdapter(applicationContext)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = dependantadapter

        swiperefresh = findViewById(R.id.swipeRefreshLayout)
        progress = findViewById(R.id.progress)

        post_fetch()
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

    }
    private fun post_fetch(){
        val helper = ApiHelper(applicationContext)
        val api = "${Constants.BASE_URL}/view_dependants"
        val member_id = PrefsHelper.getPrefs(applicationContext, "member_id")
        val body = JSONObject()
        body.put("member_id", member_id)
        helper.post(api,body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(
                    result.toString(),
                    Array<Dependant>::class.java
                ).toList()


                }



            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(result: String?) {

            }

        })
    }
    private fun filter(text: String) {

        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Dependant> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.surname.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            dependantadapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            dependantadapter.filterList(filteredlist)
        }
    }
}