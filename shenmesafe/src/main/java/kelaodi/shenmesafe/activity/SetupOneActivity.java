package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import java.util.ArrayList;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.constant.constant;
import kelaodi.shenmesafe.ui.LeftOut;
import kelaodi.shenmesafe.ui.LeftIn;
import kelaodi.shenmesafe.ui.RightIn;
import kelaodi.shenmesafe.ui.TVoffAnimation;
import kelaodi.shenmesafe.ui.TVonAnimation;

/**
 * Created by Administrator on 2015/4/23.
 */
public class SetupOneActivity extends Activity {
    private View setup_one, setup_two, setup_three, setup_four;
    private ViewPager viewPager;
    private ArrayList<View> viewContainter = new ArrayList<>();
    private TVonAnimation tVonAnimation = new TVonAnimation();
    private Button button_one_next, button_two_next, button_two_pre, button_three_next,
            button_three_pre, button_four_next, button_four_pre;
    private TVoffAnimation tvoffAnimation = new TVoffAnimation();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        viewPager.setAnimation(tVonAnimation);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        initview();
        initdate();
    }


    private void initview() {
        setup_one = LayoutInflater.from(this).inflate(R.layout.activity_setup_one, null);
        setup_two = LayoutInflater.from(this).inflate(R.layout.activity_setup_two, null);
        setup_three = LayoutInflater.from(this).inflate(R.layout.activity_setup_three, null);
        setup_four = LayoutInflater.from(this).inflate(R.layout.activity_setup_four, null);
        button_one_next = (Button) setup_one.findViewById(R.id.button_next);
        button_two_next = (Button) setup_two.findViewById(R.id.button_next);
        button_two_pre = (Button) setup_two.findViewById(R.id.button_pre);
        button_three_next = (Button) setup_three.findViewById(R.id.button_next);
        button_three_pre = (Button) setup_three.findViewById(R.id.button_pre);
        button_four_next = (Button) setup_four.findViewById(R.id.button_next);
        button_four_pre = (Button) setup_four.findViewById(R.id.button_pre);
        viewContainter.add(setup_one);
        viewContainter.add(setup_two);
        viewContainter.add(setup_three);
        viewContainter.add(setup_four);
    }

    private void initdate() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                (container).removeView(viewContainter.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                (container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });


        button_one_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        button_two_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);

            }
        });
        button_three_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
        button_four_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvoffAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("Issetup", true);
                        editor.commit();
                        Intent intent = new Intent(SetupOneActivity.this, HomeActivity.class);
                        Bundle bd = new Bundle();
                        bd.putInt("fromwhere", constant.ONLY);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                viewPager.startAnimation(tvoffAnimation);
            }
        });

        button_two_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        button_three_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        button_four_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
    }
}
