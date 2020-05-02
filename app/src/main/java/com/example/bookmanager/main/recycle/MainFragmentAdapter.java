package com.example.bookmanager.main.recycle;

import com.example.bookmanager.main.recycle.book.BookHolder;
import com.example.bookmanager.main.recycle.book.BookListItem;
import com.example.comment.ui.recycler.BaseMultipleAdapter;

import java.util.List;

public class MainFragmentAdapter extends BaseMultipleAdapter {

    public MainFragmentAdapter(List data) {
        super(data);
    }

    @Override
    protected void initType() {
        // 绑定rv相关item
        bindType(BookListItem.class, new BookHolder.Provider());
    }
}
