package kelaodi.shenmesafe.ui;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2015/4/20.
 */
public class TVoffAnimation extends Animation {
    int halfWidth, halfHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置动画时间700
        setDuration(3000);
        //设置动画结束后就结束在动画结束的时刻
        setFillAfter(true);
        //设置view的保存点
        halfWidth = width / 2;
        halfHeight = height / 2;
        //设置动画先加速再减速
        setInterpolator(new AccelerateDecelerateInterpolator());

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        final Matrix matrix = t.getMatrix();

        if (interpolatedTime < 0.8) {
            matrix.preScale(1 + 0.625f * interpolatedTime, 1 - interpolatedTime / 0.8f + 0.01f, halfWidth, halfHeight);
        } else {
            matrix.setScale(7.5f * (1 - interpolatedTime), 0.01f, halfWidth, halfHeight);
        }
    }


}
