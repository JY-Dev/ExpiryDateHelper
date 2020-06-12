package com.jydev.expirydatehelper.data

import android.net.Uri


data class FoodData(
    val foodName: String,
    val keepWay: String,
    val registerDate: Long,
    val enpiryDate: Long,
    val alarmList : AlarmCheck,
    val imgUri : String?,
    val memoStg : String,
    val primaryKey : Int
)

data class AlarmCheck(var beforeOneWeekDay: Boolean, var beforeTrdDay: Boolean, var beforeToday: Boolean)
