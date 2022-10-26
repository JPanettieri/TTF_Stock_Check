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
import java.io.BufferedReader
import java.io.IOException


class ProductsDbHelper(context: Context):

SQLiteOpenHelper(context, databaseName, null, databaseVersion) {
    companion object {
        private const val databaseVersion = 3
        private const val databaseName = "ProductsDb.db"
        private const val TBL_PRODUCTS = "ProductsDb"

        //      private const val ID = " id"
        private const val CODE = "itemCode"
        private const val DESCRIPTION = "itemDescription"
        private const val COUNT = "itemCount"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProducts = ("CREATE TABLE " + TBL_PRODUCTS + " ("
                + CODE + " TEXT PRIMARY KEY," + DESCRIPTION + " TEXT,"
                + COUNT + " TEXT" + ")")
        db?.execSQL(createTableProducts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PRODUCTS")
        onCreate(db)
    }


    fun insertProduct(productsDb: ProductsDb): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, productsDb.itemCode)
        contentValues.put(DESCRIPTION, productsDb.itemDescription)
        contentValues.put(COUNT, productsDb.itemCount)

        val success = db.insert(TBL_PRODUCTS, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle") //ToDo Investigate Range and Recycle for db.rawQuery
    fun getAllProducts(): ArrayList<ProductsDb> {
        val productsDbList: ArrayList<ProductsDb> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PRODUCTS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var itemCode: String
        var itemDescription: String
        var itemCount: String

        if (cursor.moveToFirst()) {
            do {
                itemCode = cursor.getString(cursor.getColumnIndex("itemCode"))
                itemDescription = cursor.getString(cursor.getColumnIndex("itemDescription"))
                itemCount = cursor.getString(cursor.getColumnIndex("itemCount"))

                val productsDb = ProductsDb(
                    itemCode = itemCode,
                    itemDescription = itemDescription,
                    itemCount = itemCount)
                productsDbList.add(productsDb)
            } while (cursor.moveToNext())
        }
        return productsDbList
    }

    fun updateProduct(productsDb: ProductsDb): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CODE, productsDb.itemCode)
        contentValues.put(DESCRIPTION, productsDb.itemDescription)
        contentValues.put(COUNT, productsDb.itemCount)

        val success =
            db.update(TBL_PRODUCTS, contentValues, "itemCode='" + productsDb.itemCode + "'", null)
        db.close()
        return success
    }

    fun deleteProductByCode(itemCode: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, itemCode)

        val success = db.delete(TBL_PRODUCTS, "itemCode='" + itemCode + "'", null)
        db.close()
        return success
    }

    fun loadFromCsv(buffer: BufferedReader) {
        val db = this.writableDatabase
        var line =buffer.readLine()
        try {
            var col = line.split(",").toTypedArray()
            while (line != null) {
                if ((col.size != 3) || (line == "Item Code,Item Description,Current Count")) {
                    line = buffer.readLine()
                    continue
                }
                col = line.split(",").toTypedArray()
                val contentValues = ContentValues(3)
                contentValues.put(CODE, col[0].trim { it <= ' ' })
                contentValues.put(DESCRIPTION, col[1].trim { it <= ' ' })
                contentValues.put(COUNT, col[2].trim { it <= ' ' })
                db.insert(TBL_PRODUCTS, null, contentValues)
                line = buffer.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        db.close()
    }

    fun clearDbAndRecreate() {
        clearDb()
        onCreate(writableDatabase)
    }

    fun clearDb() {
        writableDatabase.execSQL("DROP TABLE IF EXISTS $TBL_PRODUCTS")
    }

}




