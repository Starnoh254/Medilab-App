package com.modcom.medilabsapp.helpers


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.starnoh.medilabsapp.MainActivity
import com.starnoh.medilabsapp.MyCart
import com.starnoh.medilabsapp.models.LabTests

class SQLiteCartHelper(context: Context):
    SQLiteOpenHelper(context, "cart3.db", null, 3) {
    //Make context visible to other functions
    val context = context

    //SQLite helps store data Locally on your phone - You can CRUD
    override fun onCreate(sql: SQLiteDatabase?) {
        sql?.execSQL("CREATE TABLE IF NOT EXISTS cart(test_id Integer primary key, test_name varchar, test_cost Integer, lab_id Integer, test_description text)")
    }

    override fun onUpgrade(sql: SQLiteDatabase?, p1: Int, p2: Int) {
        sql?.execSQL("DROP TABLE IF EXISTS cart")
    }

    //INSERT Save to cart
    fun insert(test_id: String, test_name: String, test_cost: String,
               test_description: String, lab_id: String){
        val db = this.writableDatabase
        //Select before insert see if ID already exsists
        val values = ContentValues()
        values.put("test_id", test_id)
        values.put("test_name", test_name)
        values.put("lab_id", lab_id)
        values.put("test_cost", test_cost)
        values.put("test_description", test_description)

        //save
        val result:Long = db.insert("cart", null, values)

        if (result < 1){
            println("Failed to Add")
            Toast.makeText(context, "Item Already in Cart", Toast.LENGTH_SHORT).show()
        }
        else {
            println("Item Added To Cart")
            val i = Intent(context,MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
            Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show()
        }
    }//end

    //Count How may items are there in the cart table
    fun getNumItems(): Int {
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from cart", null)
        //return result count
        return result.count
    } //end

    //Clear all records
    fun clearCart(){
        val db = this.writableDatabase
        db.delete("cart", null, null)
        println("Cart Cleared")
        Toast.makeText(context, "Cart Cleared", Toast.LENGTH_SHORT).show()
        val i = Intent(context, MyCart::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)

    } //end

    //Remove One Item
    fun clearCartById(test_id: String){
        val db = this.writableDatabase
        //Provide the test_id when deleting
        db.delete("cart", "test_id=?", arrayOf(test_id))
        println("Item Id $test_id Removed")
        Toast.makeText(context, "Item Id $test_id Removed", Toast.LENGTH_SHORT).show()
        val i = Intent(context, MyCart::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }//end


    fun totalCost(): Double {
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select SUM(test_cost) from cart",
            null)
        var total: Double = 0.0
        while (result.moveToNext()){
            //the cursor result returns a Lists of test_cost.
            //Below result.getDouble(0) to retrieve the value from the first
            // column of the current row
            total += result.getDouble(0)
        }//end while
        return total
    }//End
    //https://github.com/modcomlearning/MediLabApp
    //Get all items from the Cart
    fun getAllItems(): ArrayList<LabTests> {
        val db = this.writableDatabase
        val items = ArrayList<LabTests>()
        val result: Cursor = db.rawQuery("select * from cart", null)
        //Lets Add  all  rows into the items arrayList
        while (result.moveToNext())
        {
            var model = LabTests()
            //Map rows to the model
            model.test_id = result.getString(0)  //Assume one row, test_id
            model.test_name = result.getString(1)  //Assume one row, test_name
            model.test_cost = result.getString(2)  //Assume one row, test_cost
            model.lab_id = result.getString(3)  //Assume one row, test_description
            model.test_description = result.getString(4)  //Assume one row, test_description
            items.add(model)//add model to ArrayList
        }//end while
        return items
    }}

