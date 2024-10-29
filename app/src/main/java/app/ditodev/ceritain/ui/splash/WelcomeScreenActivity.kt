package app.ditodev.ceritain.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.ditodev.ceritain.R
import app.ditodev.ceritain.databinding.ActivitySplashScreenBinding
import app.ditodev.ceritain.ui.auth.login.LoginActivity
import app.ditodev.ceritain.ui.auth.register.RegisterActivity

class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar!!.hide()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.button1.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.button1, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.button2, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(login, signup, title)
            start()
        }
    }
}