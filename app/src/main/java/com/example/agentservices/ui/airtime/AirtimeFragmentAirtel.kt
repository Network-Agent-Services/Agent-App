package com.example.agentservices.ui.airtime

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.agentservices.R
import kotlinx.android.synthetic.main.fragment_airtime_airtel.*
import kotlinx.android.synthetic.main.fragment_airtime_airtel.view.*
import java.util.*
import android.widget.Toast.makeText as makeText1


class AirtimeFragmentAirtel : Fragment() {

    val REQUEST_PHONE_CALL = 1
    val ussdCode = "*185*1*4*"

    @SuppressLint("MissingPermission")
    fun startCall() {
        println(airtime_amount_airtel.getCleanIntValue().toString())
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode +
                        airtime_phone_airtel.text.toString() + "*" +
                        airtime_amount_airtel.getCleanIntValue().toString() +
                        Uri.encode(
                    "#"
                )
            )
        )

        startActivity(callIntent)
        airtime_phone_airtel.setText("")
        airtime_amount_airtel.setText("")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_airtime_airtel, container, false)

        val airtime_amount = root?.airtime_amount_airtel
        airtime_amount?.setDelimiter(false)
        airtime_amount?.setSpacing(false)
        airtime_amount?.setDecimals(false)
        airtime_amount?.setSeparator(",")

        root?.airtime_phone_airtel?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = airtime_phone_airtel?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="0") or (f[2].toString()=="5"))) {
                        airtime_phone_airtel?.setError("Requires Airtel")
                    }
                }



            }
        })



        val btn1 = root?.airtime_btn_airtel

        btn1?.setOnClickListener {

            if (airtime_phone_airtel.text.toString().isEmpty() or
                airtime_amount_airtel.getCleanIntValue().toString().isEmpty()) {
                makeText1(context, "input phone number or amount", Toast.LENGTH_SHORT).show()

            } else if (airtime_phone_airtel.text.length < 10){
                airtime_phone_airtel.setError("Incomplete number")
            }

            else {

                if (ActivityCompat.checkSelfPermission(
                        this.requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this.requireActivity(), (arrayOf(
                            Manifest.permission.CALL_PHONE
                        )), REQUEST_PHONE_CALL
                    )

                } else {

                    startCall()

                }
            }
        }

        val btnUbicacion = root.findViewById<View>(R.id.home_btn) as Button
        btnUbicacion.setOnClickListener{
            activity!!.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
        }
        return root

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PHONE_CALL) startCall()
    }

}


