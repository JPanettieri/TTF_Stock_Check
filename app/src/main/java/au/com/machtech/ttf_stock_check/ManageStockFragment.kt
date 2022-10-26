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
import au.com.machtech.ttf_stock_check.database.ProductsDb
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper

class ManageStockFragment : Fragment() {

    private lateinit var sqLiteHelper: ProductsDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireActivity().applicationContext
        val view = inflater.inflate(R.layout.fragment_manage_stock, container, false)

        sqLiteHelper = ProductsDbHelper(context)
        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener{addProduct()}

        //Receive data from clicked line
        val updateText = ManageStockFragmentArgs.fromBundle(requireArguments()).updateClick.toList()
        view.findViewById<EditText>(R.id.insertItemCode).setText(updateText[0])
        view.findViewById<EditText>(R.id.insertItemDescription).setText(updateText[1])
        view.findViewById<EditText>(R.id.insertItemCount).setText(updateText[2])
        view.findViewById<Button>(R.id.buttonUpdate).setOnClickListener{updateItem(updateText)}
          // Inflate the layout for this fragment
        return view
    }

    private fun updateItem(updateText :List<String>){
        val itemDescription = view?.findViewById<EditText>(R.id.insertItemDescription)?.text.toString()
        val itemCount = view?.findViewById<EditText>(R.id.insertItemCount)?.text.toString()
        //check for changes
        if (itemDescription == updateText[1] && itemCount == updateText[2]){
            Toast.makeText(context, "Product not changed", Toast.LENGTH_SHORT).show()
            return
        }
        val productsDb = ProductsDb(itemCode = updateText[0],itemDescription = itemDescription,itemCount = itemCount)
        val status = sqLiteHelper.updateProduct(productsDb)
        if (status > -1) {
            clearEditText()
            findNavController().navigate(R.id.action_manageStockFragment_to_stockFragment)
        }else{
            Toast.makeText(context, "Upload to database failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText(){
        view?.findViewById<EditText>(R.id.insertItemCode)?.setText("")
        view?.findViewById<EditText>(R.id.insertItemDescription)?.setText("")
        view?.findViewById<EditText>(R.id.insertItemCount)?.setText("")
        view?.findViewById<EditText>(R.id.insertItemCode)?.requestFocus()
    }

    private fun addProduct(){
        val itemCode = view?.findViewById<EditText>(R.id.insertItemCode)?.text.toString()
        val itemDescription = view?.findViewById<EditText>(R.id.insertItemDescription)?.text.toString()
        val itemCount = view?.findViewById<EditText>(R.id.insertItemCount)?.text.toString()

        if (itemCode.isEmpty() || itemDescription.isEmpty() || itemCount.isEmpty()) {
            Toast.makeText(context, "Please enter all fields" , Toast.LENGTH_SHORT).show()
        }
        else{
            val productsDb = ProductsDb(
                itemCode = itemCode,
                itemDescription = itemDescription,
                itemCount = itemCount)
            val status = sqLiteHelper.insertProduct((productsDb))
            if (status > -1){
                Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(context, "Item code already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

/*    private fun readCsv(bufferedReader: BufferedReader): List<ProductsDb> {
        val header = bufferedReader.readLine()
        return bufferedReader.lineSequence().filter { it.isNotBlank() }.map {
            val (itemCode, itemDescription, itemCount) = it.split(',', ignoreCase = true, limit = 3)
            ProductsDb(itemCode = itemCode, itemDescription, itemCount.removeSurrounding("\""))
                }.toList()
        }*/



}

