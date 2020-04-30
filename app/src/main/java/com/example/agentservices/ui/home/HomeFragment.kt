package com.example.agentservices.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.appcompat.app.AlertDialog
import android.widget.Toast.makeText as makeText1

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.agentservices.R
import com.example.agentservices.R.*
import com.example.agentservices.ui.airtime.AirtimeFragmentAirtel
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(layout.fragment_home, container, false)


        fun withItems(
            view: View,
          nav_airtel: Int,
          nav_mtn: Int) {

            val items = arrayOf("AIRTEL", "MTN")
            val builder = AlertDialog.Builder(context!!)
            with(builder)
            {
                setTitle("Select Agent Line")
                setItems(items) { dialog, which ->
                    if (items[which] == items[0]){
                        activity!!.findNavController(R.id.nav_host_fragment).navigate(nav_airtel)
                    } else if(items[which] == items[1]){
                        activity!!.findNavController(R.id.nav_host_fragment).navigate(nav_mtn)
                    }

                }

                show()
            }
        }




        val easyloadCard = root?.easyload
        val airtimeCard = root?.airtime
        val withdrawCard = root?.withdraw
        val depositCard = root?.deposit
        val ottCard = root?.ott
        val voicebundlesCard = root?.voicebundles
        val databundlesCard = root?.databundles


        ottCard?.setOnClickListener{
            withItems(ott,R.id.nav_ott_airtel,R.id.nav_ott_mtn)
        }

        easyloadCard?.setOnClickListener{
            withItems(easyload,R.id.nav_easyload_airtel,R.id.nav_easyload_mtn)
        }

        airtimeCard?.setOnClickListener{
            withItems(airtime,R.id.nav_airtime_airtel,R.id.nav_airtime_mtn)
        }

        withdrawCard?.setOnClickListener{
            withItems(withdraw,R.id.nav_withdraw_airtel,R.id.nav_withdraw_mtn)
        }

        depositCard?.setOnClickListener{
            withItems(deposit,R.id.nav_deposit_airtel,R.id.nav_deposit_mtn)
        }

        voicebundlesCard?.setOnClickListener{
            withItems(deposit,R.id.nav_voicebundles_airtel,R.id.nav_voicebundles_mtn)
        }

        databundlesCard?.setOnClickListener{
            withItems(deposit,R.id.nav_databundles_airtel,R.id.nav_databundles_mtn)
        }

    return root
    }

}