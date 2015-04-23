package kelaodi.shenmesafe.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.ui.LeftOutRightIn;
import kelaodi.shenmesafe.ui.TVonAnimation;

/**
 * Created by Administrator on 2015/4/23.
 */
public class SetupOneActivity extends BaseSetActivity {
    private View setupone;
    private Button button_next;
    private TVonAnimation tVonAnimation = new TVonAnimation();
    private LeftOutRightIn leftOutRightIn = new LeftOutRightIn();

    @Override
    public void initView() {
        setContentView(R.layout.activity_setup_one);
        setupone = findViewById(R.id.setup_one);
        button_next = (Button) findViewById(R.id.button_next);
    }

    @Override
    public void setupView() {
        leftOutRightIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SetupOneActivity.this, SetupTwoActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        setupone.setAnimation(tVonAnimation);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setupone.startAnimation(leftOutRightIn);
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
