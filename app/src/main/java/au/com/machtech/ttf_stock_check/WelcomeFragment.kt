package au.com.machtech.ttf_stock_check
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import au.com.machtech.ttf_stock_check.database.UsersDb
import au.com.machtech.ttf_stock_check.database.UsersDbHelper


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        // set initial admin
        val db = UsersDbHelper(requireActivity().applicationContext)
        val admin = UsersDb(
            fullName = "admin",
            email = "admin@startup.com",
            password = "admin")
        if(!db.userExists(admin.email.toString())){
            db.insertUser((admin))
        }

        //Login
        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.editTextEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.editTextPassword).text.toString()
            if (db.userExists(email) && db.passwordCorrect(password)) {
                findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
            }else{
                Toast.makeText(context, "Email or Password are incorrect, please try again", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        //Registration button clicked
        view.findViewById<Button>(R.id.viewStock).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_welcomeFragment_to_currentStockFragment)
        )
        return view
    }


}