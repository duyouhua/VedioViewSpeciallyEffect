package com.dy.libvedioview.loveview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.Random;

/**
 * Descripty:
 * Auth:  邓渊  dymh21342@163.com
 * Date: 2018/1/30.14:05
 */

public class LoveAnimationHelper {

    private Interpolator[] interpolators = new Interpolator[4];

    private int mWidth = 500;
    private int mHeight = 1000;//心显示的最大宽度和最大高度
    private float oneWidth, oneHeight;


    private PointF point0;//起点坐标

    public LoveAnimationHelper() {
        // 插值器
//        interpolators[0] = new DecelerateInterpolator(); // 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
//        interpolators[1] = new DecelerateInterpolator();  // 在动画开始的地方速率改变比较慢，然后开始加速
//        interpolators[2] = new DecelerateInterpolator(); // 在动画开始的地方快然后慢
//        interpolators[3] = new DecelerateInterpolator();  // 以常量速率改变
        interpolators[0] = new AccelerateDecelerateInterpolator(); // 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        interpolators[1] = new AccelerateInterpolator();  // 在动画开始的地方速率改变比较慢，然后开始加速
        interpolators[2] = new DecelerateInterpolator(); // 在动画开始的地方快然后慢
        interpolators[3] = new LinearInterpolator();  // 以常量速率改变
        point0 = new PointF();
        oneWidth = mWidth / 4;
        oneHeight = mHeight / 5;
    }

    public void setStartPoint(float x, float y) {
        this.point0.set(x, y);
    }


    public void setMaxXY(int max_X, int max_Y) {
        this.mWidth = max_X;
        this.mHeight = max_Y;
    }

    /**
     * 获取动画集合
     *
     * @param iv
     */
    public AnimatorSet getAnimatorSet(ImageView iv) {
        // 1.alpha动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1f);

        // 2.缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.1f, 1f);

        // 动画集合
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(interpolators[1]);
        set.playTogether(alpha, scaleX, scaleY);
//        return set;

//        // 贝塞尔曲线动画
        ValueAnimator bzier = getBzierAnimator(iv);
//
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(set, bzier);
//        set2.play(bzier);
////        set2.playSequentially(set, bzier);
//        set2.setTarget(iv);
//
        return set2;
    }


    /**
     * 贝塞尔动画
     */
    private ValueAnimator getBzierAnimator(final ImageView iv) {
        PointF[] PointFs = getPointFs(iv); // 4个点的坐标
        BasEvaluator evaluator = new BasEvaluator(PointFs[1], PointFs[2]);
        ValueAnimator valueAnim = ValueAnimator.ofObject(evaluator, PointFs[0], PointFs[3]);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                iv.setX(p.x);
                iv.setY(p.y);
                iv.setAlpha(1 - animation.getAnimatedFraction()); // 透明度
            }
        });
        valueAnim.setTarget(iv);
        valueAnim.setDuration(3000);
//        valueAnim.setInterpolator(interpolators[new Random().nextInt(4)]);
        valueAnim.setInterpolator(interpolators[3]);
        return valueAnim;
    }

    private PointF[] getPointFs(ImageView iv) {
        PointF[] PointFs = new PointF[4];
        PointFs[0] = point0;

        PointFs[1] = new PointF(); // p1
        PointFs[1].x = PointFs[0].x + (new Random().nextFloat() - 0.5f) * oneWidth;
        PointFs[1].y = mHeight-oneHeight+new Random().nextFloat() * oneHeight/ 2 ;

        PointFs[2] = new PointF(); // p2
        PointFs[2].x = new Random().nextInt(mWidth);
        PointFs[2].y = new Random().nextFloat() * mHeight / 2f;

        PointFs[3] = new PointF(); // p3
        PointFs[3].x = new Random().nextFloat() * mWidth;
        PointFs[3].y = 0;
        return PointFs;
    }


    /**
     * @author 刘洋巴金
     * @date 2017-4-27
     * <p>
     * 估值器，计算路径
     */
    private class BasEvaluator implements TypeEvaluator<PointF> {

        private PointF p1;
        private PointF p2;

        public BasEvaluator(PointF p1, PointF p2) {
            super();
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public PointF evaluate(float fraction, PointF p0, PointF p3) {
            PointF pointf = new PointF();
            // 贝塞尔曲线公式  p0*(1-t)^3 + 3p1*t*(1-t)^2 + 3p2*t^2*(1-t) + p3^3
            pointf.x = p0.x * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + 3 * p1.x * fraction * (1 - fraction) * (1 - fraction)
                    + 3 * p2.x * fraction * fraction * (1 - fraction)
                    + p3.x * fraction * fraction * fraction;
            pointf.y = p0.y * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + 3 * p1.y * fraction * (1 - fraction) * (1 - fraction)
                    + 3 * p2.y * fraction * fraction * (1 - fraction)
                    + p3.y * fraction * fraction * fraction;
            return pointf;
        }
    }
}
