package com.jydev.expirydatehelper.dialog


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Window
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.data.FoodData
import com.jydev.expirydatehelper.util.dateFormat1
import kotlinx.android.synthetic.main.detail_info_dialog.*
import kotlinx.android.synthetic.main.img_sel_dialog.*
import kotlinx.android.synthetic.main.img_sel_dialog.cancel_sel_btn

class DetailInfoDialog(context: Context,foodData: FoodData) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.detail_info_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(true)
        if(foodData.imgUri!=null)
            food_img.setImageURI(Uri.parse(foodData.imgUri))
        food_name_tv.text = foodData.foodName
        register_date_tv.text = dateFormat1(foodData.registerDate)
        enpiry_date_tv.text = dateFormat1(foodData.enpiryDate)
        keep_way_tv.text = foodData.keepWay
        memo_tv.text = foodData.memoStg
        cancel_sel_btn.setOnClickListener {
            this.dismiss()
        }
        this.show()
    }
}