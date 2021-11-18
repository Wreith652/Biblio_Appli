package com.lucien.biblio_app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

    private lateinit var detailArrayList: ArrayList<Livres>

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

        detailArrayList = arrayListOf()
        checkUser()

        val posData : Int = intent.getIntExtra("POSDATA", 0).toInt()
        PrintDetailBook(PosData = posData +1)


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
    private fun PrintDetailBook(PosData : Int) {
        // Affiche le contenue de la base de donnée FireStore
        db.collection("Livres").orderBy("ID", Query.Direction.ASCENDING )
            .get()
            .addOnSuccessListener {
                for (document : DocumentChange in it?.documentChanges!!) {
                    if (document.type == DocumentChange.Type.ADDED){
                        detailArrayList.add(document.document.toObject(Livres::class.java))
                    }
                }
                BindDetail(PosData)
            }
            .addOnFailureListener {
                Log.e("Erreur getting document", it.message.toString())
            }
    }

    private fun BindDetail(posData: Int) {

        val detail : Livres = detailArrayList[posData]

        val auteur : String? = detail.Auteur

        val description : String? = detail.Description
        val image : String? = detail.Image
        val lu : Boolean? = detail.Lu
        val titre : String? = detail.Nom
        val parution : String? = detail.Parution

        binding.TVdetailTitre.setText(titre)
        Glide.with(this)
            .load(image)
            .into(binding.IVdetailImage)

        binding.TVdetailAuteur.setText(auteur)
        binding.TVdetailParution.setText(parution)
        binding.SVdetailDescription.setText(description)
        if (lu == true) {
            binding.TVdetailLu.setText("Lu")
            binding.TVdetailLu.setTextColor(Color.GREEN)
        }
        else {
            binding.TVdetailLu.setText("Pas encore lu")
            binding.TVdetailLu.setTextColor(Color.RED)
        }

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