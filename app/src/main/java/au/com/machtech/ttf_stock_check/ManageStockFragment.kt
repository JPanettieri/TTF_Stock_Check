package au.com.machtech.ttf_stock_check

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.navigation.Navigation

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import au.com.machtech.ttf_stock_check.database.Products
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        Log.e("clicked", updateText.toString())
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
        val products = Products(itemCode = updateText[0],itemDescription = itemDescription,itemCount = itemCount)
        val status = sqLiteHelper.updateProduct(products)
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
            val products = Products(
                itemCode = itemCode,
                itemDescription = itemDescription,
                itemCount = itemCount)
            val status = sqLiteHelper.insertProduct((products))
            Log.e("tableInsert", sqLiteHelper.getAllProducts().toString())
            if (status > -1){
                Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(context, "Item code already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

