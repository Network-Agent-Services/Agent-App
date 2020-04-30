package com.example.agentservices.ui.easyload

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
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.agentservices.R
import kotlinx.android.synthetic.main.fragment_easyload_mtn.*
import kotlinx.android.synthetic.main.fragment_easyload_mtn.view.*

class EasyLoadFragmentMTN : Fragment(){

    val REQUEST_PHONE_CALL = 1
    val ussdCode = "*140*1*"

    @SuppressLint("MissingPermission")
    fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" + ussdCode +
                        easyload_phone_mtn.text.toString() + "*" + "4" + "*" +
                        easyload_amount_mtn.getCleanIntValue().toString() + Uri.encode(
                    "#"
                )
            )
        )

        startActivity(callIntent)
        easyload_phone_mtn.setText("")
        easyload_amount_mtn.setText("")

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_easyload_mtn, container, false)

        val easyload_amount = root?.easyload_amount_mtn
        easyload_amount?.setDelimiter(false)
        easyload_amount?.setSpacing(false)
        easyload_amount?.setDecimals(false)
        easyload_amount?.setSeparator(",")

        root?.easyload_phone_mtn?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = easyload_phone_mtn?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="7") or (f[2].toString()=="8"))) {
                        easyload_phone_mtn?.setError("Requires MTN")
                    }
                }



            }
        })

        val btn1 = root?.easyload_btn_mtn

        btn1?.setOnClickListener {

            if (easyload_phone_mtn.text.toString().isEmpty() or easyload_amount_mtn.text.toString().isEmpty()) {
                Toast.makeText(activity, "input phone number or amount", Toast.LENGTH_SHORT).show()
            }  else if (easyload_phone_mtn.text.length < 10){
                easyload_phone_mtn.setError("Incomplete number")}
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