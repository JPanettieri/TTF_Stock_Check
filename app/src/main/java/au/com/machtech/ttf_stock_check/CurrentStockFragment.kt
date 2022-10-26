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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.database.CurrentListAdapter
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper


class CurrentStockFragment : Fragment() {
    private lateinit var productsDbHelper: ProductsDbHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: CurrentListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_stock, container, false)
        val context = requireActivity().applicationContext

        //Show database in view
        recyclerView = view.findViewById(R.id.stockList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CurrentListAdapter()
        recyclerView.adapter = adapter
        productsDbHelper = ProductsDbHelper(context)
        loadProducts()
        // Inflate the layout for this fragment
        return view
    }
    private fun loadProducts() {
        val productsList = productsDbHelper.getAllProducts()
        //display data to recycler view
        adapter?.addItems(productsList)
    }

}
