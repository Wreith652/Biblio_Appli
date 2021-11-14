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
import com.lucien.biblio_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityLoginBinding

    // FireBaseAuth
    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var password = ""

    // BandeauSuperieur
    private lateinit var actionBar: androidx.appcompat.app.ActionBar

    // ProgressDialog
    private lateinit var  progressDialog:ProgressDialog


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Config BandeaudSuperieur
        actionBar = supportActionBar!!
        actionBar.title = "Connexion"

        // Config Progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Veuillez patienter")
        progressDialog.setMessage("Connexion en cours ...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Init Firebase
        auth = FirebaseAuth.getInstance()
        checkUser()

        // Gerer click Inscription
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java ))
        }

        // Gerer click Connexion
        binding.LoginBtn.setOnClickListener{
            // Avant Connexion, Check Entries
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
        else {
            // Données valides check
            fireBaseLogin()
        }
    }

    // Connexion FireBase
    private fun fireBaseLogin() {
        // Affichage Progress
        progressDialog.show()
        // [START log_in_with_email]
        /*
        *  Link github code from firebase docs
        * https://github.com/firebase/snippets-android/blob/8184cba2c40842a180f91dcfb4a216e721cc6ae6/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin/EmailPasswordActivity.kt#L29-L36
        * */
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Sign in success, update UI with the signed-in user's information
                progressDialog.dismiss()
                val user = auth.currentUser
                val email = user!!.email
                Toast.makeText(
                    this, "Connexion réussie en tant que $email.",Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            .addOnFailureListener { e->
                // If sign in fails, display a message to the user.
                progressDialog.dismiss()
                Toast.makeText(this, "Échec de connexion : ${e.message}.",Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        // [END log_in_with_email]
    }

    // Affichage MainScreenActivity
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Open MainScreenActivity
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }
    }

    // Vérifie si connecté ou pas
    private fun  checkUser(){
        // Si Utilisateur déja connecté go to MainScreenActivity
        // Recuperer current user
        val firebaseUser = auth.currentUser

        if (firebaseUser != null) {
            // Utilisateur deja connecter
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }

    }

}