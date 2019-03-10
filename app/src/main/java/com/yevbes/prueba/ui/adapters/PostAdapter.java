package com.yevbes.prueba.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yevbes.prueba.R;
import com.yevbes.prueba.model.res.PostModelRes;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private OnAdapterItemClick listener;

    public void setListener(OnAdapterItemClick listener) {
        this.listener = listener;
    }

    private List<PostModelRes> list;

    public PostAdapter(List<PostModelRes> postModelResList) {
        list = postModelResList;
    }

    public void addRowItems(List<PostModelRes> newElements) {
        list.addAll(newElements);
        notifyDataSetChanged();
    }

    public void updateRowItems(List<PostModelRes> newElements) {
        list = newElements;
        notifyDataSetChanged();
    }

    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public PostModelRes getItem(int position) {
        return list.get(position);
    }

    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.post_rv_item, viewGroup, false);

        // Listener
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(v);
                        }
                    }
                }
        );

        PostViewHolder viewHolder = new PostViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        PostModelRes postModelRes = list.get(i);

        TextView textView = postViewHolder.titlePost;
        textView.setText(postModelRes.getTitle());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
