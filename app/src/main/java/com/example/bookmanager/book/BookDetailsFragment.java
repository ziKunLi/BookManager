package com.example.bookmanager.book;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.bookmanager.StaticDataPool;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.comment.net.NetClient;
import com.example.comment.net.callback.IError;
import com.example.comment.net.callback.IFailure;
import com.example.comment.ui.loader.LoaderStyle;
import com.example.comment.util.LogUtil;
import com.example.customui.view.BounceScrollView;
import com.example.customui.view.MoreTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;

public class BookDetailsFragment extends BaseFragment {

    private static String BOOK_OPEN_API = "v2/book/isbn/:";

    private static String API_KEY = "?apikey=0b2bdeda43b5688921839c8ecb20399b";

    @BindView(R2.id.title)
    TextView title; // 书名
    @BindView(R2.id.author)
    TextView author; // 作者
    @BindView(R2.id.binding)
    TextView binding;
    @BindView(R2.id.publisher)
    TextView publisher; // 出版社/出版日期
    @BindView(R2.id.pages)
    TextView pages; // 页数
    @BindView(R2.id.isbn13)
    TextView isbn13;
    @BindView(R2.id.bookCover)
    SimpleDraweeView bookCover;
    @BindView(R2.id.summary)
    MoreTextView summary; // 简介
    @BindView(R2.id.catalogTag)
    TextView catalogTag;
    @BindView(R2.id.catalog)
    MoreTextView catalog;
    @BindView(R2.id.container)
    BounceScrollView container;
    @BindView(R2.id.failTag)
    RelativeLayout failTag;
    @BindView(R2.id.tipText)
    TextView tipText;

    public BookDetailsFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public void onDataGet(HashMap<String, Object> data) {
        // okHttp3 + retrofit2 的网络框架
        NetClient.builder()
                .url(StaticDataPool.DOU_BAN_BOOK_API + BOOK_OPEN_API + data.get("isbn") + API_KEY)
                .success(response -> {
                    try {
                        container.setVisibility(View.VISIBLE);
                        failTag.setVisibility(View.GONE);
                        Book book = (Book) new BookDataConverter(response).convert().get(0);

                        title.setText(book.getTitle());
                        String authorNames = book.getAuthor().toString().replace(",", "");
                        author.setText(String.format(getResources().getString(R.string.author), authorNames.substring(1, authorNames.length() - 1)));
                        if (book.getSeries() == null) {
                            binding.setText(book.getBinding());
                        } else {
                            binding.setText(String.format(getResources().getString(R.string.binding), book.getBinding(), book.getSeries().getTitle()));
                        }
                        publisher.setText(String.format(getResources().getString(R.string.publisher), book.getPublisher(), book.getPubdate()));
                        pages.setText(String.format(getResources().getString(R.string.pages), book.getPages()));
                        isbn13.setText(String.format(getResources().getString(R.string.isbn13), book.getIsbn13()));
                        bookCover.setImageURI(Uri.parse(book.getImage()));
                        summary.setText(book.getSummary());
                        // 目录，有的没有，就隐藏相关ui
                        if (TextUtils.isEmpty(book.getCatalog())) {
                            catalog.setVisibility(View.GONE);
                            catalogTag.setVisibility(View.GONE);
                        } else {
                            catalog.setVisibility(View.VISIBLE);
                            catalogTag.setVisibility(View.VISIBLE);
                            catalog.setText(book.getCatalog());
                        }
                        LogUtil.v(StaticDataPool.HTTP_TAG, response);
                    }catch (Exception e){
                        container.setVisibility(View.GONE);
                        failTag.setVisibility(View.VISIBLE);
                        tipText.setText("未知错误");
                    }
                })
                .failure(throwable -> {
                    container.setVisibility(View.GONE);
                    failTag.setVisibility(View.VISIBLE);
                    LogUtil.v(StaticDataPool.HTTP_TAG, throwable.getMessage());
                })
                .error((code, message) -> {
                    LogUtil.v(StaticDataPool.HTTP_TAG, message);
                    container.setVisibility(View.GONE);
                    failTag.setVisibility(View.VISIBLE);
                })
                .loader(getContext(), LoaderStyle.BallClipRotateIndicator)
                .build()
                .get();
    }

    @Override
    public Object setLayout() {
        return R.layout.book_details_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {
        container.setVisibility(View.GONE);
        failTag.setVisibility(View.GONE);
    }
}
