package com.ys.rx.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.string_list_item.view.*
import java.util.ArrayList

class SimpleStringAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<SimpleStringAdapter.ViewHolder>() {


    var mStrings: ArrayList<String> = ArrayList()

    fun setStrings(list: ArrayList<String>)
    {
        mStrings.addAll(list)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.string_list_item, parent, false);

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mStrings!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mStrings[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String, listener: (String) -> Unit) = with(itemView) {

            tvTitle.text = item
            setOnClickListener { listener(item) }
        }
    }
}