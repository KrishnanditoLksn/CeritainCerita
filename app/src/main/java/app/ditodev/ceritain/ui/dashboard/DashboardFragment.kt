package app.ditodev.ceritain.ui.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import app.ditodev.ceritain.databinding.FragmentDashboardBinding
import app.ditodev.ceritain.utils.CameraUtil

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private var currentImage: Uri? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPicture()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPicture() {
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnCamera.setOnClickListener {
            startCamera()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            currentImage = uri
            showImage()
        } else {
            Toast.makeText(activity, "No Media Selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showImage() {
        currentImage?.let {
            binding.topImage.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImage = CameraUtil.getImageUri(requireContext())
        launcherCamera.launch(currentImage!!)
    }

    private val launcherCamera = registerForActivityResult(ActivityResultContracts.TakePicture())
    { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImage = null
            Toast.makeText(activity, "No Picture Selected", Toast.LENGTH_SHORT).show()
        }
    }
}