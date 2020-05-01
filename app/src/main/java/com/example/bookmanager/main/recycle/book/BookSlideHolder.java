package com.example.bookmanager.main.recycle.book;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.bookmanager.book.BookDetailsFragment;
import com.example.bookmanager.main.recycle.entity.Book;
import com.example.comment.router.RouterUtil;
import com.example.comment.ui.recycler.BaseMultipleHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;

// 嵌套recyclerView中的item
public class BookSlideHolder extends BaseMultipleHolder<Book> {

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R2.id.bookCover)
    SimpleDraweeView bookCover;
    @BindView(R2.id.bookName)
    TextView bookName;
    @BindView(R2.id.author)
    TextView author;

    public BookSlideHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBindBaseViewHolder(Book book) {
        container.setOnClickListener(v -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("isbn", book.getIsbn13());
            RouterUtil.startFragment(BookDetailsFragment.class, data);
        });

        bookCover.setImageURI(Uri.parse(book.getImage()));
        bookName.setText(book.getTitle());
        String authorNames = book.getAuthor().toString().replace(",", "");
        author.setText(authorNames.substring(1, authorNames.length() - 1));
    }

    public static class Provider extends BaseMultipleHolder.HolderViewProvider<Book> {

        @Override
        public BaseMultipleHolder<Book> getHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new BookSlideHolder(inflater.inflate(R.layout.main_book_item, parent, false));
        }
    }
}
