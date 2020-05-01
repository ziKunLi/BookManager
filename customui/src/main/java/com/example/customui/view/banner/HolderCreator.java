package com.example.customui.view.banner;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.customui.R;

/**
 *
 * @author NewBies
 * @date 2019/9/16
 */

public class HolderCreator implements CBViewHolderCreator{

    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.image_banner_item;
    }
}
