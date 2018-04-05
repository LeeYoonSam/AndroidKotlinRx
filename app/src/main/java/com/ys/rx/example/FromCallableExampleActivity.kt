package com.ys.rx.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_fromcallable.*
import kotlinx.android.synthetic.main.act_just_example.*
import java.util.concurrent.Callable

class FromCallableExampleActivity  : AppCompatActivity() {

    lateinit var mSimpleStringAdapter: SimpleStringAdapter
    private lateinit var disposable: Disposable

    var mRestClient: RestClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_fromcallable)

        setAdapter()
        createObservable()
    }

    init {
        mRestClient = RestClient(this)
    }

    fun setAdapter() {
        mSimpleStringAdapter = SimpleStringAdapter {
            Toast.makeText(this, "$it Clicked", Toast.LENGTH_SHORT).show()
        }

        rvTvShow.layoutManager = LinearLayoutManager(this)
        rvTvShow.layoutManager.isItemPrefetchEnabled = true
        rvTvShow.adapter = mSimpleStringAdapter
    }

    fun createObservable() {

        disposable = Observable.fromCallable { mRestClient!!.getFavoriteTvShows() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                { data -> displayTvShows(data as ArrayList<String>) },
                { t: Throwable? -> t!!.printStackTrace() },
                { Log.d(localClassName, "Complete!") }
        )
    }

    fun displayTvShows(tvShows: ArrayList<String>) {
        mSimpleStringAdapter.setStrings(tvShows)
        pbLoading.visibility = View.GONE
        rvTvShow.visibility = View.VISIBLE
    }

    override fun onDestroy() {

        if(!disposable?.isDisposed)
            disposable?.dispose()

        super.onDestroy()
    }

}