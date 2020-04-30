package com.example.agentservices.ui.transactions


import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Typeface
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.provider.FontsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import com.example.agentservices.R
import com.example.agentservices.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_transactions.*

class TransactionsFragment  : Fragment() {

    var dbHandler: UsersDBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //init db
        dbHandler = UsersDBHelper(context = context!!)

        val transactions = dbHandler!!.readAllUsers()


        for (i in 0 until transactions.size) {
            val recipe = transactions[i]
            if(recipe.sender=="AirtelMoney" && recipe.account_balance!=""){
                airtel_money_bal.text = recipe.account_balance
            }else if(recipe.sender=="MTNMobMoney" && recipe.account_balance!=""){
                mobile_money_bal.text = recipe.account_balance
            }else if(recipe.sender=="433" && recipe.account_balance!=""){
                airtel_airtime_bal.text = recipe.account_balance
            }else if(recipe.sender=="MTNEASYLOAD" && recipe.account_balance!=""){
                mtn_airtime_bal.text = recipe.account_balance
            }
        }

        val tableRow = TableRow(context)
//        tableRow.setBackgroundColor(#2196F3);
        tableRow.setPadding(5,5,10,5)
        tableRow.setBackgroundColor(Color.LTGRAY)

        val textView1 = TextView(context)
        textView1.text = "No"
        textView1.setTextSize(15F)
        textView1.setTextColor(Color.BLACK)
        textView1.setPadding(0,2,5,2)
        tableRow.addView(textView1)

        val textView2 = TextView(context)
        textView2.text = "Network"
        textView2.setTextSize(15F)
        textView2.setPadding(0,2,5,2)
        textView2.setTextColor(Color.BLACK)
        tableRow.addView(textView2)

        val textView3 = TextView(context)
        textView3.text = "Transaction"
        textView3.setTextSize(15F)
        textView3.setPadding(0,2,5,2)
        textView3.setTextColor(Color.BLACK)
        tableRow.addView(textView3)

        val textView4 = TextView(context)
        textView4.text = "Amount"
        textView4.setTextSize(15F)
        textView4.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView4.setPadding(0,2,5,2)
        textView4.setTextColor(Color.BLACK)
        tableRow.addView(textView4)

        val textView5 = TextView(context)
        textView5.text = "Phone"
        textView5.setTextSize(15F)
        textView5.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView5.setPadding(0,2,5,2)
        textView5.setTextColor(Color.BLACK)
        tableRow.addView(textView5)

        val textView6 = TextView(context)
        textView6.text = "Name"
        textView6.setTextSize(15F)
        textView6.setPadding(0,2,5,2)
        textView6.setTextColor(Color.BLACK)
        tableRow.addView(textView6)

        val textView7 = TextView(context)
        textView7.text = "Date"
        textView7.setTextSize(15F)
        textView7.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView7.setPadding(0,2,5,2)
        textView7.setTextColor(Color.BLACK)
        tableRow.addView(textView7)

        val textView8 = TextView(context)
        textView8.text = "Account Balance"
        textView8.setTextSize(15F)
        textView8.setPadding(0,2,5,2)
        textView8.setTextColor(Color.BLACK)
        tableRow.addView(textView8)

        val textView9 = TextView(context)
        textView9.text = "Transaction ID"
        textView9.setTextSize(15F)
        textView9.setPadding(0,2,5,2)
        textView9.setTextColor(Color.BLACK)
        tableRow.addView(textView9)


        table_main.addView(tableRow)


//        (tableRow.parent as? ViewGroup)?.removeView(tableRow)
//        table_main.addView(tableRow)




        var count = 1
        for (i in 0 until transactions.size) {
            val recipe = transactions[i]

            if (recipe.network!="") {


                val tableRow = TableRow(context)
                tableRow.setPadding(10, 10, 10, 10)

                val textView1 = TextView(context)
                textView1.text = count.toString()
                textView1.setTextSize(15F)
                textView1.setPadding(0, 2, 10, 2)
                tableRow.addView(textView1)

                val textView2 = TextView(context)
                textView2.text = recipe.network
                textView2.setTextSize(15F)
                textView2.setPadding(0, 2, 10, 2)
                tableRow.addView(textView2)

                val textView3 = TextView(context)
                textView3.text = recipe.transaction_type
                textView3.setTextSize(15F)
                textView3.setPadding(0, 2, 10, 2)
                tableRow.addView(textView3)

                val textView4 = TextView(context)
                textView4.text = recipe.amount
                textView4.setTextSize(15F)
                textView4.setTextColor(Color.BLUE)
                textView4.setPadding(0, 2, 10, 2)
                tableRow.addView(textView4)

                val textView5 = TextView(context)
                textView5.text = recipe.phoneNo
                textView5.setTextSize(15F)
                textView5.setPadding(0, 2, 10, 2)
                tableRow.addView(textView5)

                val textView6 = TextView(context)
                textView6.text = recipe.name
                textView6.setTextSize(15F)
                textView6.setPadding(0, 2, 10, 2)
                tableRow.addView(textView6)

                val textView7 = TextView(context)
                textView7.text = recipe.date
                textView7.setTextSize(15F)
                textView7.setPadding(0, 2, 10, 2)
                tableRow.addView(textView7)

                val textView8 = TextView(context)
                textView8.text = recipe.account_balance
                textView8.setTextSize(15F)
//            textView8.setBackgroundColor(Color.GREEN)
                textView8.setTextColor(Color.BLUE)
                textView8.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView8.setPadding(0, 2, 10, 2)
                tableRow.addView(textView8)

                val textView9 = TextView(context)
                textView9.text = recipe.transaction_id
                textView9.setTextSize(15F)
                textView9.setPadding(0, 2, 10, 2)
                tableRow.addView(textView9)


                table_main.addView(tableRow)
                count = count + 1

            }






//            listItems[i] = i.toString() + " ." +
//                    recipe.sender + "\n" +
//                    recipe.network + "\n" +
//                    recipe.transaction_id + "\n" +
//                    recipe.transaction_type + "\n" +
//                    recipe.name + "\n" +
//                    recipe.phoneNo + "\n" +
//                    recipe.amount + "\n" +
//                    recipe.date + "\n" +
//                    recipe.account_balance + "\n" +
//                    recipe.message


        }

    }
}








