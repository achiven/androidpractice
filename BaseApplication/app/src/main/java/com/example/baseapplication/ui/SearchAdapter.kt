package com.example.baseapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapplication.R
import com.example.baseapplication.data.api.OrgResponse
import kotlinx.android.synthetic.main.layout_search_list_item.view.*

class SearchAdapter(
    private val clickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SearchViewHolder(parent: View) : RecyclerView.ViewHolder(parent)
    class LoadingViewHolder(parent: View) : RecyclerView.ViewHolder(parent)

    class Item(avatarUrl: String, name: String) : IFItem(avatarUrl, name)
    class LoadingItem : IFItem()

    open class IFItem() {
        var avatarUrl: String = ""
        var name: String = ""

        constructor(avatarUrl: String, name: String) : this() {
            this.avatarUrl = avatarUrl
            this.name = name
        }
    }

    var list = mutableListOf<IFItem>()

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    override fun getItemViewType(position: Int): Int {
        return if (isLoadingHolder(list[position]))
            VIEW_TYPE_LOADING
        else
            VIEW_TYPE_ITEM
    }

    private fun isLoadingHolder(item: IFItem): Boolean {
        return item is LoadingItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_search_list_item, parent, false)
            return SearchViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            populateItemRows(holder, position)
        } else {
            showLoadingItem()
        }
    }

    private fun populateItemRows(holder: SearchViewHolder, position: Int) {
        val clickListener = this.clickListener
        list[position].let { item ->
            with(holder.itemView) {
                Glide.with(this)
                    .load(item.avatarUrl)
                    .into(avatar_img)
                repo_name.text = item.name

                this.setOnClickListener {
                    clickListener?.onClick(position)
                }
            }
        }
    }

    private fun showLoadingItem() {
        //ProgressBar would be displayed
    }

    var isProgressBar = false

    fun addProgressBar() {
        if (!isProgressBar) {
            list.add(LoadingItem())

            notifyItemInserted(list.size - 1)
            isProgressBar = true
        }
    }

    fun removeProgressBar() {
        if (isProgressBar && list.isNotEmpty() && isLoadingHolder(list.last())) {
            list.removeAt(list.size - 1)
            notifyItemRemoved(list.size)

        }
        isProgressBar = false
    }

    fun setItems(responses: ArrayList<OrgResponse>) {
        list.clear()

        for (response in responses) {
            list.add(Item(response.owner.avatar_url, response.name))
        }
        notifyDataSetChanged()
    }
}