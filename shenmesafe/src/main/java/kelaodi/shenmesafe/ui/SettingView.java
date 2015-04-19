/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/19.
 */
public class SettingView extends RelativeLayout {
    public SettingView(Context context) {
        super(context);
        inflate(context, R.layout.ui_setting_view, this);
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ui_setting_view, this);

    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.ui_setting_view, this);

    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.ui_setting_view, this);

    }
}
