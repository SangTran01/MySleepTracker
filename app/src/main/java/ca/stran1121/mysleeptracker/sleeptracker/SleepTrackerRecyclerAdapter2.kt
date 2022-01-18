package ca.stran1121.mysleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.stran1121.mysleeptracker.R
import ca.stran1121.mysleeptracker.database.entity.SleepNight
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString

class SleepTrackerRecyclerAdapter2(val data: List<SleepNight>):
    RecyclerView.Adapter<SleepTrackerRecyclerAdapter2.ViewHolder>() {

    class ViewHolder private constructor(private val view: View): RecyclerView.ViewHolder(view) {
        val textViewSleepQuality: TextView = view.findViewById(R.id.quality_string)
        val textViewSleepLength: TextView = view.findViewById(R.id.sleep_length)
        val imageViewSleepQuality: ImageView = view.findViewById(R.id.quality_image)

        fun bind(item: SleepNight) {
            val resource = view.context.resources
            textViewSleepQuality.text = convertNumericQualityToString(item.sleepQuality, resource)
            textViewSleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, resource)
            imageViewSleepQuality.setImageResource(
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
            fun getInstance(viewGroup: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(viewGroup.context).
                inflate(R.layout.list_item_sleep_tracker, viewGroup, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.getInstance(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val sleepNight = data[position]
        viewHolder.bind(sleepNight)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

class SleepNightClickListener2(val clickListener: (sleepNightId: Long) -> Unit) {
    fun onClick(sleepNight: SleepNight) {
        clickListener(sleepNight.nightId)
    }
}