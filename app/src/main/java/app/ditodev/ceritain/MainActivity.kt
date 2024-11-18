package app.ditodev.ceritain

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.ditodev.ceritain.databinding.ActivityMainBinding
import app.ditodev.ceritain.ui.auth.login.LoginActivity
import app.ditodev.ceritain.ui.splash.WelcomeScreenActivity
import app.ditodev.ceritain.ui.viewmodels.MainViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainVm by viewModels<MainViewModel> {
        StoryViewModelFactory.getInstance(this)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val navView: BottomNavigationView = binding.navView

        mainVm.getSession().observe(this) {
            if (it.token.isEmpty() && auth.currentUser == null) {
                startActivity(Intent(this, WelcomeScreenActivity::class.java))
                finish()
            } else {
                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_maps
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar.apply {
            menuInflater.inflate(R.menu.option_menu, menu)
            title = getString(R.string.app_name)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            mainVm.logout()

            lifecycleScope.launch {
                val credentialManager = CredentialManager.create(this@MainActivity)
                auth.signOut()
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}