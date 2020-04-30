package com.example.agentservices

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_SENDER, user.sender)
        values.put(DBContract.UserEntry.COLUMN_NETWORK, user.network)
        values.put(DBContract.UserEntry.COLUMN_TRANSACTION_ID, user.transaction_id)
        values.put(DBContract.UserEntry.COLUMN_TRANSACTION, user.transaction_type)
        values.put(DBContract.UserEntry.COLUMN_AMOUNT, user.amount)
        values.put(DBContract.UserEntry.COLUMN_NAME, user.name)
        values.put(DBContract.UserEntry.COLUMN_PHONE, user.phoneNo)
        values.put(DBContract.UserEntry.COLUMN_DATE, user.date)
        values.put(DBContract.UserEntry.COLUMN_ACCOUNT_BALANCE, user.account_balance)
        values.put(DBContract.UserEntry.COLUMN_MESSAGE, user.message)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(trabsactionId : String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_TRANSACTION_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(trabsactionId )
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }
//
//    fun readUser(trabsactionId: String): ArrayList<UserModel> {
//        val users = ArrayList<UserModel>()
//        val db = writableDatabase
//        var cursor: Cursor? = null
//        try {
//            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_TRANSACTION_ID + "='" + trabsactionId + "'", null)
//        } catch (e: SQLiteException) {
//            // if table not yet present, create it
//            db.execSQL(SQL_CREATE_ENTRIES)
//            return ArrayList()
//        }
//
//        var sender: String
//        var message: String
//        if (cursor!!.moveToFirst()) {
//            while (cursor.isAfterLast == false) {
//                sender = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SENDER))
//                message = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_MESSAGE))
//
//                users.add(UserModel(trabsactionId, sender, message))
//                cursor.moveToNext()
//            }
//        }
//        return users
//    }

    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

//        var trabsactionId : String
        var sender: String
        var transaction_id: String
        var network: String
        var transaction_type: String
        var amount: String
        var name: String
        var phoneNo: String
        var date: String
        var account_balance: String
        var message: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                sender = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SENDER))
                transaction_id = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TRANSACTION_ID))
                network = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NETWORK))
                transaction_type = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TRANSACTION))
                amount = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_AMOUNT))
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                phoneNo = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PHONE))
                date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATE))
                account_balance = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ACCOUNT_BALANCE))
                message = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_MESSAGE))

                users.add(UserModel(sender, transaction_id, network, transaction_type, amount, name, phoneNo, date, account_balance, message))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "transactions.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_TRANSACTION_ID + " TEXT," +
                    DBContract.UserEntry.COLUMN_SENDER + " TEXT," +
                    DBContract.UserEntry.COLUMN_NETWORK + " TEXT," +
                    DBContract.UserEntry.COLUMN_TRANSACTION + " TEXT," +
                    DBContract.UserEntry.COLUMN_AMOUNT + " TEXT," +
                    DBContract.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_PHONE + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATE + " TEXT," +
                    DBContract.UserEntry.COLUMN_ACCOUNT_BALANCE + " TEXT," +
                    DBContract.UserEntry.COLUMN_MESSAGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}