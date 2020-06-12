package com.jydev.expirydatehelper.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.data.FoodData

class MainAdapter(context: Context, foodDataArray: MutableList<FoodData>) : BaseAdapter() {
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
        foodTv.text = foodData.foodName
        registerDateTv.text = foodData.registerDate.toString() //TODO: 날짜 포맷 변경
        enpiryDateTv.text = foodData.enpiryDate.toString() //TODO: 유통기한 구하는 함수 제작
        keepWayTv.text = foodData.keepWay
        foodData.imgUrl.let {

        }
        when(foodData.keepWay){
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