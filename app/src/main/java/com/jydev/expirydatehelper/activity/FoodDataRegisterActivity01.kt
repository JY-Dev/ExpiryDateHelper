package com.jydev.expirydatehelper.activity

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.data.AlarmCheck
import com.jydev.expirydatehelper.dialog.DatePickerDialog
import com.jydev.expirydatehelper.util.DataProcess
import com.jydev.expirydatehelper.util.alarmEnableCheck
import com.jydev.expirydatehelper.util.dateFormat1
import kotlinx.android.synthetic.main.activity_food_data_register01.*
import kotlinx.android.synthetic.main.activity_food_data_register01.enpiry_date_tv
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.main_item.*
import java.util.*

class FoodDataRegisterActivity01 : BaseActivity() {
    lateinit var spinnerAdapter : ArrayAdapter<String>
    lateinit var keepWayList : Array<String>
    var enpiryTimeMil : Long? = null
    var whereFrom = ""
    var primaryKey = 0
    private var keepWay = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_data_register01)
        init()
    }

    private fun init(){
        toolbar_title.text = "식자재 등록"
        whereFrom = intent.extras!!.getString("whereFrom","")
        primaryKey = intent.extras!!.getInt("primaryKey",0)
        if (whereFrom=="수정") modifySetting()
        keepWayList = resources.getStringArray(R.array.keep_way_list)
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,keepWayList)
        keep_way_spinner.adapter = spinnerAdapter
        keep_way_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(parent?.getChildAt(0)!=null){
                    val parentTv = parent.getChildAt(0) as TextView
                    parentTv.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textSize = 20F
                    }
                    keepWay = keepWayList[position]
                }

            }

        }
        enpiry_date.setOnClickListener {
            DatePickerDialog(this,enpiryTimeMil){timeMils,date ->
                enpiryTimeMil = timeMils
                enpiry_date_tv.text = date
                checkBoxSetEnable(alarmEnableCheck(timeMils))
            }
        }
        next_btn.setOnClickListener {
            if(food_name_tv.text.isNotEmpty()&&enpiry_date_tv.text.isNotEmpty()){
                val intent = Intent(this,FoodDataRegisterActivity02::class.java)
                intent.putExtra("foodName",food_name_tv.text.toString())
                intent.putExtra("keepWay",keepWay)
                intent.putExtra("enpiryDate",enpiryTimeMil)
                val alarmCheckArray = mutableListOf(alarm_check01.isChecked,alarm_check02.isChecked,alarm_check03.isChecked)
                intent.putExtra("alarmCheck",alarmCheckArray.toBooleanArray())
                intent.putExtra("whereFrom",whereFrom)
                intent.putExtra("primaryKey",primaryKey)
                startActivity(intent)
            } else Toast.makeText(this,"모든 항목을 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun modifySetting(){
        toolbar_title.text = "식자재 수정"
        DataProcess().loadData(this,primaryKey) {
            food_name_tv.setText(it.foodName)
            when(it.keepWay){
                "냉장" ->  keep_way_spinner.setSelection(0)
                "냉동" ->  keep_way_spinner.setSelection(1)
                "상온" ->  keep_way_spinner.setSelection(2)
            }
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.enpiryDate
            enpiryTimeMil = it.enpiryDate
            enpiry_date_tv.text = dateFormat1(cal)
            checkBoxSetEnable(it.alarmList)
        }
    }

    private fun checkBoxSetEnable(alarmCheck: AlarmCheck){
        alarm_check01.apply {
            isEnabled = alarmCheck.beforeOneWeekDay
            isChecked = false
        }
        alarm_check02.apply {
            isEnabled = alarmCheck.beforeTrdDay
            isChecked = false
        }
        alarm_check03.apply {
            isEnabled = alarmCheck.beforeToday
            isChecked = false
        }
    }
}