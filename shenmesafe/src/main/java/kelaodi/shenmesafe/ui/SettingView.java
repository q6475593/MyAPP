/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.activity.SettingActivity;

/**
 * Created by Administrator on 2015/4/19.
 */
public class SettingView extends RelativeLayout {


    private View view;
    private TextView tv_setting_title, tv_setting_content;
    private String content, timu;

    public SettingView(Context context) {
        super(context);
        initView(context);
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        //把属性集和我们自己定义的属性集合建立映射关系
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.setting_view_style);
        timu = array.getString(R.styleable.setting_view_style_timu);
        content = array.getString(R.styleable.setting_view_style_content);
        tv_setting_title.setText(timu);
        tv_setting_content.setText(content);
        array.recycle();
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);

    }


    public void initView(Context context) {
        view = inflate(context, R.layout.ui_setting_view, this);
        tv_setting_title = (TextView) findViewById(R.id.tv_settint_title);
        tv_setting_content = (TextView) findViewById(R.id.tv_setting_content);
    }

    /**
     * 返回当前自定义控件checkbox的选中状态
     *
     * @return
     */

    public static boolean Ischecked(CheckBox checkBox) {
        return checkBox.isChecked();
    }

    /**
     * 设置textview字
     */
    public void CheckTextView(TextView tv, String st) {
        tv.setText(st);
    }

    /**
     * 设置自定义空间的勾选状态
     *
     * @param checkBox
     * @param checked
     */
    public void Setchecked(CheckBox checkBox, boolean checked,String st) {
        checkBox.setChecked(checked);
        if (checked == true) {
            CheckTextView(tv_setting_content, st);
        } else if (checked == false) {
            CheckTextView(tv_setting_content, st);
        }
    }

}
