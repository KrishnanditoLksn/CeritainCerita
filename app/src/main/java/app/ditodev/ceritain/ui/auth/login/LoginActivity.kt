package app.ditodev.ceritain.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.lifecycleScope
import app.ditodev.ceritain.BuildConfig
import app.ditodev.ceritain.MainActivity
import app.ditodev.ceritain.R
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.databinding.ActivityLoginBinding
import app.ditodev.ceritain.ui.auth.register.RegisterActivity
import app.ditodev.ceritain.ui.viewmodels.LoginViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.Utils.showLoading
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val loginVm by viewModels<LoginViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        auth = Firebase.auth
        binding.btnGoogleLogin.setOnClickListener {
            signIn()
        }
        setupAction()
        showLoading(false, binding.pbLoad1)
        setupLogin()
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.edLoginEmail.error = "Email is required"
                binding.edLoginPassword.error = "Password is required"
            }
            loginVm.handleLogin(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true, binding.pbLoad1)
                        }

                        is Result.Success -> {
                            showLoading(false, binding.pbLoad1)
                            AlertDialog.Builder(this).apply {
                                setTitle("Message")
                                setMessage("Anda berhasil login.Saatnya menikmati story app buatan kami !!")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is Result.Error -> {
                            showLoading(false, binding.pbLoad1)
                            AlertDialog.Builder(this).apply {
                                setTitle("Warning")
                                setMessage("Anda belum  berhasil login status :${result.error}")
                                setPositiveButton("Oke") { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun setupAction() {
        binding.btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun signIn() {
        val credentialManager = androidx.credentials.CredentialManager.create(this)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.WEB_CLIENT_ID)
            .build()

        val request = androidx.credentials.GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity
                )
                handleSignIn(result)
            } catch (e: androidx.credentials.exceptions.GetCredentialException) {
                Log.e("Error", e.message.toString())
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("ERROR", "Invalid google id token response ${e.message.toString()}")
                    }
                } else {
                    Log.e("LOGINACTIVITY", "Credential error")
                }
            }

            else -> {
                Log.e("LOGINACTIVITY", "Credential error")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d("LOGINACTIVITY", "Sign in with credential:failure", task.exception)
                    updateUI(null)
                    Toast
                        .makeText(this, "Silahkan Registrasi dahulu", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}