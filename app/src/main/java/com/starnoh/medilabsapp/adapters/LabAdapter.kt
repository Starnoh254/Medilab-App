package com.starnoh.medilabsapp.adapters
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.starnoh.medilabsapp.LabTestsActivity
import com.starnoh.medilabsapp.R
import com.starnoh.medilabsapp.models.Lab


class LabAdapter (var context: Context):
    RecyclerView.Adapter<LabAdapter.ViewHolder>(){


    // Create a list and connect it with our model
    var itemList : List<Lab> = listOf()  //Its empty

    //Create a class here , will hold our views in single_lab xml
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabAdapter.ViewHolder {
        //access/inflate the single lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lab,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabAdapter.ViewHolder, position: Int) {
        //Find your 3 text
        val lab_name = holder.itemView.findViewById<MaterialTextView>(R.id.lab_name)
        val permit_id = holder.itemView.findViewById<MaterialTextView>(R.id.permit_id)
        val email = holder.itemView.findViewById<MaterialTextView>(R.id.email)
        //Assume one Lab
        val lab = itemList[position]
        lab_name.text = lab.lab_name
        permit_id.text = lab.permit_id
        email.text = lab.email
        holder.itemView.setOnClickListener {
            //carry the lab_id of what you clicked
            //carry it with bundles , preferences
            val name = lab.lab_name
            val id = lab.lab_id
            val i = Intent(context, LabTestsActivity::class.java)
            i.putExtra("Lab_id", id)
            i.putExtra("name", name)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size //Count how many items in the list
    }
    // This is for filtering data
    fun filterList(filterList: List<Lab>){
        itemList = filterList
        notifyDataSetChanged()
    }




    // Earlier we mentioned item List is empty!
    //We will get data from our API , then bring it to below function
    // The data you bring here must follow the lab model
    fun setListItems(data: List<Lab>){
        itemList = data //map/link the data to itemList
        notifyDataSetChanged()//Tell this adapter class that now itemlist is loaded with data
    }


}