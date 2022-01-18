package ca.stran1121.mysleeptracker.sleepDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import ca.stran1121.mysleeptracker.R
import ca.stran1121.mysleeptracker.database.SleepNightDatabase
import ca.stran1121.mysleeptracker.databinding.FragmentSleepDetailBinding
import ca.stran1121.mysleeptracker.sleepquality.SleepQualityViewModel

class SleepDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments = SleepDetailFragmentArgs.fromBundle(requireArguments())

        val binding: FragmentSleepDetailBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_sleep_detail, container, false)

        val application = requireNotNull(context).applicationContext

        val sleepNightDao = SleepNightDatabase.getInstance(application).sleepNightDao

        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightId, sleepNightDao)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SleepDetailViewModel::class.java)

        binding.sleepDetailViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(SleepDetailFragmentDirections
                        .actionSleepDetailFragmentToSleepTrackerFragment())

                viewModel.onAfterClose()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}