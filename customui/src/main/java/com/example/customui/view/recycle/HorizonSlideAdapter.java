package com.example.customui.view.recycle;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.example.comment.ui.recycler.BaseMultipleAdapter;
import com.example.comment.ui.recycler.BaseMultipleHolder;

import java.util.List;

// 只有一种类型的，支持横滑的adapter
public abstract class HorizonSlideAdapter<T> extends BaseMultipleAdapter {
    protected View itemView;
    private int itemWidth;

    public HorizonSlideAdapter(List data, int itemWidth){
        super(data);
        this.itemWidth = itemWidth;
    }

    protected abstract Class getEntity();

    protected abstract BaseMultipleHolder.HolderViewProvider getProvider();

    @Override
    protected void initType() {
        bindType(getEntity(), getProvider());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMultipleHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        itemView = holder.getItemView();
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        if(layoutParams == null){
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width = itemWidth;
        itemView.setLayoutParams(layoutParams);
    }

    public int getItemWidth(){
        return itemWidth;
    }
}
