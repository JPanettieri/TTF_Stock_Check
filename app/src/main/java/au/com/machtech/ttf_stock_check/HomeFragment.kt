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
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        //setup bottom NavBar
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)

        val updateClick = Bundle()
        updateClick.putStringArray("updateClick", arrayOf("", "",""))
        bottomNav.setupWithNavController(navController = navHostFragment.navController)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            navHostFragment.navController.navigate(item.itemId, updateClick)
            true
        }


        //setup toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination,_ ->
            toolbar.title = destination.label
        }

        return view
    }



}