package com.jydev.expirydatehelper.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.data.AlarmCheck
import com.jydev.expirydatehelper.data.FoodData
import com.jydev.expirydatehelper.dialog.ImgSelDialog
import com.jydev.expirydatehelper.util.CameraUtil
import com.jydev.expirydatehelper.util.DataProcess
import com.jydev.expirydatehelper.util.PreferenceManager
import kotlinx.android.synthetic.main.activity_food_data_register02.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class FoodDataRegisterActivity02 : BaseActivity() {
    private lateinit var cameraUtil: CameraUtil
    private lateinit var foodData :FoodData
    private lateinit var alarmArray :BooleanArray
    private var foodName = ""
    private var keepWay = ""
        private var enpiryDate = 0L
    private var whereFrom = ""
    private var primaryKey = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_data_register02)
        init()


    }

    private fun init(){
        toolbar_title.text = "식자재 등록"
        cameraUtil = CameraUtil(this)
        whereFrom = intent.extras!!.getString("whereFrom","")
        primaryKey = intent.extras!!.getInt("primaryKey",0)
        if(whereFrom=="수정") modifySetting()
            intent.extras!!.getBooleanArray("alarmCheck")!!.let {
                alarmArray = it
            }
            keepWay = intent.extras!!.getString("keepWay","")
            enpiryDate = intent.extras!!.getLong("enpiryDate",0L)
            foodName = intent.extras!!.getString("foodName","")

        back_btn.setOnClickListener {
            finish()
        }


        photo_add_btn.setOnClickListener {
            tedPermission(this)
        }
        photo_del_btn.setOnClickListener {
            cameraUtil.clearImageUri()
            food_img.setImageDrawable(resources.getDrawable(R.drawable.food,applicationContext.theme))
        }
        complete_btn.setOnClickListener {
            val preferenceManager = PreferenceManager()
            if(whereFrom!="수정"){
                foodData = FoodData(foodName,keepWay,System.currentTimeMillis(),enpiryDate,
                    AlarmCheck(alarmArray[0],alarmArray[1],alarmArray[2]),cameraUtil.getImageUri().toString(),memo_tv.text.toString()
                    ,preferenceManager.getInt(this,"primaryKey")
                )

                preferenceManager.setInt(this,"primaryKey",preferenceManager.getInt(this,"primaryKey")+1)
            } else {
                foodData = FoodData(foodName,keepWay,System.currentTimeMillis(),enpiryDate,
                    AlarmCheck(alarmArray[0],alarmArray[1],alarmArray[2]),cameraUtil.getImageUri().toString(),memo_tv.text.toString()
                    ,primaryKey
                )
            }

            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

                DataProcess().insertData(this,foodData.primaryKey,foodData)
                startActivity(intent)
                finish()



        }
    }

    private fun modifySetting(){
        toolbar_title.text = "식자재 수정"
        alarmArray = booleanArrayOf(false,false,false)
        DataProcess().loadData(this,primaryKey){
            foodData = it
            food_img.setImageURI(Uri.parse(foodData.imgUri))
            memo_tv.setText(foodData.memoStg)
            cameraUtil.setImageUri(Uri.parse(it.imgUri))
            alarmArray.forEachIndexed { index, b ->
                when(index){
                    0 -> alarmArray[0] = foodData.alarmList.beforeOneWeekDay
                    1 -> alarmArray[1] = foodData.alarmList.beforeTrdDay
                    2 -> alarmArray[2] = foodData.alarmList.beforeToday
                }
            }
        }


    }

    private fun tedPermission(context: Context) {

        val permissionListener = object : PermissionListener {
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                //권한 실패시
            }

            override fun onPermissionGranted() {
                ImgSelDialog(context, { cameraUtil.captureCamera() }, { cameraUtil.getAlbum() })
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode != RESULT_OK) return
        when(requestCode){
            cameraUtil.REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        cameraUtil.galleryAddPic()
                        food_img.setImageURI(cameraUtil.getImageUri())
                    } catch (e: Exception) {
                    }

                } else {
                    Toast.makeText(this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            cameraUtil.REQUEST_TAKE_ALBUM ->{
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.data != null) {
                        try {
                            cameraUtil.setImageUri(data.data)
                            food_img.setImageURI(cameraUtil.getImageUri())
                            //cropImage()
                        } catch (e: Exception) {
                        }
                    }
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}