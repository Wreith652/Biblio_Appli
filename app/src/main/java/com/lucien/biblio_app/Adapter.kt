package com.lucien.biblio_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Adapter(private val context: Context, private val livreliste : ArrayList<Livres>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val livres : Livres = livreliste[position]


        if (livres.Lu == true)
            holder.Lu.setText("Lu")
        else
            holder.Lu.setText("Pas encore lu")

        holder.Titre.text = livres.Nom
        holder.Auteur.text = livres.Auteur



        Glide.with(context)
            .load(livres.Image)
            .into(holder.Image)



    }

    override fun getItemCount(): Int {
        return livreliste.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val Image : ImageView = itemView.findViewById(R.id.IVImage)
        val Titre : TextView = itemView.findViewById(R.id.TVtitre)
        val Auteur : TextView = itemView.findViewById(R.id.TVauteur)
        val Lu : TextView = itemView.findViewById(R.id.TVlu)

    }
}