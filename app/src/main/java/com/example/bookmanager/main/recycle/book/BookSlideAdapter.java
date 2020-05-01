package com.example.bookmanager.main.recycle.book;

import com.example.bookmanager.main.recycle.entity.Book;
import com.example.comment.ui.recycler.BaseMultipleHolder;
import com.example.customui.view.recycle.HorizonSlideAdapter;

import java.util.List;

// 嵌套recyclerView中的item只有一种，所以封装了两个抽象方法，实现即可
public class BookSlideAdapter extends HorizonSlideAdapter {

    public BookSlideAdapter(List data, int itemWidth) {
        super(data, itemWidth);
    }

    @Override
    protected Class getEntity() {
        return Book.class;
    }

    @Override
    protected BaseMultipleHolder.HolderViewProvider getProvider() {
        return new BookSlideHolder.Provider();
    }
}
