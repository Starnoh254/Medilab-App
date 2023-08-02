package com.starnoh.medilabsapp.adapters
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.starnoh.medilabsapp.R
import com.starnoh.medilabsapp.SingleLabTest
import com.starnoh.medilabsapp.models.LabTests


class LabTestsAdapter (var context: Context):
    RecyclerView.Adapter<LabTestsAdapter.ViewHolder>(){


    // Create a list and connect it with our model
    var itemList : List<LabTests> = listOf()  //Its empty

    //Create a class here , will hold our views in single_lab xml
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestsAdapter.ViewHolder {
        //access/inflate the single lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_labtests,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabTestsAdapter.ViewHolder, position: Int) {
        //Find your 3 text
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
        //Assume one LabTest
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = "KES " + item.test_cost
        holder.itemView.setOnClickListener {
            val i = Intent(context, SingleLabTest::class.java)
            i.putExtra("Lab_id", item.lab_id)
            i.putExtra("test_id", item.test_id)
            i.putExtra("test_discount", item.test_discount)
            i.putExtra("test_cost", item.test_cost)
            i.putExtra("test_name", item.test_name)
            i.putExtra("test_description", item.test_description)
            i.putExtra("availability", item.availability)
            i.putExtra("more_info", item.more_info)
            i.putExtra("reg_date", item.reg_date)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size //Count how many items in the list
    }
    // This is for filtering data
    fun filterList(filterList: List<LabTests>){
        itemList = filterList
        notifyDataSetChanged()
    }




    // Earlier we mentioned item List is empty!
    //We will get data from our API , then bring it to below function
    // The data you bring here must follow the lab model
    fun setListItems(data: List<LabTests>){
        itemList = data //map/link the data to itemList
        notifyDataSetChanged()//Tell this adapter class that now itemlist is loaded with data
    }


}