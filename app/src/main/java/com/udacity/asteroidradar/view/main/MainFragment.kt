package com.udacity.asteroidradar.view.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkInfo
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.util.Constants.FIRST_LAUNCH
import com.udacity.asteroidradar.util.Constants.PAST
import com.udacity.asteroidradar.util.Constants.TODAY
import com.udacity.asteroidradar.util.Constants.TODAY_ONWARD
import com.udacity.asteroidradar.util.isNetworkAvailable
import com.udacity.asteroidradar.util.logMessage
import com.udacity.asteroidradar.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var preferences: SharedPreferences
    private var isFirstLaunch: Boolean = true

    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        setHasOptionsMenu(true)
        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            mainviewModel = viewModel
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH, true)
        addObservers()
    }

    override fun onStart() {
        super.onStart()
        if (isFirstLaunch) {
            if (requireContext().isNetworkAvailable()) {
                viewModel.startWorker()
                preferences.edit().putBoolean(FIRST_LAUNCH, false).apply()
            } else showNoConnectivityDialog()
        }
    }

    private fun addObservers() {
        viewModel.workerState.observe(viewLifecycleOwner, { workInfoList ->
            workInfoList.forEach {
                if (it.state == WorkInfo.State.RUNNING) viewModel.startLoading()
                if (it.state != WorkInfo.State.RUNNING) viewModel.stopLoading()
                logMessage("${it.state}")
            }
        })
    }

    private fun showNoConnectivityDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.no_connectivity_message))
            .setPositiveButton(android.R.string.ok) { _, _ -> goToDeviceSettings() }
            .setNegativeButton(android.R.string.cancel) { _, _ -> requireActivity().finish() }
            .setCancelable(false)
        builder.create().show()
    }

    private fun goToDeviceSettings() {
        val dialogIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids -> viewModel.sortAsteroidList(TODAY_ONWARD)
            R.id.show_today_asteroids -> viewModel.sortAsteroidList(TODAY)
            R.id.show_past_asteroids -> viewModel.sortAsteroidList(PAST)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
