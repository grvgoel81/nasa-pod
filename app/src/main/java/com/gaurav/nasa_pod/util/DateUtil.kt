package com.gaurav.nasa_pod.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        /**
         * This method return today's date in "yyyy-MM-dd" format
         */
        fun todayDate(): String {
            val calendar = Calendar.getInstance(Locale.US)
            return simpleDateFormat.format(calendar.time)
        }

        /**
         * This method get a date in "yyyy-MM-dd" format and a days count and
         * return the date of the day with days count before the date
         */
        fun getDateBeforeDate(date: String, daysCountBeforeDate: Int): String {
            val formattedDate: Date = simpleDateFormat.parse(date)!!
            val calendar = Calendar.getInstance(Locale.US)
            calendar.time = formattedDate
            calendar.add(Calendar.DAY_OF_YEAR, -daysCountBeforeDate)

            return simpleDateFormat.format(Date(calendar.timeInMillis))
        }

        /**
         * This method get a date in "yyyy-MM-dd" format and a days count and
         * return the date of the day with days count after the date
         */
        fun getDateAfterDate(date: String, daysCountAfterDate: Int): String {
            val formattedDate: Date = simpleDateFormat.parse(date)!!
            val calendar = Calendar.getInstance(Locale.US)
            calendar.time = formattedDate
            calendar.add(Calendar.DAY_OF_YEAR, daysCountAfterDate)

            return simpleDateFormat.format(Date(calendar.timeInMillis))
        }
    }
}