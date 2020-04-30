package com.example.agentservices.ui.ott

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.agentservices.R
import kotlinx.android.synthetic.main.fragment_ott_airtel.*
import kotlinx.android.synthetic.main.fragment_ott_airtel.view.*
import kotlinx.coroutines.processNextEventInCurrentThread

class OTTFragmentAirtel : Fragment() {


    val REQUEST_PHONE_CALL = 1
    var ott_optn = 0
    val ussdCode = "*185*1*7*"
    var optn_selected = ""



    @SuppressLint("MissingPermission")
    fun startCall() {

        if (ott_optn==0){
            optn_selected = "1"

        } else if(ott_optn==1){
            optn_selected = "2"

        } else if(ott_optn==2){
            optn_selected = "3"

        } else if(ott_optn==3){
            optn_selected = "4"

        } else if(ott_optn==4){
            optn_selected = "5"
        }

        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode  + optn_selected + "*" +
                        ott_phone_airtel.text.toString() +
                        Uri.encode(
                            "#"
                        )
            )
        )

        startActivity(callIntent)

        ott_phone_airtel.setText("")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_ott_airtel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ott_phone_airtel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = ott_phone_airtel.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="0") or (f[2].toString()=="5"))) {
                        ott_phone_airtel.setError("Requires Airtel")
                    }
                }



            }
        })


        val ott_options = resources.getStringArray(R.array.ott_option)

        val adapterArray = ArrayAdapter(context!!.applicationContext, android.R.layout.simple_spinner_dropdown_item, ott_options)
        spinner.adapter = adapterArray

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long){

                ott_optn = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                activity!!.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)            }

        }


        ott_btn_airtel.setOnClickListener {
            //
            if (ott_phone_airtel.text.toString().isEmpty()) {
                Toast.makeText(activity!!, "input phone number ", Toast.LENGTH_SHORT).show()
            }else if (ott_phone_airtel.text.length < 10){
                ott_phone_airtel.setError("Incomplete number")
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


//        back button
        home_btn.setOnClickListener {
            activity!!.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PHONE_CALL) startCall()
    }


}