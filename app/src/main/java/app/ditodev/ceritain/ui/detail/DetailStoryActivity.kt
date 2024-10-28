package app.ditodev.ceritain.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.ditodev.ceritain.R
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.databinding.ActivityDetailStoryBinding
import app.ditodev.ceritain.ui.viewmodels.DetailStoryViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.Utils
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private val detailViewModel by viewModels<DetailStoryViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchDetailStories()
    }

    private fun fetchDetailStories() {
        val itemId = intent.getStringExtra(Utils.EXTRA_ID)
        if (itemId != null) {
            detailViewModel.getStories(itemId).observe(this) {
                when (it) {
                    is Result.Loading -> {
                        Utils.showLoading(true, binding.progressBarDetail)
                    }

                    is Result.Success -> {
                        Utils.showLoading(false, binding.progressBarDetail)
                        binding.tvDetailName.text = it.data.name
                        binding.tvDetailDescription.text = it.data.description
                        Glide.with(this)
                            .load(it.data.photoUrl)
                            .into(binding.ivDetailPhoto)
                    }

                    is Result.Error -> {
                        Utils.showLoading(false, binding.progressBarDetail)
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage(it.error)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }
}