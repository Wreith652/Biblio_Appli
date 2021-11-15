package com.lucien.biblio_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.lucien.biblio_app.databinding.ActivityMainScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainScreenActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMainScreenBinding

    // FireBaseAuth
    private lateinit var auth: FirebaseAuth

    // BandeauSuperieur
    private lateinit var actionBar: androidx.appcompat.app.ActionBar

    private val db: FirebaseFirestore = Firebase.firestore

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

    private fun PrintFireStorageDB() {
        // Affiche le contenue de la base de donnée FireStore
        db.collection("Livres")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()

                if(it.isSuccessful) {

                    for (document in it.result!!) {
                        result.append(document.data.getValue("ID_Image")).append(" ")
                            .append(document.data.getValue("Nom")).append("\n\n")
                    }
                    binding.ResultTV.setText(result)
                }
            }
            /*.addOnFailureListener {
                binding.ResultTV.setText("Error getting documents.")
            }*/

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