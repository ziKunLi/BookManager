package com.example.bookmanager.book;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.bookmanager.StaticDataPool;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.comment.net.NetClient;
import com.example.comment.net.callback.IFailure;
import com.example.comment.net.callback.ISuccess;
import com.example.comment.util.LogUtil;
import com.example.customui.view.MoreTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;

public class BookDetailsFragment extends BaseFragment {

    private static String BOOK_OPEN_API = "v2/book/isbn/:";

    private static String API_KEY = "?apikey=0b2bdeda43b5688921839c8ecb20399b";

    @BindView(R2.id.title)
    TextView title;
    @BindView(R2.id.author)
    TextView author;
    @BindView(R2.id.binding)
    TextView binding;
    @BindView(R2.id.publisher)
    TextView publisher;
    @BindView(R2.id.pages)
    TextView pages;
    @BindView(R2.id.isbn13)
    TextView isbn13;
    @BindView(R2.id.bookCover)
    SimpleDraweeView bookCover;
    @BindView(R2.id.summary)
    MoreTextView summary;

    public BookDetailsFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public void onSaveInstance(HashMap<String, Object> data) {
        super.onSaveInstance(data);
        NetClient.builder()
                .url(StaticDataPool.DOU_BAN_BOOK_API + BOOK_OPEN_API + data.get("isbn") + API_KEY)
                .success(response -> {
                    Book book = (Book) new BookDataConverter(response).convert().get(0);
                    title.setText(book.getTitle());
                    String authorNames = book.getAuthor().toString().replace(",", "");
                    author.setText(String.format(getResources().getString(R.string.author), authorNames.substring(1, authorNames.length() - 1)));
                    binding.setText(String.format(getResources().getString(R.string.binding), book.getBinding(), book.getSeries().getTitle()));
                    publisher.setText(String.format(getResources().getString(R.string.publisher), book.getPublisher(), book.getPubdate()));
                    pages.setText(String.format(getResources().getString(R.string.pages), book.getPages()));
                    isbn13.setText(String.format(getResources().getString(R.string.isbn13), book.getIsbn13()));
                    bookCover.setImageURI(Uri.parse(book.getImage()));
                    summary.setText(book.getSummary());
                    LogUtil.v(StaticDataPool.HTTP_TAG, response);
                })
                .failure(throwable -> LogUtil.v(StaticDataPool.HTTP_TAG, throwable.getMessage()))
                .build()
                .get();
    }

    @Override
    public Object setLayout() {
        return R.layout.book_details_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {

    }
}
