package com.ws.mesh.mylab;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ws.mesh.mylab.frame.FragmentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    public FragmentManager fragmentManager;
    @BindView(R.id.tv_text)
    public TextView tvText;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvText.setText(getClass().getSimpleName());
        fragmentManager = new FragmentManager(this, LauncherFragment.class);

        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getCurrSize() == 1
                && System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            return;
        }
        fragmentManager.removeTopFragment();
    }

    //创建悬浮顶端的窗口
    public void onPostCreate() {
        View root = findViewById(android.R.id.content);
        if (root instanceof FrameLayout) {
            FrameLayout content = (FrameLayout) root;
            final ImageView stackView = new ImageView(this);
            stackView.setImageResource(R.drawable.ic_launcher_background);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp2Px(50), dp2Px(100));
            params.gravity = Gravity.END;
            final int dp18 = dp2Px(18);
            params.topMargin = dp18 * 7;
            params.rightMargin = dp18;
            stackView.setLayoutParams(params);
            content.addView(stackView);
        }
    }

    private int dp2Px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
