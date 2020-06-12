package com.jydev.expirydatehelper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodDataRoom(@PrimaryKey val primaryKey:Int,val foodData: String)