package au.com.machtech.ttf_stock_check

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.R.layout.fragment_stock
import au.com.machtech.ttf_stock_check.database.ProductAdapter
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper


class StockFragment : Fragment() {

    private lateinit var productsDbHelper: ProductsDbHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(fragment_stock, container, false)
        val context = requireActivity().applicationContext

        //Show database in view
        recyclerView = view.findViewById(R.id.stockList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter()
        recyclerView.adapter = adapter
        productsDbHelper = ProductsDbHelper(context)
        loadProducts()
        //Clicked Item Toast and select to edit
        adapter?.setOnClickItem {
            Toast.makeText(context, it.itemCode, Toast.LENGTH_SHORT)
                .show()
            //create bundle of clicked data
            val bundleList = ArrayList<String>()
            bundleList.add(it.itemCode.toString())
            bundleList.add(it.itemDescription.toString())
            bundleList.add(it.itemCount.toString())
            val updateClick = bundleList.toTypedArray()
            //Start safe navigate passing clicked line data
            val action = StockFragmentDirections.actionGlobalManageStockFragment(updateClick)
            requireActivity().findNavController(R.id.stockList).navigate(action)
        }

        adapter?.setOnClickDeleteItem {
            deleteProduct(it.itemCode.toString())
        }

        return view

    }

    private fun deleteProduct(itemCode: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            productsDbHelper.deleteProductByCode(itemCode)
            loadProducts()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadProducts() {
        val productsList = productsDbHelper.getAllProducts()
        Log.e("products", "${productsList.size}")
        //display data to recycler view
        adapter?.addItems(productsList)
    }


}