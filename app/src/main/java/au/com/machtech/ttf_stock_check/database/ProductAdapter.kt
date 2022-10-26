package au.com.machtech.ttf_stock_check.database
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.R
import au.com.machtech.ttf_stock_check.R.layout.card_items_manage_products

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productsDbList : ArrayList<ProductsDb> = ArrayList()
    private var onClickItem: ((ProductsDb) -> Unit)? = null
    private var onClickDeleteItem: ((ProductsDb) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<ProductsDb>) {
        this.productsDbList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ProductsDb) ->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (ProductsDb) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate((card_items_manage_products), parent, false))



    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val products = productsDbList[position]
        holder.bindView(products)
        holder.itemView.setOnClickListener{onClickItem?.invoke(products)}
        holder.buttonDelete.setOnClickListener{onClickDeleteItem?.invoke(products)}
    }

    override fun getItemCount(): Int {
        return productsDbList.size
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view ){
        private var itemCode = view.findViewById<TextView>(R.id.tvItemCode)
        private var itemDescription = view.findViewById<TextView>(R.id.tvItemDescription)
        private var itemCount = view.findViewById<TextView>(R.id.tbItemCount)
         var buttonDelete = view.findViewById<Button>(R.id.buttonDelete)!!

        fun bindView(productsDb: ProductsDb){
            itemCode.text = productsDb.itemCode
            itemDescription.text = productsDb.itemDescription
            itemCount.text = productsDb.itemCount
        }
    }

}