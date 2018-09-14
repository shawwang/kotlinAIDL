package com.example.shaw.kotlinapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_STATIC
import com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_FIXED
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.shaw.kotlinapp.aidl.Book
import com.example.shaw.kotlinapp.aidl.IBookManager
import com.example.shaw.kotlinapp.aidl.IOnNewBookArrivedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {

    private val mHandler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                1 -> {
                    Log.d(TAG, "receive new book" + msg!!.obj)
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }

    companion object {
        const val TAG : String  = "MainActivity"
    }

    override fun onTabReselected(position: Int) {

    }

    override fun onTabSelected(position: Int) {
        viewPager.currentItem = position
    }

    override fun onTabUnselected(position: Int) {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBar.setTabSelectedListener(this)
        navBar.setMode(MODE_FIXED)
        navBar.addItem( BottomNavigationItem (R.drawable.ic_launcher_foreground, "首页").setActiveColorResource(R.color.colorPrimary))
        .addItem( BottomNavigationItem (R.drawable.ic_launcher_foreground, "音乐").setActiveColorResource(R.color.colorPrimary))
        .addItem(BottomNavigationItem (R.drawable.ic_launcher_foreground, "电影").setActiveColorResource(R.color.colorPrimary))
        .addItem( BottomNavigationItem (R.drawable.ic_launcher_foreground, "游戏").setActiveColorResource(R.color.colorPrimary))
        .setFirstSelectedPosition(0)
                .setBackgroundStyle(BACKGROUND_STYLE_STATIC)
                .initialise()
        viewPager.adapter = MyAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                navBar.selectTab(p0)
            }

        })

        val intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    private val mConnection : ServiceConnection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val bookManager = IBookManager.Stub.asInterface(service)
            val bookList = bookManager.bookList
            Log.i(TAG, bookList.javaClass.canonicalName)
            Log.i(TAG, bookList.toString())
            val book = Book(3, "Android进阶")
            bookManager.addBook(book)
            val newBookList = bookManager.bookList
            Log.i(TAG, newBookList.toString())
            bookManager.registerListener(listener)
        }

    }

    private val listener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(newBook: Book?) {
            mHandler.obtainMessage(1, newBook).sendToTarget()
        }

    }

    class MyAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment {
            return BaseFragment.newInstance(p0)
        }

        override fun getCount(): Int {
            return 4
        }
    }
}