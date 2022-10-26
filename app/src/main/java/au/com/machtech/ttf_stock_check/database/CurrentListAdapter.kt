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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.R

class CurrentListAdapter : RecyclerView.Adapter<CurrentListAdapter.CurrentListViewHolder>() {
    private var productsDbList : ArrayList<ProductsDb> = ArrayList()
    private var onClickItem: ((ProductsDb) -> Unit)? = null


        @SuppressLint("NotifyDataSetChanged")
        fun addItems(items: ArrayList<ProductsDb>) {
            this.productsDbList = items
            notifyDataSetChanged()
        }

       /* fun setOnClickItem(callback: (ProductsDb) ->Unit){
            this.onClickItem = callback
        }*/

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrentListViewHolder(
            LayoutInflater.from(parent.context).inflate((R.layout.card_items_products), parent, false))



        override fun onBindViewHolder(holder: CurrentListViewHolder, position: Int) {
            val products = productsDbList[position]
            holder.bindView(products)
            holder.itemView.setOnClickListener{onClickItem?.invoke(products)}

        }

        override fun getItemCount(): Int {
            return productsDbList.size
        }

        class CurrentListViewHolder(view: View) : RecyclerView.ViewHolder(view ){
            private var tvItemCodeCurrent = view.findViewById<TextView>(R.id.tvItemCodeCurrent)
            private var itemDescriptionCurrent = view.findViewById<TextView>(R.id.tvItemDescriptionCurrent)
            private var itemCountCurrent = view.findViewById<TextView>(R.id.tvItemCountCurrent)


            fun bindView(productsDb: ProductsDb){
                tvItemCodeCurrent.text = productsDb.itemCode
                itemDescriptionCurrent.text = productsDb.itemDescription
                itemCountCurrent.text = productsDb.itemCount
            }
        }

}