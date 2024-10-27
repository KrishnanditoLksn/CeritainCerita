package app.ditodev.ceritain

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.ditodev.ceritain.databinding.ActivityMainBinding
import app.ditodev.ceritain.ui.splash.WelcomeScreenActivity
import app.ditodev.ceritain.ui.viewmodels.MainViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val mainVm by viewModels<MainViewModel> {
        StoryViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        mainVm.getSession().observe(this) {
            if (it.token.isEmpty()) {
                startActivity(Intent(this, WelcomeScreenActivity::class.java))
                finish()
            }
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        }
        return super.onOptionsItemSelected(item)
    }
}