package kelaodi.shenmesafe.ui;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2015/4/24.
 */
public class LeftOutRightIn extends Animation {
    int Width, Height;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置动画时间700
        setDuration(1500);
        //设置动画结束后就结束在动画结束的时刻
        setFillAfter(true);
        //设置view的保存点
        Width = width;
        Height = height;
        //设置动画先加速再减速
        setInterpolator(new AccelerateDecelerateInterpolator());

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        final Matrix matrix = t.getMatrix();
        if (0 <= interpolatedTime && interpolatedTime <= 1) {
            matrix.setTranslate(-interpolatedTime * Width, 0);
        }

    }

}
