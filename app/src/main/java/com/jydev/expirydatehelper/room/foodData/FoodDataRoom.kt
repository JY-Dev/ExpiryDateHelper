package com.jydev.expirydatehelper.room.foodData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodDataRoom(@PrimaryKey val primaryKey:Int,val foodData: String)