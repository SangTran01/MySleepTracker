package ca.stran1121.mysleeptracker.sleepquality

import android.os.Bundle
import android.util.Log
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
import ca.stran1121.mysleeptracker.databinding.FragmentSleepQualityBinding

class SleepQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sleep_quality, container, false
        )

        val application = requireNotNull(activity).application
        val sleepNightDao = SleepNightDatabase.getInstance(application).sleepNightDao

        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightId, sleepNightDao)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SleepQualityViewModel::class.java)

        binding.sleepQualityViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.sleepQuality.observe(viewLifecycleOwner, Observer {
            //navigate back
            Log.d("SleepQuality", "${it}")
            this.findNavController()
                .navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
        })

        return binding.root
    }
}