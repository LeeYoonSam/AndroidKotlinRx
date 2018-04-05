package com.ys.rx.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Single
import kotlinx.android.synthetic.main.act_just_map.*

class SingleJustMapExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_just_map)

        Single.just(4)
                .map { it -> it.toString() }
                .subscribe { it -> tvValue.text = it }

// Java 코드
//        Single.just(4).map(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return String.valueOf(integer);
//            }
//        }).subscribe(new SingleSubscriber<String>() {
//            @Override
//            public void onSuccess(String value) {
//                mValueDisplay.setText(value);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//
//            }
//        });
    }
}