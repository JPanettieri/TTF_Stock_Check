package au.com.machtech.ttf_stock_check.database
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class UsersDbHelper(context: Context):
        SQLiteOpenHelper(context, databaseName, null, databaseVersion){
  companion object{
      private const val databaseVersion = 1
      private const val databaseName = "UsersDb.db"
      private const val TBL_USERSDb = "UsersDb"
      private const val NAME = "fullName"
      private const val EMAIL = "email"
      private const val PASSWORD = "password"

  }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProducts=("CREATE TABLE "+ TBL_USERSDb +" ("
                + NAME +" TEXT," + EMAIL + " TEXT PRIMARY KEY,"
                + PASSWORD + " TEXT" + ")")
        db?.execSQL(createTableProducts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USERSDb")
        onCreate(db)
    }

    fun insertUser(usersDb: UsersDb): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, usersDb.fullName)
        contentValues.put(EMAIL, usersDb.email)
        contentValues.put(PASSWORD, usersDb.password)

        val success = db.insert(TBL_USERSDb, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle") //ToDo Investigate Range and Recycle for db.rawQuery
    fun getAllUsers(): ArrayList<UsersDb>{
        val usersDbList: ArrayList<UsersDb> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USERSDb"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var fullName: String
        var email: String
        var password: String

        if (cursor.moveToFirst()){
            do {
                fullName = cursor.getString(cursor.getColumnIndex("fullName"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                password = cursor.getString(cursor.getColumnIndex("password"))

                val user = UsersDb(
                    fullName = fullName,
                    email = email,
                    password = password)
                usersDbList.add(user)
            }while (cursor.moveToNext())
        }
        return usersDbList
        }

    fun updateUser (usersDb: UsersDb): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, usersDb.fullName)
        contentValues.put(EMAIL, usersDb.email)
        contentValues.put(PASSWORD, usersDb.password)

        val success = db.update(TBL_USERSDb, contentValues, "email='" + usersDb.email + "'", null)
        db.close()
        return success
    }

    fun deleteUserByEmail(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(EMAIL, email)

        val success = db.delete(TBL_USERSDb, "email='" + email + "'", null)
        db.close()
        return success
    }
    fun userExists(searchItem :String): Boolean {
        val db = this.writableDatabase
        val columns = arrayOf(EMAIL)
        val selection = EMAIL + " =?"
        val selectionArgs = arrayOf(searchItem)
        val limit = "1"

        val cursor: Cursor =
            db.query(TBL_USERSDb, columns, selection, selectionArgs, null, null, null, limit)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun passwordCorrect(searchItem :String): Boolean {
        val db = this.writableDatabase
        val columns = arrayOf(PASSWORD)
        val selection = PASSWORD + " =?"
        val selectionArgs = arrayOf(searchItem)
        val limit = "1"

        val cursor: Cursor =
            db.query(TBL_USERSDb, columns, selection, selectionArgs, null, null, null, limit)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }



}



