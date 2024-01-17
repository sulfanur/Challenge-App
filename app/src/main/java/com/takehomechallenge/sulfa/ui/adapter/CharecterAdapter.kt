package com.takehomechallenge.sulfa.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.sulfa.api.response.ResultsItem
import com.takehomechallenge.sulfa.databinding.ItemCharacterBinding
import com.takehomechallenge.sulfa.ui.DetailPage

class CharacterAdapter(private val listUser : ArrayList<ResultsItem>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCharacterBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listUser[position]
        Glide
            .with(holder.itemView.context)
            .load(data.image)
            .circleCrop()
            .into(holder.binding.imgProfile)
        holder.binding.tvName.text = data.name
        holder.itemView.setOnClickListener{
            onItemCallback.onitemclick(listUser[holder.adapterPosition])
        }

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailPage::class.java)
            i.putExtra("id", data.id)
            i.putExtra("name", data.name)
            i.putExtra("species", data.species)
            i.putExtra("gender", data.gender)
            i.putExtra("origin", data.origin?.name)
            i.putExtra("location", data.location?.name)
            i.putExtra("image", data.image)

            holder.itemView.context.startActivity(i)
        }
    }
    private lateinit var onItemCallback: OnItemCallback
    fun setOnItemCallback(onItemCallback: OnItemCallback){
        this.onItemCallback = onItemCallback
    }
    interface OnItemCallback{
        fun onitemclick(data:ResultsItem)
    }


}