package au.com.machtech.ttf_stock_check

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import au.com.machtech.ttf_stock_check.database.Products
import au.com.machtech.ttf_stock_check.database.Users
import au.com.machtech.ttf_stock_check.database.UsersDbHelper
import java.io.*



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



}
