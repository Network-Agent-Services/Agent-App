package com.example.agentservices.ui.withdraw

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
import kotlinx.android.synthetic.main.fragment_withdraw_mtn.*
import kotlinx.android.synthetic.main.fragment_withdraw_mtn.view.*

class WithdrawFragmentMTN : Fragment() {

    val REQUEST_PHONE_CALL = 1
    val ussdCode = "*165*3*1*"

    @SuppressLint("MissingPermission")
    fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(
            Uri.parse(
                "tel:" +
                        ussdCode +
                        withdraw_phone_mtn.text.toString() + "*" +
                        withdraw_amount_mtn.getCleanIntValue().toString() + "*" + "2" +
                        Uri.encode(
                            "#"
                        )
            )
        )

        startActivity(callIntent)
        withdraw_phone_mtn.setText("")
        withdraw_amount_mtn.setText("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_withdraw_mtn, container, false)
        val withdraw_amount = root?.withdraw_amount_mtn
        withdraw_amount?.setDelimiter(false)
        withdraw_amount?.setSpacing(false)
        withdraw_amount?.setDecimals(false)
        withdraw_amount?.setSeparator(",")

        root?.withdraw_phone_mtn?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val f = withdraw_phone_mtn?.text.toString()

                if (f.length==3){
                    if(!((f[2].toString()=="7") or (f[2].toString()=="8"))) {
                        withdraw_phone_mtn?.setError("Requires MTN")
                    }
                }



            }
        })


        val btn1 = root?.withdraw_btn_mtn

        btn1?.setOnClickListener {

            if (withdraw_phone_mtn.text.toString().isEmpty() or withdraw_amount_mtn.text.toString().isEmpty()) {
                Toast.makeText(activity, "input phone number or amount", Toast.LENGTH_SHORT).show()
            } else if (withdraw_phone_mtn.text.length < 10){
                withdraw_phone_mtn.setError("Incomplete number")}

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