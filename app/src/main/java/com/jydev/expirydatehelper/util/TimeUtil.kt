package com.jydev.expirydatehelper.util

import com.jydev.expirydatehelper.data.AlarmCheck
import java.util.*
import java.util.concurrent.TimeUnit

fun alarmEnableCheck(mils: Long): AlarmCheck {
    val enableArray =
        AlarmCheck(beforeOneWeekDay = false, beforeTrdDay = false, beforeToday = false)
    val inputCal = calClearTime(mils)
    val currentCal = calClearTime(System.currentTimeMillis())
    if (inputCal.timeInMillis >= currentCal.timeInMillis) {
        val remainDay = TimeUnit.MILLISECONDS.toDays(inputCal.timeInMillis) -  TimeUnit.MILLISECONDS.toDays(currentCal.timeInMillis)
        when {
            remainDay >= 7 -> {
                enableArray.beforeOneWeekDay = true
                enableArray.beforeTrdDay = true
                enableArray.beforeToday = true
            }
            remainDay >= 3 -> {
                enableArray.beforeTrdDay = true
                enableArray.beforeToday = true
            }
            else -> enableArray.beforeToday = true
        }
    }
    return enableArray
}

fun calClearTime(mils: Long): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = mils
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.timeInMillis = detailedTime(calendar.timeInMillis)
    return calendar

}

private fun detailedTime(mils:Long):Long{
    return mils.toString().replaceRange(mils.toString().length-3,mils.toString().length,"0").toLong()
}