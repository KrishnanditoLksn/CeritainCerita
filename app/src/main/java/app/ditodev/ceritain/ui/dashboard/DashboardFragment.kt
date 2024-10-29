package app.ditodev.ceritain.ui.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.ditodev.ceritain.R
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.databinding.FragmentDashboardBinding
import app.ditodev.ceritain.ui.viewmodels.UploadPictureViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.CameraUtil
import app.ditodev.ceritain.utils.CameraUtil.reduceFileImage
import app.ditodev.ceritain.utils.Utils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private var currentImage: Uri? = null
    private val uploadVm by viewModels<UploadPictureViewModel> {
        StoryViewModelFactory.getInstance(requireActivity())
    }
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPicture()
        Utils.showLoading(false, binding.progressBarLoading)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        binding.buttonAdd.setOnClickListener {
            currentImage?.let { uri ->
                val imageFile = CameraUtil.uriToFile(uri, requireContext()).reduceFileImage()
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData(
                    "photo", imageFile.name, requestImageFile
                )

                val description = binding.edAddDescription.text.toString()
                val requestBody = description.toRequestBody("text/plain".toMediaType())

                uploadVm.uploadStory(multiPartBody, requestBody)
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Loading -> {
                                Utils.showLoading(true, binding.progressBarLoading)
                            }

                            is Result.Success -> {
                                Utils.showLoading(false, binding.progressBarLoading)
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Warning")
                                    .setMessage(result.data.message)
                                    .setPositiveButton("Gambar sukses diupload") { _, _ ->
                                        findNavController().navigate(R.id.navigation_home)
                                    }
                                    .show()
                            }

                            is Result.Error -> {
                                Utils.showLoading(false, binding.progressBarLoading)
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Error")
                                    .setMessage(result.error)
                                    .setPositiveButton("Gambar gagal diupload") { _, _ ->
                                        findNavController().navigate(R.id.navigation_dashboard)
                                    }
                                    .show()
                            }
                        }
                    }
            }
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

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                showImage()
            } else {
                currentImage = null
                Toast.makeText(activity, "No Picture Selected", Toast.LENGTH_SHORT).show()
            }
        }
}