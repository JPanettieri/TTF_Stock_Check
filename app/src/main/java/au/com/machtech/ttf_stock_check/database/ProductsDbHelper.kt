package au.com.machtech.ttf_stock_check.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ProductsDbHelper(context: Context):
        SQLiteOpenHelper(context, databaseName, null, databaseVersion){
  companion object{
      private const val databaseVersion = 2
      private const val databaseName = "Products.db"
      private const val TBL_PRODUCTS = "Products"
//      private const val ID = " id"
      private const val CODE = "itemCode"
      private const val DESCRIPTION = "itemDescription"
      private const val COUNT = "itemCount"

  }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProducts=("CREATE TABLE "+ TBL_PRODUCTS +" ("
                + CODE +" TEXT PRIMARY KEY," + DESCRIPTION + " TEXT,"
                + COUNT + " TEXT" + ")")
        db?.execSQL(createTableProducts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PRODUCTS")
        onCreate(db)
    }

    fun insertProduct(products: Products): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, products.itemCode)
        contentValues.put(DESCRIPTION, products.itemDescription)
        contentValues.put(COUNT, products.itemCount)

        val success = db.insert(TBL_PRODUCTS, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle") //ToDo Investigate Range and Recycle for db.rawQuery
    fun getAllProducts(): ArrayList<Products>{
        val productsList: ArrayList<Products> = ArrayList()
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
        var itemCode: String
        var itemDescription: String
        var itemCount: String

        if (cursor.moveToFirst()){
            do {
                itemCode = cursor.getString(cursor.getColumnIndex("itemCode"))
                itemDescription = cursor.getString(cursor.getColumnIndex("itemDescription"))
                itemCount = cursor.getString(cursor.getColumnIndex("itemCount"))

                val products = Products(
                    itemCode = itemCode,
                    itemDescription = itemDescription,
                    itemCount = itemCount)
                productsList.add(products)
            }while (cursor.moveToNext())
        }
        return productsList
        }

    fun updateProduct (products: Products): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CODE, products.itemCode)
        contentValues.put(DESCRIPTION, products.itemDescription)
        contentValues.put(COUNT, products.itemCount)

        val success = db.update(TBL_PRODUCTS, contentValues, "itemCode='" + products.itemCode + "'", null)
        db.close()
        return success
    }

    fun deleteProductByCode(itemCode: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, itemCode)

        val success = db.delete(TBL_PRODUCTS, "itemCode='" + itemCode + "'", null)
        db.close()
        return success
    }




}



