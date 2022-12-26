package com.hashconcepts.whatsappstatussaver.statussaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.hashconcepts.whatsappstatussaver.R
import com.hashconcepts.whatsappstatussaver.databinding.FragmentStatusSaverBinding
import com.hashconcepts.whatsappstatussaver.statussaver.adapter.StatusSaverPagerAdapter


class StatusSaverFragment : Fragment() {
    private var _binding: FragmentStatusSaverBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStatusSaverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        val statusSaverAdapter = StatusSaverPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.viewpager.adapter = statusSaverAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = getString(R.string.images)
                }
                1 -> {
                    tab.text = getString(R.string.videos)
                }
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}