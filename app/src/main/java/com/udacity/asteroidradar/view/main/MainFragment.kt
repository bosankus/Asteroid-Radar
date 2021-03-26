package com.udacity.asteroidradar.view.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkInfo
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.util.Constants.PAST
import com.udacity.asteroidradar.util.Constants.TODAY
import com.udacity.asteroidradar.util.Constants.TODAY_ONWARD
import com.udacity.asteroidradar.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

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

        viewModel.workerState.observe(viewLifecycleOwner, { workInfoList ->
            workInfoList.forEach {
                if (it.state != WorkInfo.State.RUNNING) viewModel.stopLoading()
            }
        })
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
