package com.example.agentservices.ui.notification


import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agentservices.R
import com.example.agentservices.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment  : Fragment()  {

    var dbHandler: UsersDBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init db
        dbHandler = UsersDBHelper(context = context!!)

        val transactions = dbHandler!!.readAllUsers()

        val mListView: ListView = sms_listview


        val listItems = arrayOfNulls<String>(transactions.size)

        for (i in 0 until transactions.size) {
            val recipe = transactions[i]

            listItems[i] = i.toString() + ". " + recipe.sender + "\n" +
                    recipe.message


        }

        val arrayAdapter = ArrayAdapter(context!!, android.R.layout.preference_category , listItems)
        mListView.adapter = arrayAdapter


    }

}
