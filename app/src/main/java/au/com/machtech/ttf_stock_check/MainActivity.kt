package au.com.machtech.ttf_stock_check
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val file = assets.open("TTF_Products.csv")
        val context = applicationContext
        val filestream = InputStreamReader(file)
        val buffer = BufferedReader(filestream)
        val sqliteHelper = ProductsDbHelper(context)
        if (sqliteHelper.getAllProducts().count() < 5 ){
            sqliteHelper.loadFromCsv(buffer)
        }

    }



}
