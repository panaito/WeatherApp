package com.tinashe.weather.ui.home.detail

import android.content.res.Configuration
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.tinashe.weather.R
import com.tinashe.weather.model.DateFormat
import com.tinashe.weather.model.Entry
import com.tinashe.weather.utils.DateUtil
import com.tinashe.weather.utils.WeatherUtil
import com.tinashe.weather.utils.inflateView
import com.tinashe.weather.utils.tint
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_hour_item_detail.*
import java.util.*
import java.util.concurrent.TimeUnit

class HoursAdapter : RecyclerView.Adapter<HoursAdapter.HourHolder>() {

    var entries = mutableListOf<Entry>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder = HourHolder.inflate(parent)

    override fun getItemCount(): Int = entries.size

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(entries[position])
    }

    class HourHolder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        companion object {
            fun inflate(parent: ViewGroup):
                    HourHolder = HourHolder(inflateView(R.layout.weather_hour_item_detail, parent, false))
        }

        fun bind(entry: Entry) {
            val context = itemView.context

            val date = Date(TimeUnit.MILLISECONDS.convert(entry.time, TimeUnit.SECONDS))
            val now = Calendar.getInstance()
            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 1)
            hourTime.text = if (date.before(now.time)) {
                context.getString(R.string.now)
            } else {
                DateUtil.getFormattedDate(date, DateFormat.TIME_SHORT)
            }
            val icon = WeatherUtil.getIcon(context, entry.icon)
            val currentNightMode = itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                icon?.tint(Color.WHITE)
            }
            hourIcon.setImageDrawable(icon)
            hourTemperature.text = context.getString(R.string.degrees, entry.temperature.toInt())
        }
    }
}