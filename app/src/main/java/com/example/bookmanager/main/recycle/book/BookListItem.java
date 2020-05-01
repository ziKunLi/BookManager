package com.example.bookmanager.main.recycle.book;

import com.example.bookmanager.main.recycle.entity.Book;
import com.example.bookmanager.main.recycle.entity.SimpleTitleItem;

import java.util.List;

public class BookListItem {

    private List<Book> books;

    private SimpleTitleItem simpleTitleItem;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public SimpleTitleItem getSimpleTitleItem() {
        return simpleTitleItem;
    }

    public void setSimpleTitleItem(SimpleTitleItem simpleTitleItem) {
        this.simpleTitleItem = simpleTitleItem;
    }
}
