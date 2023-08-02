package com.starnoh.medilabsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.modcom.medilabsapp.helpers.SQLiteCartHelper
import com.starnoh.medilabsapp.R
import com.starnoh.medilabsapp.models.LabTests

class LabTestsCartAdapter(var context: Context):
    RecyclerView.Adapter<LabTestsCartAdapter.ViewHolder>() {


    //Create a List and connect it with our model
    var itemList : List<LabTests> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestsCartAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_labtests_cart,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: LabTestsCartAdapter.ViewHolder, position: Int) {
        //Find your 3 text views
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
        //Assume one Lab
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = item.test_cost+" KES"
        val remove = holder.itemView.findViewById<MaterialButton>(R.id.remove)
        remove.setOnClickListener {
            val test_id = item.test_id
            val helper = SQLiteCartHelper(context)
            helper.clearCartById(test_id)
            // The Item is Removed.
            // Go to Helper and reload the MyCart Activity in clearCartById function
        }


        // Toast.makeText(context, "yyy"+item.test_cost, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }

    //This is for filtering data
    fun filterList(filterList: List<LabTests>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<LabTests>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}