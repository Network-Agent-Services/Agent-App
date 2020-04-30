package com.example.agentservices.ui.voicebundles

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
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.agentservices.R
import kotlinx.android.synthetic.main.fragment_voicebundles_mtn.*
import kotlinx.android.synthetic.main.fragment_voicebundles_mtn.view.*
import kotlinx.android.synthetic.main.fragment_voicebundles_mtn.view.spinner

class VoiceBundlesFragmentMTN : Fragment(){

    val REQUEST_PHONE_CALL = 1
    var voicebundles_optn = 0
    val ussdCode = "*165*7*2*1*"

    @SuppressLint("MissingPermission")
    fun startCall() {
        var optn_selected = ""

        if (voicebundles_optn==0){
            optn_selected = "1" + "*" + "1"

        } else if(voicebundles_optn==1){
            optn_selected = "1" + "*" + "2"

        } else if(voicebundles_optn==2){
            optn_selected = "1" + "*" + "3"

        } else if(voicebundles_optn==3) {
            optn_selected = "1" + "*" + "4"
        }


        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode +
                        optn_selected + "*" +
                        voicebundles_phone_mtn.text.toString() +
                        Uri.encode(
                            "#"
                        )
            )
        )

        startActivity(callIntent)
        voicebundles_phone_mtn.setText("")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_voicebundles_mtn, container, false)

        root?.voicebundles_phone_mtn?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = voicebundles_phone_mtn?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="7") or (f[2].toString()=="8"))) {
                        voicebundles_phone_mtn?.setError("Requires MTN")
                    }
                }



            }
        })

        // access the spinner
        val spinner = root?.spinner

        val voicebundles_options = resources.getStringArray(R.array.voicebundles_option_mtn)

        val adapter = ArrayAdapter(context!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item, voicebundles_options)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
//                    Toast.makeText(context,
//                         "You selected " + position, Toast.LENGTH_SHORT).show()

                voicebundles_optn = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                activity!!.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
            }
        }




        val btn1 = root?.voicebundles_btn_mtn

        btn1?.setOnClickListener {

            if (voicebundles_phone_mtn.text.toString().isEmpty()) {
                Toast.makeText(activity, "input phone numbert", Toast.LENGTH_SHORT).show()
            } else if (voicebundles_phone_mtn.text.length < 10){
                voicebundles_phone_mtn.setError("Incomplete number")}
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