package ca.stran1121.mysleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.stran1121.mysleeptracker.R
import ca.stran1121.mysleeptracker.database.entity.SleepNight
import ca.stran1121.mysleeptracker.databinding.ListItemSleepTrackerBinding
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString


//change from using default Recycler.Adapter to use Reccycler.Listadapter
class SleepTrackerRecyclerAdapter :
    ListAdapter<SleepNight, SleepTrackerRecyclerAdapter.ViewHolder>(SleepNightDiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemSleepTrackerBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewQuality: TextView = binding.qualityString
        val textViewSleepLength: TextView = binding.sleepLength
        val imageViewQuality: ImageView = binding.qualityImage

        fun bind(item: SleepNight) {
            binding.sleepNight = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(viewGroup: ViewGroup): ViewHolder {
//                val view = LayoutInflater.from(viewGroup.context)
//                    .inflate(R.layout.list_item_sleep_tracker, viewGroup, false)
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ListItemSleepTrackerBinding.inflate(inflater, viewGroup, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position) //from ListAdapter getItem()
        viewHolder.bind(item)
    }

    //ListAdapter can figure out item Count
//    override fun getItemCount(): Int {
//        return data.size
//    }

}

//From Recycler ListAdapter
//more efficient way to detect changes than using NotifyDataSetChanged()
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}