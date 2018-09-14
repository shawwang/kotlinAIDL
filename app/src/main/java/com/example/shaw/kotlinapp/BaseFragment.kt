package com.example.shaw.kotlinapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment.*

class BaseFragment : Fragment() {
    private var titleArr: Array<String> = arrayOf("首页", "音乐", "电影", "游戏")

    companion object {
        fun newInstance(index: Int): BaseFragment{
            val baseFragment = BaseFragment()
            val args = Bundle()
            args.putInt("index", index)
            baseFragment.arguments = args
            return baseFragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments!!.getInt("index")
        tv_title.text = titleArr[index]
    }
}
