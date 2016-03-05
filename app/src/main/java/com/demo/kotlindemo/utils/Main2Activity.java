package com.demo.kotlindemo.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.demo.kotlindemo.R;

import java.io.File;
import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Main2Activity extends AppCompatActivity {

    String TAG = "Main2Activity";
    FileFinder fileFinder = new FileFinder();
    HashMap<String, String> songsList= new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        fileFinder.fetchDirectories();
        fileFinder.setMatchFilePattern(".mp3");
        Action0 completeAction = new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "directory:" + fileFinder.getSongsList());
            }
        };

        Action1 onNext = new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "directory:" + fileFinder.getSongsList());
            }
        };

        Action1 onError = new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "onError:" + o);
            }
        };


        Observable.from(fileFinder.getDirectories()).map(new Func1<File, Object>() {
            @Override
            public Object call(File file) {
                Log.d(TAG, "directory:" + file.getName());
                fileFinder.scanDirectories(file.getAbsolutePath());
                return fileFinder.getSongsList();
            }
        }).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError,completeAction);


    }
}
