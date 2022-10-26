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

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var usersDbList : ArrayList<UsersDb> = ArrayList()
    private var onClickItem: ((UsersDb) -> Unit)? = null
    private var onClickDeleteItem: ((UsersDb) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addUser(user: ArrayList<UsersDb>) {
        this.usersDbList = user
        notifyDataSetChanged()
    }

    fun setOnClickUser(callback: (UsersDb) ->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteUser(callback: (UsersDb) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate((R.layout.card_items_user), parent, false))


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = usersDbList[position]
        holder.bindView(users)
        holder.itemView.setOnClickListener{onClickItem?.invoke(users)}
        holder.buttonDelete.setOnClickListener{onClickDeleteItem?.invoke(users)}
    }

    override fun getItemCount(): Int {
        return usersDbList.size
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view ){
        private var fullName = view.findViewById<TextView>(R.id.tvFullName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var password = view.findViewById<TextView>(R.id.tvPassword)
         var buttonDelete = view.findViewById<Button>(R.id.buttonDelete)!!

        fun bindView(usersDb: UsersDb){
            fullName.text = usersDb.fullName
            email.text = usersDb.email
            password.text = usersDb.password
        }
    }

}