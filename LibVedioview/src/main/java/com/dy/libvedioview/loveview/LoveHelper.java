package com.dy.libvedioview.loveview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * Descripty:
 * Auth:  邓渊  dymh21342@163.com
 * Date: 2018/1/30.11:38
 */

public class LoveHelper {
    public static final int MAX_LOVE_COUNT = 20;

    private int index = 0;//当前显示的view
    private ArrayList<LoveItem> listLoves;//存放所有的View
    private LoveAnimationHelper animationHelper;

    public LoveHelper(final ViewGroup viewGroup) {
        animationHelper = new LoveAnimationHelper();

        listLoves = new ArrayList<>();
        for (int i = 0; i < MAX_LOVE_COUNT; i++) {
            LoveItem item = new LoveItem(viewGroup);
            item.setRandomImage();
            viewGroup.addView(item.imageLove);
            listLoves.add(item);
        }

        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                animationHelper.setMaxXY(viewGroup.getWidth(), viewGroup.getHeight());
            }
        });
    }

    /**
     * Descripty:
     * Auth: damao  2018/1/30 16:35
     *
     * @param viewItem 点赞的位置
     */
    public void showLoveView(View viewItem) {
        final LoveItem item = listLoves.get(++index % MAX_LOVE_COUNT);
        if (item.isShow) {
            return;
        }

        animationHelper.setStartPoint(viewItem.getX(), viewItem.getY());

        AnimatorSet set = animationHelper.getAnimatorSet(item.imageLove);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.isShow = false;
                item.imageLove.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                item.isShow = true;
                item.imageLove.setVisibility(View.VISIBLE);
            }
        });
        set.start();
    }

}
