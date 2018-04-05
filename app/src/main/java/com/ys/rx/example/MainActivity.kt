package com.ys.rx.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.act_main.*

class MainActivity  : AppCompatActivity() {

    lateinit var mainAdapter: MainAdapter
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        createObservable()
    }

    private fun createObservable() {
        val listObservable: Observable<ArrayList<ExampleActivityModel>> = Observable.just(getExampleList())

        disposable = listObservable.subscribe(
                { data ->
                    run {
                        mainAdapter = MainAdapter(data) {
                            val intent = Intent(this, it.mExampleActivityClass)
                            startActivity(intent)
                        }

                        rvMain.layoutManager = LinearLayoutManager(this)
                        rvMain.layoutManager.isItemPrefetchEnabled = true
                        rvMain.adapter = mainAdapter
                    }
                },
                { t: Throwable? -> t!!.printStackTrace() },
                { Log.d(localClassName, "Complete!") }
        )
    }

    private fun getExampleList(): ArrayList<ExampleActivityModel>? {
        val acts: ArrayList<ExampleActivityModel> = ArrayList()
        acts.add(ExampleActivityModel(JustExampleActivity::class.java, "Example 1: JustExampleActivity"))
        acts.add(ExampleActivityModel(FromCallableExampleActivity::class.java, "Example 2: FromCallableExampleActivity"))
        acts.add(ExampleActivityModel(SingleFromCallableActivity::class.java, "Example 3: SingleFromCallableActivity"))

        return acts
    }

    override fun onDestroy() {

        if(!disposable.isDisposed)
            disposable.dispose()

        super.onDestroy()
    }
}