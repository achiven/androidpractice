package com.example.mvpbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvpbase.R
import com.example.mvpbase.model.User
import de.hdodenhof.circleimageview.CircleImageView


class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onClick(user: User)
    }

    private var items = ArrayList<User>()
    private lateinit var listener: OnItemClickListener

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var profile: CircleImageView = view.findViewById(R.id.iv_item_profile)
        var name: TextView = view.findViewById(R.id.tv_item_name)
        var phone: TextView = view.findViewById(R.id.tv_item_phone)
        var mail: TextView = view.findViewById(R.id.tv_item_mail)
        var like_cnt: TextView = view.findViewById(R.id.tv_item_like_cnt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)

        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateView(user:User){
        val pos = getPosition(user)

        if(pos == RecyclerView.NO_POSITION) return

        items[pos] = user
        notifyItemChanged(pos)
    }



    fun getPosition(user:User) : Int{

        for(idx in 0 .. items.size){
            if(items[idx].getFullName().equals(user.getFullName())){
                return idx
            }
        }

        return RecyclerView.NO_POSITION
    }

    fun setItems(items : ArrayList<User>){
        this.items = items
        notifyDataSetChanged()
    }

    fun setClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = items[holder.adapterPosition]

        Glide.with(holder.itemView.context)
            .load(user.picture.large)
            .into(holder.profile)

        holder.name.text = user.getFullName()
        holder.phone.text = user.phone
        holder.mail.text = user.email
        holder.like_cnt.text = user.getLikeCnt()

        holder.itemView.setOnClickListener(){
            listener.onClick(user)
        }
    }

}