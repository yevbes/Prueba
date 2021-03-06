package com.yevbes.prueba.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yevbes.prueba.R;
import com.yevbes.prueba.managers.DataManager;
import com.yevbes.prueba.model.res.PostModelRes;
import com.yevbes.prueba.ui.adapters.OnAdapterItemClick;
import com.yevbes.prueba.ui.adapters.PostAdapter;
import com.yevbes.prueba.utils.NetworkStatusChecker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostListFragment extends Fragment {


    private PostAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DataManager dataManager;
    private CoordinatorLayout coordinatorLayout;


    private List<PostModelRes> postModelResList;

    public PostListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dataManager = DataManager.getInstance();
        postModelResList = new ArrayList<>();
        mAdapter = new PostAdapter(postModelResList);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataManager.clearDisposable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);



            final RecyclerView recyclerView = view.findViewById(R.id.postRecyclerView);

            mAdapter.setListener(new OnAdapterItemClick() {
                @Override
                public void onItemClick(View view) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    Toast.makeText(getContext(), String.valueOf(mAdapter.getItem(position).getId()) ,Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(llm);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

            coordinatorLayout = getView().findViewById(R.id.coordinatorLayout);

            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getPosts();
                }
            });

            int[] colors = {android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light};
            swipeRefreshLayout.setColorSchemeColors(colors);
        }
        getPosts();
    }

    public void getPosts() {
        if (NetworkStatusChecker.isInternetConnected(getContext())) {
            swipeRefreshLayout.setRefreshing(true);

            /*Call<List<PostModelRes>> call = dataManager.getPostsList();
            call.enqueue(new Callback<List<PostModelRes>>() {
                @Override
                public void onResponse(Call<List<PostModelRes>> call, Response<List<PostModelRes>> response) {
                    if (response.code() == 200) { // OK
                        postModelResList = response.body();
                        mAdapter.updateRowItems(postModelResList);
                    } else if (response.code() == 404) {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Error on request!",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Houston we have a problems!",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<PostModelRes>> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });*/

            dataManager.addDisposable(
                    dataManager.getPostsList().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<List<PostModelRes>>() {
                                @Override
                                public void onNext(List<PostModelRes> postModelRes) {
                                    postModelResList = postModelRes;
                                    mAdapter.updateRowItems(postModelResList);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                @Override
                                public void onComplete() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            })
            );
        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Check for internet connection!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
