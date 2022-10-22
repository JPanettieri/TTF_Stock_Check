package au.com.machtech.ttf_stock_check.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.R
import au.com.machtech.ttf_stock_check.R.layout.card_items_products

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productsList : ArrayList<Products> = ArrayList()
    private var onClickItem: ((Products) -> Unit)? = null
    private var onClickDeleteItem: ((Products) -> Unit)? = null

    fun addItems(items: ArrayList<Products>) {
        this.productsList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (Products) ->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (Products) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate((card_items_products), parent, false))


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val products = productsList[position]
        holder.bindView(products)
        holder.itemView.setOnClickListener{onClickItem?.invoke(products)}
        holder.buttonDelete.setOnClickListener{onClickDeleteItem?.invoke(products)}
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view ){
        private var itemCode = view.findViewById<TextView>(R.id.tvItemCode)
        private var itemDescription = view.findViewById<TextView>(R.id.tvEmail)
        private var itemCount = view.findViewById<TextView>(R.id.tvPassword)
         var buttonDelete = view.findViewById<Button>(R.id.buttonDelete)!!

        fun bindView(products: Products){
            itemCode.text = products.itemCode
            itemDescription.text = products.itemDescription
            itemCount.text = products.itemCount
        }
    }

}