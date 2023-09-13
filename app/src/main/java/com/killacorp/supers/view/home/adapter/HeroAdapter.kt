package com.killacorp.supers.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.killacorp.supers.R
import com.killacorp.supers.domain.model.HeroModel
import com.squareup.picasso.Picasso
import java.util.*

class HeroAdapter() : RecyclerView.Adapter<HeroAdapter.HeroHolder>() {

    private var heroesList = ArrayList<HeroModel>()

    private lateinit var onHereoListener: OnHereoListener
    interface OnHereoListener {
        fun call(id : String)
    }
    fun onClick(onHereoListener: OnHereoListener){
        this.onHereoListener = onHereoListener
    }

    class HeroHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var heroeImage: ImageView = itemView.findViewById(R.id.heroImg)
        var heroeName: TextView = itemView.findViewById(R.id.hereoName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item_view,parent,false)
        return HeroHolder(view)
    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val hero = heroesList[position]
        holder.heroeName.text  = hero.name
        Picasso.get().load(hero.image?.url).error(R.drawable.ic_launcher_foreground).into(holder.heroeImage)
        holder.heroeName.setOnClickListener {
            val pos = holder.adapterPosition
            if(pos != RecyclerView.NO_POSITION){
                onHereoListener.call(hero.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return heroesList.size
    }

    fun addAll(newData : ArrayList<HeroModel>){
        heroesList = newData
    }


}