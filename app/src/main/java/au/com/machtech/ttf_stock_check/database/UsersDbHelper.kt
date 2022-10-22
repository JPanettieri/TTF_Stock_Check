package au.com.machtech.ttf_stock_check.database

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
      private const val databaseName = "Users.db"
      private const val TBL_PRODUCTS = "Users"
      private const val NAME = "fullName"
      private const val EMAIL = "email"
      private const val PASSWORD = "password"

  }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProducts=("CREATE TABLE "+ TBL_PRODUCTS +" ("
                + NAME +" TEXT," + EMAIL + " TEXT PRIMARY KEY,"
                + PASSWORD + " TEXT" + ")")
        db?.execSQL(createTableProducts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PRODUCTS")
        onCreate(db)
    }

    fun insertUser(users: Users): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, users.fullName)
        contentValues.put(EMAIL, users.email)
        contentValues.put(PASSWORD, users.password)

        val success = db.insert(TBL_PRODUCTS, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle") //ToDo Investigate Range and Recycle for db.rawQuery
    fun getAllUsers(): ArrayList<Users>{
        val usersList: ArrayList<Users> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PRODUCTS"
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

                val user = Users(
                    fullName = fullName,
                    email = email,
                    password = password)
                usersList.add(user)
            }while (cursor.moveToNext())
        }
        return usersList
        }

    fun updateUser (users: Users): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, users.fullName)
        contentValues.put(EMAIL, users.email)
        contentValues.put(PASSWORD, users.password)

        val success = db.update(TBL_PRODUCTS, contentValues, "email='" + users.email + "'", null)
        db.close()
        return success
    }

    fun deleteUserByEmail(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(EMAIL, email)

        val success = db.delete(TBL_PRODUCTS, "email='" + email + "'", null)
        db.close()
        return success
    }
}



