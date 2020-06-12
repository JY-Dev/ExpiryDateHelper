package com.jydev.expirydatehelper.data


data class FoodData(
    val foodName: String,
    val keepWay: String,
    val registerDate: Long,
    val enpiryDate: Long,
    val alarmList : MutableList<AlarmCheck>,
    val imgUrl : String,
    val memoStg : String
)

data class AlarmCheck(var beforeOneWeekDay: Boolean, var beforeTrdDay: Boolean, var beforeToday: Boolean)
