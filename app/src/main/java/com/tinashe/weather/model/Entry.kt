package com.tinashe.weather.model

/**
 * Created by tinashe on 2018/03/20.
 */
data class Entry(val time: Long,
                 val summary: String,
                 val icon: String,
                 val precipType: String,
                 val temperature: Double,
                 var location: String = "",
                 val temperatureHigh: Double,
                 val temperatureMin: Double)