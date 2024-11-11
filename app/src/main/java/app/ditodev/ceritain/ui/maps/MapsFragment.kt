package app.ditodev.ceritain.ui.maps

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.ditodev.ceritain.R
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.databinding.FragmentMapsBinding
import app.ditodev.ceritain.ui.viewmodels.MapsViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private val mapsVm by viewModels<MapsViewModel> {
        StoryViewModelFactory.getInstance(requireActivity())
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mapsVm.getStoriesWithLocation().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Utils.showLoading(true, binding.progressBar)
                }

                is Result.Success -> {
                    result.data.forEach { data ->
                        val latLng = LatLng(data.lat!!, data.lon!!)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(data.name)
                                .snippet(data.description)
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    }
                    Utils.showLoading(false, binding.progressBar)
                }

                is Result.Error -> {
                    Utils.showLoading(false, binding.progressBar)
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        setMapStyle(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        Utils.showLoading(true, binding.progressBar)
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}