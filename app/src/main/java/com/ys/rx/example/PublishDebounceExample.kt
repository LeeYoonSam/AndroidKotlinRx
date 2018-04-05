package com.ys.rx.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.act_publish_debounce.*
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import android.text.Editable
import android.text.TextWatcher



class PublishDebounceExample : AppCompatActivity() {

    lateinit var simpleStringAdapter: SimpleStringAdapter
    lateinit var publishSubject: PublishSubject<String>
    lateinit var disposable: Disposable

    val restClient: RestClient = RestClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_publish_debounce)

        setAdaapter()
        listenToSearchInput()
        createObservables()
    }

    private fun setAdaapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isItemPrefetchEnabled = true

        simpleStringAdapter = SimpleStringAdapter {
            Toast.makeText(this, "$it Clicked", Toast.LENGTH_SHORT).show()
        }

        rvSearchResult.layoutManager = linearLayoutManager
        rvSearchResult.adapter = simpleStringAdapter
    }

    private fun listenToSearchInput() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                publishSubject.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun createObservables() {
        publishSubject = PublishSubject.create()

        disposable = publishSubject.debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map { searchWord -> restClient.searchForCity(searchWord) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    handleSearchResults(it)
                }
    }

    private fun handleSearchResults(cities: List<String>) {
        if (cities.isEmpty()) {
            showNoSearchResults()
        } else {
            showSearchResults(cities)
        }
    }

    private fun showNoSearchResults() {
        tvNoResult.visibility = View.VISIBLE
        rvSearchResult.visibility = View.GONE
    }

    private fun showSearchResults(cities: List<String>) {
        tvNoResult.visibility = View.GONE
        rvSearchResult.visibility = View.VISIBLE
        simpleStringAdapter.setStrings(cities as ArrayList<String>)
    }

    override fun onDestroy() {
        if(!disposable.isDisposed) {
            disposable.dispose()
        }

        super.onDestroy()
    }
}