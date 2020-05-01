package com.example.comment.util;

import android.graphics.Bitmap;

import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Target;

public class PaletteUtil {

    public interface ColorGetListener {
        void onColorGet(int color);
    }

    public static void getMainColor(Bitmap bitmap, final Target target, final ColorGetListener colorGetListener) {
        // 异步方式获取
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {

                if (palette != null) {
                    Palette.Swatch swatch = target == null ? palette.getDominantSwatch() : palette.getSwatchForTarget(target);
                    if (swatch == null) {
                        colorGetListener.onColorGet(0xffffffff);
                    } else if (colorGetListener != null) {
                        colorGetListener.onColorGet(swatch.getRgb());
                    }
                }
            }
        });
    }
}
