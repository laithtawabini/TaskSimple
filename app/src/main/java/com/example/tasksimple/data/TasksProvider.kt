package com.example.tasksimple.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

class TasksProvider: ContentProvider() {

    companion object {
        val PROVIDER_NAME = "com.example.tasksimple/TasksProvider"
        val URL = "content://$PROVIDER_NAME/${DatabaseHelper.TABLE_NAME}"
        val CONTENT_URL = Uri.parse(URL)
        val DB_ATTRIBUTES = DatabaseHelper
    }

    lateinit var db: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(): Boolean {
        db = DatabaseHelper(context).writableDatabase
        return db != null
    }


    override fun insert(uri: Uri, cv: ContentValues?): Uri? {
        db.insert(DatabaseHelper.TABLE_NAME, null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }


    override fun update(uri: Uri, cv: ContentValues?, condition: String?, conditionVal: Array<out String>?): Int {
        var updatedRecordsCount = db.update(DatabaseHelper.TABLE_NAME, cv, condition, conditionVal)
        context?.contentResolver?.notifyChange(uri, null)
        return updatedRecordsCount
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun delete(uri: Uri, condition: String?, conditionVal: Array<out String>?): Int {
        var deletedRecordsCount = db.delete(DatabaseHelper.TABLE_NAME, condition, conditionVal)
        context?.contentResolver?.notifyChange(uri, null)
        return deletedRecordsCount
    }


    override fun query(
        uri: Uri,
        selectedCols: Array<out String>?,
        condition: String?,
        conditionVal: Array<out String>?,
        order: String?
    ): Cursor? {
        return db.query(DatabaseHelper.TABLE_NAME, selectedCols, condition, conditionVal, null, null, order)
    }


    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.${DatabaseHelper.TABLE_NAME}"
    }

}