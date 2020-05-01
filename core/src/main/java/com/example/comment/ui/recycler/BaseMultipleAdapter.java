package com.example.comment.ui.recycler;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMultipleAdapter extends RecyclerView.Adapter<BaseMultipleHolder> {

    private Context context;

    private List data;

    private List<Pair<Class, BaseMultipleHolder.HolderViewProvider>> typeList = new ArrayList<>();

    protected abstract void initType();

    public void setData(List data){
        this.data = data;
    }

    public List getData(){
        return this.data;
    }

    public BaseMultipleAdapter(List data){
        initType();
        this.data = data;
    }

    @NonNull
    @Override
    public BaseMultipleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        if(viewType == -1){
            return new ErrorViewHolder.ErrorViewHolderProvider().getHolder(LayoutInflater.from(context), parent);
        }
        return typeList.get(viewType).second.getHolder(LayoutInflater.from(context), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMultipleHolder holder, int position) {
        if(holder instanceof ErrorViewHolder){
            holder.onBindBaseViewHolder("不支持类型");
        }
        holder.onBindBaseViewHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getData().get(position);
        for(int i = 0; i < typeList.size(); i++){
            Class clazz = typeList.get(i).first;
            if(clazz.isInstance(object)){
                return i;
            }
        }
        return -1;
    }

    protected void bindType(Class entity, BaseMultipleHolder.HolderViewProvider holder) {
        typeList.add(new Pair<>(entity, holder));
    }
}
