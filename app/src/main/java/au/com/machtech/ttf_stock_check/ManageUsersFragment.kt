package au.com.machtech.ttf_stock_check
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import au.com.machtech.ttf_stock_check.database.UsersDb
import au.com.machtech.ttf_stock_check.database.UsersDbHelper


class ManageUsersFragment : Fragment() {


    private lateinit var usersDbHelper: UsersDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_users, container, false)
        usersDbHelper = UsersDbHelper(requireActivity().applicationContext)
        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener{addUser()}

        //Receive data from clicked line
        val updateText = ManageUsersFragmentArgs.fromBundle(requireArguments()).editUser.toList()
        view.findViewById<EditText>(R.id.insertFullName).setText(updateText[0])
        view.findViewById<EditText>(R.id.insertEmail).setText(updateText[1])
        view.findViewById<EditText>(R.id.insertPassword).setText(updateText[2])
        view.findViewById<Button>(R.id.buttonUpdate).setOnClickListener{updateUser(updateText)}

         // Inflate the layout for this fragment
        return view
    }

    private fun updateUser(updateText :List<String>){
        val fullName = view?.findViewById<EditText>(R.id.insertFullName)?.text.toString()
        val password = view?.findViewById<EditText>(R.id.insertPassword)?.text.toString()
        //check for changes
        if (fullName == updateText[0] && password == updateText[2]){
            Toast.makeText(context, "User not changed", Toast.LENGTH_SHORT).show()
            return
        }
        val usersDb = UsersDb(email = updateText[1], fullName = fullName, password = password)
        val status = usersDbHelper.updateUser(usersDb)
        if (status > -1) {
            clearEditText()
            findNavController().navigate(R.id.action_manageUsersFragment_to_usersFragment)
        }else{
            Toast.makeText(context, "Upload to database failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText(){
        view?.findViewById<EditText>(R.id.insertFullName)?.setText("")
        view?.findViewById<EditText>(R.id.insertEmail)?.setText("")
        view?.findViewById<EditText>(R.id.insertPassword)?.setText("")
        view?.findViewById<EditText>(R.id.insertFullName)?.requestFocus()
    }

    private fun addUser(){
        val fullName = view?.findViewById<EditText>(R.id.insertFullName)?.text.toString()
        val email = view?.findViewById<EditText>(R.id.insertEmail)?.text.toString()
        val password = view?.findViewById<EditText>(R.id.insertPassword)?.text.toString()

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter all fields" , Toast.LENGTH_SHORT).show()
        }
        else{
            val user = UsersDb(
                fullName = fullName,
                email = email,
                password = password)
            val status = usersDbHelper.insertUser((user))
            if (status > -1){
                Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(context, "User with this Email already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
