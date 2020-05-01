package com.example.bookmanager.main.recycle.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.comment.router.RouterUtil;
import com.example.comment.ui.recycler.BaseMultipleHolder;
import com.example.comment.util.DensityUtil;
import com.example.customui.view.recycle.CanScrollHorizonRecyclerView;
import com.example.customui.view.recycle.HorizonSlideAdapter;
import com.example.customui.view.recycle.HorizontalSnapHolder;

import butterknife.BindView;

// 仿网易云音乐发现页面recyclerView嵌套
// 最外层recyclerView的item，这里主要是处理title，内部嵌套的item将分发下去处理
public class BookHolder extends HorizontalSnapHolder<BookListItem> {

    @BindView(R2.id.simpleTitleIcon)
    ImageView icon;
    @BindView(R2.id.simpleTitle)
    TextView title;
    @BindView(R2.id.actionButton)
    TextView actionButton;

    public BookHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void onBindSnapViewHolder(BookListItem bookListItem) {
        horizonSlideAdapter.setData(bookListItem.getBooks());
        title.setText(bookListItem.getSimpleTitleItem().getMainTitle());
        actionButton.setText(bookListItem.getSimpleTitleItem().getButtonText());
        actionButton.setOnClickListener(v -> RouterUtil.startFragment(bookListItem.getSimpleTitleItem().getButtonAction()));
    }

    @Override
    protected double getShowItemCount() {
        return 3;
    }

    @Override
    protected int getShowPx() {
        return 0;
    }

    @Override
    protected int getItemPosition(BookListItem item) {
        return 0;
    }

    @Override
    protected int getItemOffset(BookListItem item) {
        return 0;
    }

    @Override
    protected CanScrollHorizonRecyclerView getRecyclerView() {
        return itemView.findViewById(R.id.canScrollRecyclerView);
    }

    @Override
    protected int getLeftPadding() {
        return DensityUtil.dip2px(16f);
    }

    @Override
    protected int getDivider() {
        return DensityUtil.dip2px(10f);
    }

    @Override
    protected HorizonSlideAdapter getHorizonAdapter(BookListItem item, int width) {
        return new BookSlideAdapter(item.getBooks(), width);
    }

    @Override
    protected Boolean needSlide(BookListItem item) {
        return false;
    }

    public static class Provider extends BaseMultipleHolder.HolderViewProvider<BookListItem> {

        @Override
        public BaseMultipleHolder<BookListItem> getHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new BookHolder(inflater.inflate(R.layout.title_rv_item, parent, false));
        }
    }
}
