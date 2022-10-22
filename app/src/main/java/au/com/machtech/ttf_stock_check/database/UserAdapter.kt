package au.com.machtech.ttf_stock_check.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.machtech.ttf_stock_check.R

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var usersList : ArrayList<Users> = ArrayList()
    private var onClickItem: ((Users) -> Unit)? = null
    private var onClickDeleteItem: ((Users) -> Unit)? = null

    fun addUser(user: ArrayList<Users>) {
        this.usersList = user
        notifyDataSetChanged()
    }

    fun setOnClickUser(callback: (Users) ->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteUser(callback: (Users) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate((R.layout.card_items_user), parent, false))


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = usersList[position]
        holder.bindView(users)
        holder.itemView.setOnClickListener{onClickItem?.invoke(users)}
        holder.buttonDelete.setOnClickListener{onClickDeleteItem?.invoke(users)}
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view ){
        private var fullName = view.findViewById<TextView>(R.id.tvFullName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var password = view.findViewById<TextView>(R.id.tvPassword)
         var buttonDelete = view.findViewById<Button>(R.id.buttonDelete)!!

        fun bindView(users: Users){
            fullName.text = users.fullName
            email.text = users.email
            password.text = users.password
        }
    }

}