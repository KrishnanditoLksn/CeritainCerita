package app.ditodev.ceritain.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.ditodev.ceritain.MainActivity
import app.ditodev.ceritain.R
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.databinding.ActivityLoginBinding
import app.ditodev.ceritain.ui.auth.register.RegisterActivity
import app.ditodev.ceritain.ui.viewmodels.LoginViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.Utils.showLoading

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
}