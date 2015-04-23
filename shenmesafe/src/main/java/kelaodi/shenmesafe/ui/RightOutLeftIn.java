package kelaodi.shenmesafe.ui;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2015/4/24.
 */
public class RightOutLeftIn extends Animation {
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

        final Matrix matrix = t.getMatrix();

        matrix.setTranslate(-Width, 0);
    }
}
