package com.hashconcepts.whatsappstatussaver

import android.Manifest.permission
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hashconcepts.whatsappstatussaver.databinding.FragmentHomeBinding
import com.permissionx.guolindev.PermissionX


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val hasStoragePermission: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.downloadLayout.setOnClickListener {
            if (!hasStoragePermission) {
                requestStoragePermission()
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_homeFragment_to_statusSaverFragment)
        }

        binding.uploadLayout.setOnClickListener {
            if (!hasStoragePermission) {
                requestStoragePermission()
                return@setOnClickListener
            }
            // @Todo -> Open Video file
        }

        binding.settingsLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStoragePermission()
    }

    private fun requestStoragePermission() {
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        /**
         * From Android SDK >= 29 scoped storage is used so there is no need
         * WRITE_EXTERNAL_STORAGE Permission
         *
         * Asking for it on those versions would return permission not granted
         */
        val permissions: String = when {
            minSdk29 -> {
                permission.READ_EXTERNAL_STORAGE
            }
            else -> {
                listOf(
                    permission.READ_EXTERNAL_STORAGE,
                    permission.WRITE_EXTERNAL_STORAGE
                ).toString()
            }
        }

        PermissionX.init(this)
            .permissions(permissions)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    getString(R.string.storage_permission_message),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.grant_permission_manually),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .request { allGranted, _, _ ->
                if (!allGranted) {
                    Snackbar.make(binding.root, getString(R.string.storage_permission_denied), Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}