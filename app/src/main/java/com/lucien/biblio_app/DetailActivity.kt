package com.lucien.biblio_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucien.biblio_app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityDetailBinding

    // FireBaseAuth
    private lateinit var auth: FirebaseAuth

    // BandeauSuperieur
    private lateinit var actionBar: androidx.appcompat.app.ActionBar

    // FireStore Init
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Config BandeaudSuperieur
        actionBar = supportActionBar!!
        actionBar.title = "Détail"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Init Firebase
        auth = FirebaseAuth.getInstance()
        checkUser()


        val auteur : String? = intent.getStringExtra("AUTEUR").toString()

        val description : String? = intent.getStringExtra("DESCRIPTION").toString()
        val image : String? = intent.getStringExtra("IMAGE").toString()
        val lu : String? = intent.getStringExtra("LU").toString()
        val titre : String? = intent.getStringExtra("TITRE").toString()
        val parution : String? = intent.getStringExtra("PARUTION").toString()

        binding.TVdetailTitre.setText(titre)
        Glide.with(this)
            .load(image)
            .into(binding.IVdetailImage)

        binding.TVdetailAuteur.setText(auteur)
        binding.TVdetailParution.setText(parution)
        binding.TVdetailLu.setText(lu)

    }


    // Vérifie si connecté ou pas
    private fun  checkUser(){
        // Si NON connecté go to LoginActivity
        // Recuperer current user
        val firebaseUser = auth.currentUser

        if (firebaseUser != null) {
            // Utilisateur deja connecteé



        }
        else{
            // Utilisateur NON connecté
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    // Affiche detail du livre
    private fun PrintDetailBook() {

    }

    // Deconnexion
    private fun BackActivity() {
        // [START auth_sign_out]
        startActivity(Intent(this, MainScreenActivity::class.java))
        finish()
        // [END auth_sign_out]

    }

    override fun onSupportNavigateUp(): Boolean {
        BackActivity() // Retour sur MainScreen  lorsque click sur bouton retour
        return super.onSupportNavigateUp()
    }


}