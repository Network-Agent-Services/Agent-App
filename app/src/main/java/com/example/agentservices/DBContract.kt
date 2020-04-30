package com.example.agentservices




import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "transactions"
            val COLUMN_ID = "ID"
            val COLUMN_TRANSACTION_ID = "transaction_id"
            val COLUMN_SENDER = "sender"
            val COLUMN_NETWORK = "network"
            val COLUMN_TRANSACTION = "transaction_type"
            val COLUMN_AMOUNT = "amount"
            val COLUMN_NAME = "name"
            val COLUMN_PHONE = "phoneNo"
            val COLUMN_DATE = "date"
            val COLUMN_ACCOUNT_BALANCE = "account_balance"
            val COLUMN_MESSAGE = "message"

        }
    }
}
