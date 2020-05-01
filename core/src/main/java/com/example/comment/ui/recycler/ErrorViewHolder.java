package com.example.comment.ui.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.customui.R;
import com.example.customui.R2;

import butterknife.BindView;

public class ErrorViewHolder extends BaseMultipleHolder<String> {

    @BindView(R2.id.text)
    TextView text;

    public ErrorViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBindBaseViewHolder(String s) {
        text.setText(s);
    }

    public static class ErrorViewHolderProvider extends HolderViewProvider {

        @Override
        public BaseMultipleHolder getHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new ErrorViewHolder(inflater.inflate(R.layout.text_item, parent, false));
        }
    }
}
