package com.ys.rx.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.act_single_fromcallable.*

class SingleFromCallableActivity: AppCompatActivity() {

    lateinit var simpleStringAdapter: SimpleStringAdapter
    val restClient: RestClient? = RestClient(this)
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_single_fromcallable)

        setAdapter()
        createObserver()
    }

    fun setAdapter() {

        simpleStringAdapter = SimpleStringAdapter {
            Toast.makeText(this, "$it Clicked", Toast.LENGTH_SHORT).show()
        }

        val linearLayoutManager = LinearLayoutManager(this)
        rvTvShow.layoutManager = linearLayoutManager
        rvTvShow.layoutManager.isItemPrefetchEnabled = true
        rvTvShow.adapter = simpleStringAdapter
    }

    fun createObserver() {
        val singleObserver: Single<List<String>>? = Single.fromCallable { restClient?.getFavoriteTvShows() }

        disposable = singleObserver!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { it -> displayTvShows(it as ArrayList<String>) },
                        { displayErrorMessage() })

    }

    fun displayTvShows(tvShows: ArrayList<String>) {
        simpleStringAdapter.setStrings(tvShows)
        pbLoading.visibility = View.GONE
        rvTvShow.visibility = View.VISIBLE
    }

    fun displayErrorMessage() {
        pbLoading.setVisibility(View.GONE)
        tvError.setVisibility(View.VISIBLE)
    }

    override fun onDestroy() {

        if(!disposable!!.isDisposed) {
            disposable?.dispose()
        }
        super.onDestroy()
    }
}