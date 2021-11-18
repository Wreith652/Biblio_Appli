package com.lucien.biblio_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.lucien.biblio_app.databinding.ActivityMainScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainScreenActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMainScreenBinding

    // FireBaseAuth
    private lateinit var auth: FirebaseAuth

    // BandeauSuperieur
    private lateinit var actionBar: androidx.appcompat.app.ActionBar

    // FireStore Init
    private val db: FirebaseFirestore = Firebase.firestore

    // RecyclerView
    private lateinit var livreRecyclerview : RecyclerView
    private lateinit var livresArrayList: ArrayList<Livres>

    // Adapter
    private lateinit var  adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Config BandeaudSuperieur
        actionBar = supportActionBar!!
        actionBar.title = "BiblioApp"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)



        // Init Firebase
        auth = FirebaseAuth.getInstance()
        checkUser()

        livreRecyclerview = findViewById(R.id.BookRV)
        livreRecyclerview.layoutManager = LinearLayoutManager(this)
        livreRecyclerview.setHasFixedSize(true)

        livresArrayList = arrayListOf()

        adapter = Adapter(this,livresArrayList)

        livreRecyclerview.adapter = adapter


    }

    // Vérifie si connecté ou pas
    private fun  checkUser(){
        // Si NON connecté go to LoginActivity
        // Recuperer current user
        val firebaseUser = auth.currentUser

        if (firebaseUser != null) {
            // Utilisateur deja connecteé

            PrintFireStorageDB()

        }
        else{
            // Utilisateur NON connecté
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    // Affiche Db Firestore
    private fun PrintFireStorageDB() {
        // Affiche le contenue de la base de donnée FireStore
        db.collection("Livres").orderBy("ID", Query.Direction.ASCENDING )
            .get()
            .addOnSuccessListener {

                for (document : DocumentChange in it?.documentChanges!!) {
                    if (document.type == DocumentChange.Type.ADDED){
                        livresArrayList.add(document.document.toObject(Livres::class.java))
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("Erreur getting document", it.message.toString())
            }

    }

    // Deconnexion
    private fun signOut() {
        // [START auth_sign_out]
        auth.signOut()
        checkUser()
        // [END auth_sign_out]

    }

    override fun onSupportNavigateUp(): Boolean {
        signOut() // Retour sur Login  lorsque click sur bouton retour
        return super.onSupportNavigateUp()
    }
}