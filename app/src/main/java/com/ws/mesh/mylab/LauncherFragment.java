package com.ws.mesh.mylab;

import android.widget.TextView;

import com.ws.mesh.mylab.frame.JumpCast;

import butterknife.BindView;
import butterknife.OnClick;

public class LauncherFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView tvText;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launcher;
    }

    @Override
    protected void setUpView() {


    }

    @OnClick(R.id.btn_turn)
    public void turnClick() {
//        tvText.setText("111");
        push(new JumpCast.Builder()
                .setFragment(new MainFragment())
                .finishCurrFragment(false)
                .build());
    }
}
