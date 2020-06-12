package com.jydev.expirydatehelper.room.foodData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FoodDataRoom::class),version = 1)
abstract class FoodDataDB :RoomDatabase(){
    abstract fun getFoodDataDao() : FoodDataDao
    companion object{
        private var INSTANCE : FoodDataDB? = null

        fun getInstance(context: Context) : FoodDataDB?{
            if(INSTANCE ==null){
                synchronized(FoodDataDB::class){
                    INSTANCE = Room.databaseBuilder(context,
                        FoodDataDB::class.java,"fooddata.db").build()
                }
            }
            return INSTANCE
        }
    }
}