package com.example.petfriends.ui.auth

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.petfriends.R
import com.example.petfriends.databinding.ActivityLoginBinding
import com.example.petfriends.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        supportActionBar?.hide()

        mAuth = Firebase.auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setupLogin()
        setupGoogleLogin()

        binding.tvRegisterLogin.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun setupGoogleLogin() {
        binding.apply {
            ivGoogleLogin.setOnClickListener {
                val signIntent = googleSignInClient.signInIntent
                resultLauncher.launch(signIntent)
            }
        }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setupLogin() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edEmailLogin.text.toString()
                val password = edPasswordLogin.text.toString()
                when{
                    email.isEmpty() -> {
                        edEmailLogin.error = getString(R.string.enter_email)
                        edEmailLogin.requestFocus()
                    }
                    password.isEmpty() -> {
                        edPasswordLogin.error = getString(R.string.enter_password)
                        edPasswordLogin.requestFocus()
                    }
                    password.length < 6 -> {
                        edPasswordLogin.error = getString(R.string.length_password)
                        edPasswordLogin.requestFocus()
                    }
                    !isEmailValid(email) -> {
                        edEmailLogin.error = getString(R.string.email_invalid)
                        edEmailLogin.requestFocus()
                    }
                    else -> {
                        showLoading(true)
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@LoginActivity){
                            if (it.isSuccessful){
                                showLoading(false)
                                Log.d(ContentValues.TAG, getString(R.string.success_sign_in))
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle(getString(R.string.success))
                                    setMessage(getString(R.string.success_sign_in))
                                    setPositiveButton(getString(R.string.cont)){ _, _ ->
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                                Toast.makeText(this@LoginActivity, getString(R.string.success_sign_in), Toast.LENGTH_SHORT).show()
                            }else{
                                showLoading(false)
                                Log.e(ContentValues.TAG, getString(R.string.failed_sign_in), it.exception)
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle(getString(R.string.failed))
                                    setMessage(getString(R.string.wrong_password))
                                    setPositiveButton(getString(R.string.cont)){ _, _ ->
                                        show().dismiss()
                                    }
                                    create()
                                    show()
                                }
                                Toast.makeText(this@LoginActivity, getString(R.string.wrong_password), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}