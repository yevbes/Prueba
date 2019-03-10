package com.yevbes.prueba.managers;

import android.content.SharedPreferences;

import com.yevbes.prueba.model.RestService;
import com.yevbes.prueba.model.ServiceGenerator;
import com.yevbes.prueba.model.res.PostModelRes;

import java.util.List;

import io.reactivex.Observable;

public class DataManager {
    private static DataManager INSTANCE = null;

    private SharedPreferences sharedPreferences;
    private RestService restService;

    private DataManager() {
        restService = ServiceGenerator.createService(RestService.class);
    }

    // Singleton Pattern
    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    // region ========== Network ===========

    public Observable<List<PostModelRes>> getPostsList(){
        return restService.getPostList();
    }

    public Observable<PostModelRes> getPostById(int id) {
        return restService.getPostById(id);
    }
}
