package au.com.machtech.ttf_stock_check
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.database.UserAdapter
import au.com.machtech.ttf_stock_check.database.UsersDbHelper

class UsersFragment : Fragment() {
    private lateinit var usersDbHelper: UsersDbHelper
    private var adapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        val context = requireActivity().applicationContext

        //Show database in view
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
        usersDbHelper = UsersDbHelper(context)
        loadUsers()
        //Clicked Item Toast and select to edit
        adapter?.setOnClickUser {
            Toast.makeText(context, it.email, Toast.LENGTH_SHORT)
                .show()
            //create bundle of clicked data
            val bundleList = ArrayList<String>()
            bundleList.add(it.fullName.toString())
            bundleList.add(it.email.toString())
            bundleList.add(it.password.toString())
            val editUser = bundleList.toTypedArray()
            //Start safe navigate passing clicked line data
            val action = UsersFragmentDirections.actionUsersFragmentToManageUsersFragment(editUser)
            requireActivity().findNavController(R.id.usersList).navigate(action)
        }

        adapter?.setOnClickDeleteUser {
            deleteUser(it.email.toString())
        }
        return view
    }
    private fun deleteUser(email: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are you sure you want to delete user?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            usersDbHelper.deleteUserByEmail(email)
            loadUsers()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadUsers() {
        val usersList = usersDbHelper.getAllUsers()
        //display data to recycler view
        adapter?.addUser(usersList)
    }
}