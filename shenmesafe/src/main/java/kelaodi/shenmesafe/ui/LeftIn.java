package kelaodi.shenmesafe.ui;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2015/4/24.
 */
public class LeftIn extends Animation {
    int Width, Height;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(1500);
        setFillAfter(true);
        Width = width;
        Height = height;
        setInterpolator(new AccelerateDecelerateInterpolator());

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        //图片从左面进入
        final Matrix matrix = t.getMatrix();
        matrix.preTranslate(-Width, 0);
        matrix.setTranslate(interpolatedTime * Width, 0);
    }
}
