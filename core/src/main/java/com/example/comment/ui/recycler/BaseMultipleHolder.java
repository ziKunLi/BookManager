package com.example.comment.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMultipleHolder<T> extends RecyclerView.ViewHolder {

    protected View itemView;

    public Unbinder unbinder;

    private Context context;

    public BaseMultipleHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        context = itemView.getContext();
        unbinder = ButterKnife.bind(this, itemView);
    }

    public abstract void onBindBaseViewHolder(T t);

    public View getItemView(){
        return itemView;
    }

    public Context getContext(){
        return context;
    }

    public static abstract class HolderViewProvider<T>{

        public abstract BaseMultipleHolder<T> getHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
    }
}
