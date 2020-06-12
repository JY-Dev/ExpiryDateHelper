package com.jydev.expirydatehelper.util

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jydev.expirydatehelper.data.FoodData
import com.jydev.expirydatehelper.room.FoodDataDB
import com.jydev.expirydatehelper.room.FoodDataRoom
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataProcess {
    val type: TypeToken<FoodData> = object : TypeToken<FoodData>() {}
    val gson = GsonBuilder().create()

    //Room data insert
    fun insertData(context: Context, primaryKey: Int, foodData: FoodData) {
        val data = gson.toJson(foodData, type.type)
        val foodDataRoom = FoodDataRoom(primaryKey, data)
        val a = Observable.just(foodDataRoom)
            .subscribeOn(Schedulers.io())
            .subscribe({
                FoodDataDB.getInstance(context)
                    ?.getFoodDataDao()
                    ?.insert(foodDataRoom)
            },
                {
                })
    }

    fun updateData(context: Context, primaryKey: Int, foodData: FoodData, callBack: () -> Unit) {
        val a = Observable.just(FoodDataDB.getInstance(context))
            .subscribeOn(Schedulers.io())
            .subscribe {
                it?.getFoodDataDao()?.updateByPrimary(primaryKey, gson.toJson(foodData, type.type))
                callBack()
            }

    }

    //Room data load
    fun loadAllData(context: Context, Callback: (foodDataList: List<FoodDataRoom>) -> Unit) {
        val a = FoodDataDB
            .getInstance(context)!!
            .getFoodDataDao()
            .getAllExerRoutine()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Callback(it)
            }, {
                Log.e("MyTag", it.message)
            })
    }

    fun loadData(context: Context, primaryKey: Int, Callback: (foodData: FoodData) -> Unit) {
        val a = FoodDataDB
            .getInstance(context)!!
            .getFoodDataDao()
            .getExerRoutine(primaryKey)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Callback(jsonToArray(it.foodData))
            }, {
            })
    }

    fun deleteData(mContext: Context, primaryKey: Int, callBack: () -> Unit) {
        val a = Observable.just(FoodDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe {
                it?.getFoodDataDao()?.deleteGroup(primaryKey)
                callBack()
            }
    }

    fun jsonToArray(jsonArray: String): FoodData {

        return gson.fromJson(jsonArray, type.type)
    }
}