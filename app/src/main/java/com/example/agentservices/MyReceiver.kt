package com.example.agentservices

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onReceive(context: Context, intent: Intent) {

        usersDBHelper = UsersDBHelper(context)


        val extras = intent.extras

        if (extras != null){
            val sms = extras.get("pdus") as Array<*>
//            Log.e(TAG,sms.toString())


            var smsMessageStr = ""
            var phone = ""
            for (i in sms.indices) {
//                Log.e(TAG,sms[].toString())

                val format = extras.getString("format")
                var smsMessage = if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                }else{
                    SmsMessage.createFromPdu(sms[i] as ByteArray)

                }
                val phoneNumber = smsMessage.originatingAddress
                val messageText = smsMessage.messageBody

                if(i == sms.lastIndex){
                    phone += phoneNumber.toString()
                }

//                smsMessageStr = "$phoneNumber\n"
                smsMessageStr += messageText



            }

            if (phone.isNotEmpty() and smsMessageStr.isNotEmpty()) {

                Toast.makeText(context, phone +": " + smsMessageStr, Toast.LENGTH_LONG).show()

                Log.e(TAG, smsMessageStr)


                if (smsMessageStr.startsWith("Y'ello. You have withdrawn")){
//                 withdraw mtn
                    val network = "MTN"
                    val transaction_type = "Withdraw"
                    val amount = smsMessageStr.substringAfter("Y'ello. You have withdrawn UGX ").substringBefore(" ")
                    val name = smsMessageStr.substringAfter("to ").substringBefore("256")
                    val phoneNo = smsMessageStr.substringAfter(name).substringBefore(" on")
                    val date = smsMessageStr.substringAfter("on ").substringBefore('.')
                    val account_balance = smsMessageStr.substringAfter("Your new MTN Mobile Money Agent balance is UGX ").substringBefore('.')
                    val transaction_id = smsMessageStr.substringAfter("Financial Transaction Id: ").substringBefore(".")
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else if (smsMessageStr.startsWith("Y'ello. You have sent")){
//                    deposit mtn
                    val network = "MTN"
                    val transaction_type = "Deposit"
                    val amount = smsMessageStr.substringAfter("Y'ello. You have sent UGX ").substringBefore(" ")
                    val name = smsMessageStr.substringAfter("to ").substringBefore(" on")
                    val phoneNo = ""
                    val date = smsMessageStr.substringAfter("on ").substringBefore('.')
                    val account_balance = smsMessageStr.substringAfter("Your new MTN MoMo Agent balance is UGX").substringBefore('.')
                    val transaction_id = smsMessageStr.substringAfter("Transaction ID: ").substringBefore(".")
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else if (smsMessageStr.startsWith("You have deposited")){

//                    withdraw airtel
                    val network = "Airtel"
                    val transaction_type = "Deposit"
                    val amount = smsMessageStr.substringAfter("You have deposited UGX ").substringBefore(" ")
                    val name = ""
                    val phoneNo = smsMessageStr.substringAfter("Mobile Number: ").substringBefore(" ")
                    val date  = smsMessageStr.substringAfter("on ").substringBefore(" Mobile")
                    val account_balance = smsMessageStr.substringAfter("Your bal: UGX ").substringBefore('.')
                    val transaction_id = smsMessageStr.substringAfter("Trans ID: ").substringBefore(".")
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else if (smsMessageStr.startsWith("Cash Out")){
//                    deposit airtel
                    val network = "Airtel"
                    val transaction_type = "Withdraw"
                    val amount = smsMessageStr.substringAfter("Amount UGX ").substringBefore(" ")
                    val name = ""
                    val phoneNo = smsMessageStr.substringAfter("Mobile Number: ").substringBefore(" ")
                    val date = smsMessageStr.substringAfter("Cash Out ").substringBefore(" Mobile Number")
                    val account_balance = smsMessageStr.substringAfter("Your bal UGX ").substringBefore('.')
                    val transaction_id = smsMessageStr.substringAfter("Txn Id: ").substringBefore(".")
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else if (smsMessageStr.startsWith("Msg:210:Recharge")){
//                    airtel Airtime
//         Msg:210:Recharge 1500 of UGX.. to 704372702 is successful. Your new balance is 19050 UGX.. Transaction number R200423.1537.220139
                    val network = "Airtel"
                    val transaction_type = "Airtime"
                    val amount = smsMessageStr.substringAfter("Msg:210:Recharge ").substringBefore(" ")
                    val name = ""
                    val phoneNo = smsMessageStr.substringAfter("to ").substringBefore(" ")
                    val date = ""
                    val account_balance = smsMessageStr.substringAfter("Your new balance is ").substringBefore(' ')
                    val transaction_id = ""
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = "0" + phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else if (phone.startsWith("MTNEASYLOAD") && smsMessageStr.startsWith("Topup for ")){
//                    Mtn Airtime
//                  Topup for UGX.500.00 made to customer mobile no. 256788821487 on 23/04/2020 at 03:13PM. Your updated stock balance is UGX.28400.00. Ref:988388979                    val network = "MTN"
                    val network = "MTN"
                    val transaction_type = "Airtime"
                    val amount = smsMessageStr.substringAfter("Topup for UGX.").substringBefore(".")
                    val name = ""
                    val phoneNo = smsMessageStr.substringAfter("customer mobile no. 256").substringBefore(" ")
                    val date = smsMessageStr.substringAfter("on ").substringBefore(" ")
                    val account_balance = smsMessageStr.substringAfter("Your updated stock balance is UGX.").substringBefore('.')
                    val transaction_id = ""
                    val message = smsMessageStr
                    val sender = phone

                    Log.e(TAG,"I doesnot save it, if its so, then" + amount + phoneNo + account_balance)

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = "0" + phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))


                }else if (smsMessageStr.startsWith("Your payment of UGX ")){
//                    Mtn Airtime
//                    Your payment of UGX 1,000 has been received by VOICEBUNDLES .Your balance is 72,359.Thank you for using AirtelMoney. Wed Apr 22 17:52:14 EAT 2020
                    val network = "Airtel"
                    val transaction_type = "Voice bundles"
                    val amount = smsMessageStr.substringAfter("Your payment of UGX ").substringBefore(" ")
                    val name = ""
                    val phoneNo = ""
                    val date = ""
                    val account_balance = smsMessageStr.substringAfter("Your balance is ").substringBefore('.')
                    val transaction_id = ""
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))

                }else{
//                    other messages
                    val network = ""
                    val transaction_type = ""
                    val amount = ""
                    val name = ""
                    val phoneNo = ""
                    val date = ""
                    val account_balance = ""
                    val transaction_id = ""
                    val message = smsMessageStr
                    val sender = phone

                    usersDBHelper.insertUser(UserModel(
                        sender = sender,
                        network = network,
                        transaction_type = transaction_type,
                        amount = amount,
                        name = name,
                        phoneNo = phoneNo,
                        date = date,
                        account_balance = account_balance,
                        transaction_id = transaction_id,
                        message = message))
                }



                 }



            }


        }
    }


