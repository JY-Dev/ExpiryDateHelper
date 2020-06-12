package com.jydev.expirydatehelper.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.room.foodData.FoodDataRoom
import com.jydev.expirydatehelper.util.DataProcess
import com.jydev.expirydatehelper.util.checkEnpiryDate
import com.jydev.expirydatehelper.util.dateFormat1
import com.jydev.expirydatehelper.util.getEnpiryDay

class MainAdapter(context: Context, foodDataArray: List<FoodDataRoom>, page: Int) : BaseAdapter() {
    private var mFoodDataArray = foodDataArray
    private val mContext = context
    private val mKeepWayImg = context.resources.obtainTypedArray(R.array.keep_way_img_list)

    @SuppressLint("ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view :View = convertView ?: LayoutInflater.from(mContext).inflate(R.layout.main_item,null)
        val foodTv = view.findViewById<TextView>(R.id.added_food_tv)
        val registerDateTv = view.findViewById<TextView>(R.id.register_date_tv)
        val enpiryDateTv = view.findViewById<TextView>(R.id.enpiry_date_tv)
        val foodImg = view.findViewById<ImageView>(R.id.food_img)
        val keepWayTv = view.findViewById<TextView>(R.id.keep_way_tv)
        val keepWayImg = view.findViewById<ImageView>(R.id.keep_way_img)
        val foodData = mFoodDataArray[position]
        val enpiryDayTv = view.findViewById<TextView>(R.id.enpiry_day_tv)
        val dayTv = view.findViewById<TextView>(R.id.day_tv)
        val dataProcess = DataProcess()
        if(dataProcess.jsonToArray(foodData.foodData).imgUri!=null)
            foodImg.setImageURI(Uri.parse(dataProcess.jsonToArray(foodData.foodData).imgUri))
        foodTv.text = dataProcess.jsonToArray(foodData.foodData).foodName
        registerDateTv.text = dateFormat1(dataProcess.jsonToArray(foodData.foodData).registerDate)
        enpiryDateTv.text = dateFormat1(dataProcess.jsonToArray(foodData.foodData).enpiryDate)
        keepWayTv.text = dataProcess.jsonToArray(foodData.foodData).keepWay
        when(checkEnpiryDate(dataProcess.jsonToArray(foodData.foodData).enpiryDate)){
            "초과" -> {
                enpiryDayTv.setTextColor(ContextCompat.getColor(mContext,R.color.red))
                dayTv.setTextColor(ContextCompat.getColor(mContext,R.color.red))
            }
            "임박" -> {
                enpiryDayTv.setTextColor(ContextCompat.getColor(mContext,R.color.yellow))
                dayTv.setTextColor(ContextCompat.getColor(mContext,R.color.yellow))
            }
            else -> {
                enpiryDayTv.setTextColor(ContextCompat.getColor(mContext,R.color.blue))
                dayTv.setTextColor(ContextCompat.getColor(mContext,R.color.blue))
            }
        }
        if(getEnpiryDay(dataProcess.jsonToArray(foodData.foodData).enpiryDate)==0L)
        enpiryDayTv.text = "당"
        else enpiryDayTv.text = getEnpiryDay(dataProcess.jsonToArray(foodData.foodData).enpiryDate).toString()

        when(dataProcess.jsonToArray(foodData.foodData).keepWay){
            "상온" -> keepWayImg.setBackgroundResource(mKeepWayImg.getResourceId(0,0))
            "냉장" -> keepWayImg.setBackgroundResource(mKeepWayImg.getResourceId(1,0))
            "냉동" -> keepWayImg.setBackgroundResource(mKeepWayImg.getResourceId(2,0))
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return mFoodDataArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mFoodDataArray.size
    }


}