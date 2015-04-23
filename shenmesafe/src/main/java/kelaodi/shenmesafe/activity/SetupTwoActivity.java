package kelaodi.shenmesafe.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.ui.RightOutLeftIn;

/**
 * Created by Administrator on 2015/4/23.
 */
public class SetupTwoActivity extends BaseSetActivity {
    private View setup_two;
    private Button button_pre;
    private RightOutLeftIn rightOutLeftIn = new RightOutLeftIn();

    @Override
    public void initView() {

        setContentView(R.layout.activity_setup_two);
        setup_two = findViewById(R.id.setup_two);
        button_pre = (Button) findViewById(R.id.button_pre);
    }

    @Override
    public void setupView() {
        button_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightOutLeftIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(SetupTwoActivity.this, SetupOneActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }

    @Override
    public void showNext() {

    }

    @Override
    public void showPrevious() {

    }
}
