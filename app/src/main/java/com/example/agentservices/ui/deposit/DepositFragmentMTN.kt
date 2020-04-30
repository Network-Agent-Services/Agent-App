package com.example.agentservices.ui.deposit

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

import kotlinx.android.synthetic.main.fragment_deposit_mtn.*
import kotlinx.android.synthetic.main.fragment_deposit_mtn.view.*

class DepositFragmentMTN : Fragment() {

    val REQUEST_PHONE_CALL = 1
    val ussdCode = "*165*4*"

    @SuppressLint("MissingPermission")
    fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode +
                        deposit_phone_mtn.text.toString() + "*" +
                        deposit_amount_mtn.getCleanIntValue().toString() + "*" + "2" +
                        Uri.encode(
                            "#"
                        )
            )
        )

        startActivity(callIntent)
        deposit_phone_mtn.setText("")
        deposit_amount_mtn.setText("")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_deposit_mtn, container, false)

        val deposit_amount = root?.deposit_amount_mtn
        deposit_amount?.setDelimiter(false)
        deposit_amount?.setSpacing(false)
        deposit_amount?.setDecimals(false)
        deposit_amount?.setSeparator(",")

        root?.deposit_phone_mtn?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = deposit_phone_mtn?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="7") or (f[2].toString()=="8"))) {
                        deposit_phone_mtn?.setError("Requires MTN")
                    }
                }



            }
        })

        val btn1 = root?.deposit_btn_mtn

        btn1?.setOnClickListener {

            if (deposit_phone_mtn.text.toString().isEmpty() or deposit_amount_mtn.text.toString().isEmpty()) {
                Toast.makeText(activity, "input phone number or amount", Toast.LENGTH_SHORT).show()
            } else if (deposit_phone_mtn.text.length < 10){
                deposit_phone_mtn.setError("Incomplete number")}
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