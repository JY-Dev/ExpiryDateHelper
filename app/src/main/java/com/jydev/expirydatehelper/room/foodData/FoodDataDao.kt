package com.jydev.expirydatehelper.room.foodData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface FoodDataDao {
    @Query("SELECT * FROM fooddataroom WHERE `primaryKey` =:primaryKey")
    fun getExerRoutine(primaryKey: Int): Flowable<FoodDataRoom>

    @Query("Delete FROM fooddataroom WHERE `primaryKey` = :primaryKey")
    fun deleteGroup(primaryKey: Int)

    @Query("Update fooddataroom SET primaryKey =:primaryKey, foodData =:foodData WHERE `primaryKey` = :primaryKey")
    fun updateByPrimary(primaryKey: Int, foodData: String)

    //이미 저장된 항목이 있을 경우 데이터를 덮어쓴다
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg foodDataRoom: FoodDataRoom)

    @Query("SELECT * FROM fooddataroom")
    fun getAllExerRoutine(): Flowable<List<FoodDataRoom>>
}