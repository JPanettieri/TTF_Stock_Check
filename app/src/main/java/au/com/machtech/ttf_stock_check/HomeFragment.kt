package au.com.machtech.ttf_stock_check

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.database.Users
import au.com.machtech.ttf_stock_check.database.UsersDbHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        //setup bottom NavBar
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
//            bottomNav.setupWithNavController(navController = navHostFragment.navController)
/*        val updateClick = arrayOf("","","").toString()
        val bundleString = Bundle()
            bottomNav.setOnItemSelectedListener{ navHostFragment.navController.navigate(a,bundleString )
                return@setOnItemSelectedListener true }*/

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