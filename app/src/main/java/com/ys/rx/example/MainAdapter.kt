package com.ys.rx.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import kotlinx.android.synthetic.main.string_list_item.view.*

class MainAdapter(val example:ArrayList<ExampleActivityModel>,private val listener: (ExampleActivityModel) -> Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.string_list_item, parent, false);

        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return example.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(example.get(position), listener)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item:  ExampleActivityModel, listener: (ExampleActivityModel) -> Unit) = with(itemView) {
            tvTitle.text = item.mExampleName
            setOnClickListener { listener(item) }
        }
    }
}