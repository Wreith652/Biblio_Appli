package com.lucien.biblio_app

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lucien.biblio_app.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivitySignupBinding

    // FireBaseAuth
    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var password = ""

    // BandeauSuperieur
    private lateinit var actionBar: androidx.appcompat.app.ActionBar

    // ProgressDialog
    private lateinit var  progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Config BandeaudSuperieur, // enable bouton retour
        actionBar = supportActionBar!!
        actionBar.title = "Inscription"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Config Progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Veuillez patienter")
        progressDialog.setMessage("Création de votre compte ...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Init Firebase
        auth = FirebaseAuth.getInstance()

        // Gerer click Inscription
        binding.SignupBtn.setOnClickListener{
            // Avant Inscription, Check Entries
            checklog()
        }



    }

    // Assure conformite du mdp et Email
    private fun checklog() {
        // Recuperer Entry
        email = binding.EmailEt.text.toString().trim()
        password = binding.PassordEt.text.toString().trim()

        // Check si valide Entry
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // Email invalide
            binding.EmailEt.error = "Email Invalide"
        }
        else if (TextUtils.isEmpty(password)){
            // Mdp non renseigné
            binding.PassordEt.error = "Mot de passe Invalide"
        }
        else if (password.length < 6 ) {
            // Password pas assez long
            binding.PassordEt.error = "Le MDP doit contenir au moins 6 charactères"
        }
        else {
            // Données valides check
            fireBaseSignup()
        }
    }

    // Inscription à FireBase
    private fun fireBaseSignup() {
        // Affichage Progress
        progressDialog.show()

        // Créer Nouveau compte
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(this) {
                // Sign up success, update UI with the signed-in user's information
                progressDialog.dismiss()
                val user = auth.currentUser
                val email = user!!.email
                Toast.makeText(
                    this, "Inscription réussie en tant que $email.",Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            .addOnFailureListener { e ->
                // If sign up fails, display a message to the user.
                progressDialog.dismiss()
                Toast.makeText(this, "Échec de l' inscription : ${e.message}.",Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
    }

    // Affichage MainScreenActivity
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Open MainScreenActivity
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Retour sur précédente activity  lorsque click sur bouton retour
        return super.onSupportNavigateUp()
    }
}