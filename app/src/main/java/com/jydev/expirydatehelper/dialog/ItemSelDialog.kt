package com.jydev.expirydatehelper.dialog


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.activity.FoodDataRegisterActivity01
import com.jydev.expirydatehelper.data.FoodData
import kotlinx.android.synthetic.main.item_sel_dialog.*

class ItemSelDialog(context: Context,foodData:FoodData, onDeleteCallBack: () -> Unit) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.item_sel_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        detail_info_btn.setOnClickListener {
            DetailInfoDialog(context,foodData)
            this.dismiss()
        }
        modify_btn.setOnClickListener {
            val intent = Intent(context,FoodDataRegisterActivity01::class.java)
            intent.putExtra("whereFrom","수정")
            intent.putExtra("primaryKey",foodData.primaryKey)
            context.startActivity(intent)
            this.dismiss()
        }
        del_btn.setOnClickListener {
            onDeleteCallBack()
            this.dismiss()
        }
        cancel_btn.setOnClickListener {
            this.dismiss()
        }
        this.show()
    }
}