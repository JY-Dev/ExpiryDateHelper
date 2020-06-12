package com.jydev.expirydatehelper.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.adapter.MainAdapter
import com.jydev.expirydatehelper.dialog.ItemSelDialog
import com.jydev.expirydatehelper.room.foodData.FoodDataRoom
import com.jydev.expirydatehelper.util.DataProcess
import com.jydev.expirydatehelper.util.checkEnpiryDate

class MainFragment(position : Int,context:Context) : Fragment() {
    private val mPosition = position
    private val mContext = context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.main_fragment,null)
        val itemList = view.findViewById<ListView>(R.id.item_list)
        DataProcess().loadAllData(mContext){
            val adapter = MainAdapter(mContext,devideList(it),mPosition)
            itemList.adapter = adapter
            itemList.setOnItemClickListener { parent, view, position, id ->
                val data = adapter.getItem(position) as FoodDataRoom
                ItemSelDialog(mContext,DataProcess().jsonToArray(data.foodData)) {
                    DataProcess().deleteData(mContext,data.primaryKey){
                        Thread(Runnable {
                            val a = mContext as Activity
                            a.runOnUiThread {
                                adapter.notifyDataSetChanged()
                            }
                        }).start()

                    }
                }
            }
        }

        return view
    }

    private fun devideList(foodDataList:List<FoodDataRoom>) : List<FoodDataRoom>{
        val dataProcess = DataProcess()
        return if(mPosition>0){
            foodDataList.filter {
                when(mPosition){
                    1 -> dataProcess.jsonToArray(it.foodData).keepWay == "냉동"
                    2 ->dataProcess.jsonToArray(it.foodData).keepWay == "냉장"
                    3 -> dataProcess.jsonToArray(it.foodData).keepWay == "상온"
                    4 -> {
                        checkEnpiryDate(dataProcess.jsonToArray(it.foodData).enpiryDate)=="임박"
                    }
                    else -> checkEnpiryDate(dataProcess.jsonToArray(it.foodData).enpiryDate)=="초과"
                }
            }
        } else foodDataList
    }
}