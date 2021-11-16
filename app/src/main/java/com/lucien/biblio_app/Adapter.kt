package com.lucien.biblio_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class Adapter(private val Livreliste : ArrayList<Livre>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val livre : Livre = Livreliste[position]
        livre.Id_Image?.let { holder.ID_Image.setImageResource(it) }
        holder.Titre.text = livre.Titre
        holder.Auteur.text = livre.Auteur
    }

    override fun getItemCount(): Int {
        return Livreliste.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ID_Image : ShapeableImageView = itemView.findViewById(R.id.IVId_Image)
        val Titre : TextView = itemView.findViewById(R.id.TVTitre)
        val Auteur : TextView = itemView.findViewById(R.id.TVAuteur)

    }
}