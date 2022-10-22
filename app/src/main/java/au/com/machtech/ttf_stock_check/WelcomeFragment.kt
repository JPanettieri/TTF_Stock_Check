package au.com.machtech.ttf_stock_check

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import au.com.machtech.ttf_stock_check.database.Users
import au.com.machtech.ttf_stock_check.database.UsersDbHelper

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        // set initial admin
        val usersDbHelper = UsersDbHelper(requireActivity().applicationContext)
        val admin = Users(
            fullName = "admin",
            email = "admin@initialStart.com",
            password = "password")
        usersDbHelper.insertUser((admin))
        //Login
        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.editTextEmail).toString()
            val password = view.findViewById<EditText>(R.id.editTextPassword).toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
            }

        }

        //Registration button clicked
        view.findViewById<Button>(R.id.button_registration).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_welcomeFragment_to_registerFragment)
        )
        return view
    }

}