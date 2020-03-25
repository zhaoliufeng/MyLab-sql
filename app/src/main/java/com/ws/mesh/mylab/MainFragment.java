package com.ws.mesh.mylab;

import com.ws.mesh.mylab.frame.JumpCast;

import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setUpView() {

    }

    @OnClick(R.id.btn_back)
    public void onBack() {
        push(new JumpCast.Builder()
                .finishCurrFragment(false)
                .setFragment(new SettingFragment())
                .build());
    }
}
