package com.yevbes.prueba.model;

import com.yevbes.prueba.model.res.PostModelRes;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("posts")
    Observable<List<PostModelRes>> getPostList();

    @GET("post/{id}")
    Observable<PostModelRes> getPostById(@Path("id") int postId);
}
