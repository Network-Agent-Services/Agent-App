package com.example.agentservices.ui.databundles

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.agentservices.R
import kotlinx.android.synthetic.main.fragment_databundles_airtel.*
import kotlinx.android.synthetic.main.fragment_databundles_airtel.view.*

class DataBundlesFragmentAirtel : Fragment() {


    val REQUEST_PHONE_CALL = 1
    var databundles_optn = 0
    val ussdCode = "*185*1*5*"



    @SuppressLint("MissingPermission")
    fun startCall() {

        var optn_selected = ""

        if (databundles_optn==0){
            optn_selected = "1" + "*" + "1"

        } else if(databundles_optn==1){
            optn_selected = "1" + "*" + "2"

        } else if(databundles_optn==2){
            optn_selected = "1" + "*" + "3"

        } else if(databundles_optn==3){
            optn_selected = "1" +  "*" +"4"

        } else if(databundles_optn==4){
            optn_selected = "2" + "*" + "1"

        }else if(databundles_optn==5){
            optn_selected = "2" + "*" + "2"
        }

        else if(databundles_optn==6){
            optn_selected = "2" + "*" + "3"
        }

        else if(databundles_optn==4){
        optn_selected = "1" + "*" + "4"
        }

        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode +
                        databundles_phone_airtel.text.toString() + "*" +
                        optn_selected +
                        Uri.encode(
                            "#"
                        )
            )
        )

        startActivity(callIntent)
        databundles_phone_airtel.setText("")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_databundles_airtel, container, false)


        root?.databundles_phone_airtel?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = databundles_phone_airtel?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="0") or (f[2].toString()=="5"))) {
                        databundles_phone_airtel?.setError("Requires Airtel")
                    }
                }



            }
        })


        // access the spinner
        val spinner = root?.spinner

        val databundles_options = resources.getStringArray(R.array.databundles_option_airtel)

        val adapter = ArrayAdapter(context!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item, databundles_options)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
//                    Toast.makeText(context,
//                         "You selected " + position, Toast.LENGTH_SHORT).show()

                databundles_optn = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                activity!!.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
            }
        }




        val btn1 = root?.databundles_btn_airtel

        btn1?.setOnClickListener {

            if (databundles_phone_airtel.text.toString().isEmpty()) {
                Toast.makeText(activity, "input phone number ", Toast.LENGTH_SHORT).show()
            } else if (databundles_phone_airtel.text.length < 10){
                databundles_phone_airtel.setError("Incomplete number")
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