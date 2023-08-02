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
import com.starnoh.medilabsapp.models.Dependant
import com.starnoh.medilabsapp.models.LabTests


class DependantAdapter (var context: Context):
    RecyclerView.Adapter<DependantAdapter.ViewHolder>(){


    // Create a list and connect it with our model
    var itemList : List<Dependant> = listOf()  //Its empty

    //Create a class here , will hold our views in single_lab xml
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val surname = itemView.findViewById<MaterialTextView>(R.id.dep_name)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DependantAdapter.ViewHolder {
        //access/inflate the single lab.xml

        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_dependant,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DependantAdapter.ViewHolder, position: Int) {
        //Find your 3 text

        val others = holder.itemView.findViewById<MaterialTextView>(R.id.dep_others)
        val dob = holder.itemView.findViewById<MaterialTextView>(R.id.dep_dob)
        //Assume one Dependant
        val item = itemList[position]
        holder.surname.text = item.surname
        others.text = item.others
        dob.text =  item.dob

    }

    override fun getItemCount(): Int {
        return itemList.size //Count how many items in the list
    }
    // This is for filtering data
    fun filterList(filterList: List<Dependant>){
        itemList = filterList
        notifyDataSetChanged()
    }




    // Earlier we mentioned item List is empty!
    //We will get data from our API , then bring it to below function
    // The data you bring here must follow the lab model
    fun setListItems(data: List<Dependant>){
        itemList = data //map/link the data to itemList
        notifyDataSetChanged()//Tell this adapter class that now itemlist is loaded with data
    }


}