package com.jydev.expirydatehelper.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jydev.expirydatehelper.R
import kotlinx.android.synthetic.main.app_tool_bar.*

class FoodDataRegisterActivity02 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_data_register02)
        init()
    }

    private fun init(){
        toolbar_title.text = "식자재 등록"
        back_btn.setOnClickListener {
            finish()
        }
    }

}