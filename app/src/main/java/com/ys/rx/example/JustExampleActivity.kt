package com.ys.rx.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.act_just_example.*
import kotlin.collections.ArrayList


class JustExampleActivity : AppCompatActivity() {

    lateinit var mSimpleStringAdapter: SimpleStringAdapter
    lateinit var disposable:Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_just_example)

        setAdapter()
        createObservable()
    }

    fun setAdapter() {
        mSimpleStringAdapter = SimpleStringAdapter {
            Toast.makeText(this, "$it Clicked", Toast.LENGTH_SHORT).show()
        }

        rvColorList.layoutManager = LinearLayoutManager(this)
        rvColorList.layoutManager.isItemPrefetchEnabled = true
        rvColorList.adapter = mSimpleStringAdapter
    }

    fun createObservable() {
        val listObservable: Observable<ArrayList<String>> = Observable.just(getColorList())

        disposable = listObservable.subscribe(
                { data -> mSimpleStringAdapter.setStrings(data) },
                { t: Throwable? -> t!!.printStackTrace() },
                { Log.d(localClassName, "Complete!") }
        )
    }

    fun getColorList(): ArrayList<String>? {
        val colors: ArrayList<String> = ArrayList()
        colors.add("blue")
        colors.add("green")
        colors.add("red")
        colors.add("chartreuse")
        colors.add("Van Dyke Brown")

        return colors
    }

    override fun onDestroy() {

        if(!disposable.isDisposed)
            disposable.dispose()

        super.onDestroy()
    }
}
