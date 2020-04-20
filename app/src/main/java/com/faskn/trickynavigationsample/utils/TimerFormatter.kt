package com.faskn.trickynavigationsample.utils

/**
 * Created by Furkan on 18.04.2020
 */

class TimerFormatter(private val timeLeft: Long) {

    fun getRemainingTimeText(): String {
        val minutes = (timeLeft / 60000)
        val seconds = (timeLeft % 60000 / 1000)
        var minutesText = ""
        var secondsText = ""

        minutesText = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        secondsText = if (seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString()
        }

        return "$minutesText:${secondsText.take(2)}"
    }
}