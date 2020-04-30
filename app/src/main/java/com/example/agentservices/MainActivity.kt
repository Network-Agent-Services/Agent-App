package com.example.agentservices

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    lateinit var usersDBHelper : UsersDBHelper


//Sms Activity
    data class SmsData(val senderName: String?, val date: String, val message: String) {
    }

    private val requestReceivesms: Int = 2
    val REQUEST_PHONE_CALL = 1


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requestReceivesms){}
    }


    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        usersDBHelper = UsersDBHelper(this)



        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS),requestReceivesms)

        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, (arrayOf(
                    Manifest.permission.CALL_PHONE
                )), REQUEST_PHONE_CALL
            )

        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_transactions)
//            withItems(view)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        val fab2: FloatingActionButton = findViewById(R.id.fab2)
        fab2.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_notification)
//            withItems(view)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home,R.id.nav_notification,  R.id.nav_databundles_airtel, R.id.nav_databundles_mtn, R.id.nav_voicebundles_airtel, R.id.nav_voicebundles_mtn, R.id.nav_easyload_airtel, R.id.nav_easyload_mtn, R.id.nav_withdraw_airtel,R.id.nav_withdraw_mtn, R.id.nav_deposit_airtel,R.id.nav_deposit_mtn,
                R.id.nav_airtime_airtel,R.id.nav_airtime_mtn, R.id.nav_transactions, R.id.nav_account), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

//    fun addUser(v: View){
//        val transactionId = "0012"
//        val sender = "MTN money"
//        val message = "You have deposited 780,000 on MTN"
//        usersDBHelper.insertUser(UserModel(transactionId = transactionId,sender = sender, message = message))
//
//        Toast.makeText(this, "Transaction added", Toast.LENGTH_LONG).show()
//
//    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
