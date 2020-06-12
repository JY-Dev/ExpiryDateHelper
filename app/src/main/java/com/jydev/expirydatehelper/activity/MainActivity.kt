package com.jydev.expirydatehelper.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.jydev.expirydatehelper.R
import com.jydev.expirydatehelper.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var tabList : Array<String>
    lateinit var tabPagerAdpater : MainPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }



   private fun init(){
       tabList = resources.getStringArray(R.array.tab_list)
       tabListSet()
        add_btn.setOnClickListener {
            startActivity(Intent(this,FoodDataRegisterActivity01::class.java))
        }

    }

    private fun tabListSet(){
        tabList.forEach {
            tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView(it)))
        }
        tabPagerAdpater = MainPagerAdapter(supportFragmentManager,tab_layout.tabCount)
        main_pager.adapter = tabPagerAdpater
        main_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!=null)
                main_pager.currentItem = tab.position
            }

        })
    }

    private fun createTabView(tabName : String) : View {
        val tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
        val tabNameTv = tabView.findViewById<TextView>(R.id.tab_name_tv)
        tabNameTv.text = tabName
        return tabView
    }
}