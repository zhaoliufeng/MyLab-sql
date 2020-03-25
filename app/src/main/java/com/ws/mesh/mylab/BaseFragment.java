package com.ws.mesh.mylab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ws.mesh.mylab.frame.JumpCast;
import com.ws.mesh.mylab.frame.FragmentManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private FragmentManager fragmentManager;
    private int createEnterAnimId = R.anim.framgent_left_enter;
    private int createExitAnimId = R.anim.framgent_left_exit;
    private int showEnterAnimId = R.anim.framgent_right_enter;
    private int showExitAnimId = R.anim.framgent_right_exit;

    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract void setUpView();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);

        fragmentManager = getMainActivity().fragmentManager;

        setUpView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //anim
    public void setFragmentAnim(int createEnterAnimId, int createExitAnimId) {
        this.createEnterAnimId = createEnterAnimId;
        this.createExitAnimId = createExitAnimId;
    }

    protected void setFragmentAnim(int createEnterAnimId, int createExitAnimId, int showEnterAnimId, int showExitAnimId) {
        this.createEnterAnimId = createEnterAnimId;
        this.createExitAnimId = createExitAnimId;
        this.showEnterAnimId = showEnterAnimId;
        this.showExitAnimId = showExitAnimId;
    }

    public int getEnterAnimId(boolean isForward) {
        return isForward ? createEnterAnimId : showEnterAnimId;
    }

    public int getExitAnimId(boolean isForward) {
        return isForward ? createExitAnimId : showExitAnimId;
    }
    //anim end

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public void push(JumpCast forward) {
        fragmentManager.forward(forward.fragment);
        if (forward.finish)
            finish();
    }

    protected void finish() {
        fragmentManager.finish(this);
    }

    protected int getLevlel() {
        return fragmentManager.getCurrLevel(this);
    }

}
