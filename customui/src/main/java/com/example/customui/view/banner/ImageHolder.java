package com.example.customui.view.banner;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.comment.app.ThisApp;
import com.example.customui.R;

/**
 * @author NewBies
 * @date 2018/9/17
 */

public class ImageHolder extends Holder<String> {

    private AppCompatImageView imageView = null;

    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    public ImageHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.image);
        //设置图片加载模式为铺满，具体请搜索 ImageView.ScaleType.FIT_XY
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void updateUI(String data) {
        //从网络加载图片
        Glide.with(ThisApp.getApplicationContext())
                //url
                .load(data)
                .apply(BANNER_OPTIONS)
                .into(imageView);
    }
}
