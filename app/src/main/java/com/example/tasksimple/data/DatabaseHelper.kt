package com.example.tasksimple.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class DatabaseHelper(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object DatabaseData {
        const val DATABASE_NAME = "TaskDB.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME         = "user_tasks"
        const val COLUMN_ID          = "_id"
        const val COLUMN_TITLE       = "task_name"
        const val COLUMN_DESCRIPTION = "task_description"
        const val COLUMN_PRIORITY    = "task_priority"
        const val COLUMN_STATUS      = "task_status"
        const val COLUMN_DUE_DATE    = "task_due_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ( $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_TITLE TEXT," +
                " $COLUMN_DESCRIPTION TEXT," +
                " $COLUMN_PRIORITY INTEGER," +
                " $COLUMN_STATUS BOOLEAN," +
                " $COLUMN_DUE_DATE DATETIME);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertNewTask(db: SQLiteDatabase?, task: Task): Long? {
        //String table, String nullColumnHack, ContentValues values
        val cv: ContentValues = ContentValues().apply {
            put(COLUMN_TITLE, task.name)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_PRIORITY, task.priority)
            put(COLUMN_STATUS, task.isFinished)
            put(COLUMN_DUE_DATE, task.dueDate)
        }

        return db?.insert(TABLE_NAME, null, cv) //the ID is returned
    }

    @SuppressLint("Recycle")
    fun getAllTasks(db: SQLiteDatabase?, finishedTasks: Boolean): ArrayList<Task> {
        val finished: Int
        if(finishedTasks) finished = 1 else finished = 0

        val rs = db?.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = $finished ORDER BY $COLUMN_PRIORITY DESC", null)

        val taskList = ArrayList<Task>()

        while(rs != null && rs.moveToNext())
        {
            //dueDate is converted into days left in the task adapter
            val taskDueDate = rs.getString(5)
            taskList.add(Task(rs.getString(0).toLong(), rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), dueDate = taskDueDate))
        }

        return taskList
    }

    fun deleteTask(db: SQLiteDatabase?, task: Task): Boolean {
        //String table, String whereClause, String[] whereArgs

        return db?.delete(TABLE_NAME, "$COLUMN_ID=? ", arrayOf<String>(task.id.toString()))!! > 0
    }

    fun updateTask(db: SQLiteDatabase?, task: Task): Boolean {
        val cv = ContentValues(). apply {
            put(COLUMN_ID, task.id)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_PRIORITY, task.priority)
            put(COLUMN_STATUS, task.isFinished)
            put(COLUMN_DUE_DATE, task.dueDate)
        }

        return db?.update(TABLE_NAME, cv, "$COLUMN_ID=? ", arrayOf<String>(task.id.toString()))!! > 0
    }

}