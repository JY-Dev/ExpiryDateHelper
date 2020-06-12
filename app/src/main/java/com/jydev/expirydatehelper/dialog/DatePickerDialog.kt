package com.jydev.expirydatehelper.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Toast
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.util.calClearTime
import com.jydev.expirydatehelper.util.dateFormat1
import kotlinx.android.synthetic.main.datepicker_dialog.*
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialog(context: Context,mils:Long?, onCallbackListner : (mils:Long,date:String) -> Unit) : Dialog(context){
    private val MIN_MONTH = 1
    private val MAX_MONTH = 12
    private var MIN_DAY = 1
    private val MAX_YEAR = 2099
    private var currentYear = 0
    private var currentMonth = 0
    private var currentDay = 0

    private var cal = Calendar.getInstance()
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.datepicker_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)
        cal.timeInMillis = mils?:cal.timeInMillis
        currentYear = cal.get(Calendar.YEAR)
        currentMonth = cal.get(Calendar.MONTH) +1
        currentDay = cal.get(Calendar.DAY_OF_MONTH)
        setValue()
        picker_year.setOnValueChangedListener { picker, oldVal, newVal ->
            cal.set(Calendar.YEAR , newVal)
            picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        picker_month.setOnValueChangedListener { picker, oldVal, newVal ->
            cal.set(Calendar.MONTH, newVal-1)
            picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        picker_day.setOnValueChangedListener { picker, oldVal, newVal ->
            cal.set(Calendar.DAY_OF_MONTH,newVal)
        }

        btn_cancel.setOnClickListener {
            this.dismiss()
        }

        btn_confirm.setOnClickListener {
            if(calClearTime(cal.timeInMillis).timeInMillis>=calClearTime(System.currentTimeMillis()).timeInMillis){
                onCallbackListner(cal.timeInMillis,dateFormat1(cal))
                this.dismiss()
            }
            else Toast.makeText(context,"당일 또는 당일 이후의 날짜를 정해주세요",Toast.LENGTH_SHORT).show()

        }

        this.show()
    }

    private fun setValue(){
        picker_month.minValue = MIN_MONTH
        picker_month.maxValue = MAX_MONTH
        picker_month.value = currentMonth
        picker_year.minValue = currentYear
        picker_year.maxValue = MAX_YEAR
        picker_year.value = currentYear
        picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        picker_day.minValue = MIN_DAY
        picker_day.value = currentDay
    }


}