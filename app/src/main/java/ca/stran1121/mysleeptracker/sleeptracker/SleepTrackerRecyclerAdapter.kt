package ca.stran1121.mysleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.stran1121.mysleeptracker.R
import ca.stran1121.mysleeptracker.database.entity.SleepNight
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString


//change from using default Recycler.Adapter to use Reccycler.Listadapter
class SleepTrackerRecyclerAdapter :
    ListAdapter<SleepNight, SleepTrackerRecyclerAdapter.ViewHolder>(SleepNightDiffCallback()) {

    class ViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        val textViewQuality: TextView = view.findViewById(R.id.quality_string)
        val textViewSleepLength: TextView = view.findViewById(R.id.sleep_length)
        val imageViewQuality: ImageView = view.findViewById(R.id.quality_image)

        fun bind(
            item: SleepNight
        ) {
            val res = itemView.context.resources

            textViewSleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            textViewQuality.text = convertNumericQualityToString(item.sleepQuality, res)

            imageViewQuality.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(viewGroup: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item_sleep_tracker, viewGroup, false)
                return ViewHolder(view)
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