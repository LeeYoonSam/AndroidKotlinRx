package com.ys.rx.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.act_publish.*

class PublishSubjectExample : AppCompatActivity() {

    lateinit var mCounterEmitter: PublishSubject<Int>

    var mCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_publish)

        createCounterEmitter()
    }

    fun onClickAdd(view: View) {
        mCounter++;
        mCounterEmitter.onNext(mCounter);
    }

    fun createCounterEmitter() {
        mCounterEmitter = PublishSubject.create()

        mCounterEmitter.subscribe(
                {intValue -> tvCount.text = intValue.toString()},
                {t -> t.printStackTrace()}
        )
    }

}