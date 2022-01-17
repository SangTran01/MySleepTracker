package ca.stran1121.mysleeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ca.stran1121.mysleeptracker.R
import ca.stran1121.mysleeptracker.database.SleepNightDatabase
import ca.stran1121.mysleeptracker.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSleepTrackerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application
        val sleepNightDao = SleepNightDatabase.getInstance(application).sleepNightDao

        val factory = SleepTrackViewModelFactory(sleepNightDao, application)

        val viewModel = ViewModelProvider(this, factory)
            .get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = SleepTrackerRecyclerAdapter()
        binding.sleepList.adapter = adapter

        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(
                        SleepTrackerFragmentDirections
                            .actionSleepTrackerFragmentToSleepQualityFragment(it.nightId)
                    )

                viewModel.doneNavigating() //call this to set it null
            }
        })

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Cleared",
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.doneShowSnackBar()
            }
        })
        return binding.root
    }
}