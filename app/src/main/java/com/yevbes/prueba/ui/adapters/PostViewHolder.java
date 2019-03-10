package com.yevbes.prueba.ui.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yevbes.prueba.R;


class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titlePost;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        titlePost = itemView.findViewById(R.id.postTitle);
    }
}
