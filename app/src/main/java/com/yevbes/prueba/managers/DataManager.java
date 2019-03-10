package com.yevbes.prueba.managers;

import android.content.SharedPreferences;

import com.yevbes.prueba.model.RestService;
import com.yevbes.prueba.model.ServiceGenerator;
import com.yevbes.prueba.model.res.PostModelRes;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DataManager {
    private static DataManager INSTANCE = null;

    private SharedPreferences sharedPreferences;
    private RestService restService;
    private CompositeDisposable disposable;

    private DataManager() {
        restService = ServiceGenerator.createService(RestService.class);
        disposable = new CompositeDisposable();
    }

    // Singleton Pattern
    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public void addDisposable(Disposable disposable){
        this.disposable.add(disposable);
    }

    public void clearDisposable(){
        disposable.clear();
    }

    // region ========== Network ===========

    public Observable<List<PostModelRes>> getPostsList(){
        return restService.getPostList();
    }

    public Observable<PostModelRes> getPostById(int id) {
        return restService.getPostById(id);
    }
}
