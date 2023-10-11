package com.killacorp.supers.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.killacorp.supers.R
import com.killacorp.supers.domain.model.HeroModel
import java.util.*


class HeroAdapter() : RecyclerView.Adapter<HeroAdapter.HeroHolder>() {

    private var heroesList = ArrayList<HeroModel>()
    private lateinit var onHeroListener: OnHeroListener


    interface OnHeroListener {
        fun call(id: String?)
    }

    fun onClick(onHeroListener: OnHeroListener){
        this.onHeroListener = onHeroListener
    }

    class HeroHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var heroImage: ImageView = itemView.findViewById(R.id.imgHero)
        var heroName: TextView = itemView.findViewById(R.id.tvHeroName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item_view,parent,false)
        return HeroHolder(view)
    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val hero = heroesList[position]
        holder.heroName.text  = hero.name
        Glide.with(holder.heroImage.context)
            .load(hero.image?.url)
            .error(R.drawable.ic_launcher_foreground)
            .centerInside()
            .into(holder.heroImage)
        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition
            if(pos != RecyclerView.NO_POSITION){
                onHeroListener.call(hero.id)
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