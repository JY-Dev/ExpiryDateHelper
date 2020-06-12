package com.jydev.expirydatehelper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jydev.expirydatehelper.fragment.MainFragment

class MainPagerAdapter(fm:FragmentManager,pageCount:Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mPageCount = pageCount

    override fun getItem(position: Int): Fragment {
        return MainFragment(position)
    }

    override fun getCount(): Int {
        return mPageCount
    }
}