package com.ws.mesh.mylab.frame;

import android.support.v4.app.FragmentTransaction;

import com.ws.mesh.mylab.BaseFragment;
import com.ws.mesh.mylab.LauncherFragment;
import com.ws.mesh.mylab.MainActivity;
import com.ws.mesh.mylab.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentManager {

    private List<BaseFragment> mFragments = new ArrayList<>();
    //当前显示的Fragment
    private BaseFragment currFragment;
    private MainActivity mainActivity;

    public FragmentManager(MainActivity activity, Class<LauncherFragment> launcher) {
        bindActivity(activity, launcher);
    }

    private void bindActivity(MainActivity activity, Class<? extends BaseFragment> launcher) {
        try {
            mainActivity = activity;

            currFragment = launcher.newInstance();
            mFragments.add(currFragment);
            currFragment.setFragmentAnim(R.anim.framgent_alpha_enter, R.anim.framgent_left_exit);
            switchFragment(currFragment, true);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //跳转到指定Fragment
    public void forward(BaseFragment fragment) {
        ZLog.info("forward " + fragment.getClass().getSimpleName());
        mFragments.add(fragment);
        //UI 处理
        switchFragment(fragment,
                true);
    }

    //结束指定Fragment
    public void finish(BaseFragment fragment) {
        ZLog.info("finish " + fragment.getClass().getSimpleName());
        mFragments.remove(fragment);
        //结束之后 fragment 集合为空则结束程序
        if (!isEmptyList()) {
            //如果显示的是最顶层的fragment 则退出返回到下层fragment
            if (fragment == currFragment) {
                //UI 处理
                switchFragment(mFragments.get(mFragments.size() - 1),
                        false);
            }
        }
    }

    //结束最顶层的fragment
    public void removeTopFragment() {
        mFragments.remove(mFragments.size() - 1);
        ZLog.info("removeTopFragment after remove:\n" + ZLog.printList(mFragments));
        //结束之后 fragment 集合为空则结束程序
        if (!isEmptyList())
            switchFragment(mFragments.get(mFragments.size() - 1),
                    false);
    }

    private boolean isEmptyList() {
        if (mFragments.size() == 0) {
            mainActivity.finish();
            return true;
        }
        return false;
    }

    //获取当前层级
    public int getCurrLevel(BaseFragment fragment) {
        return mFragments.indexOf(fragment);
    }

    public int getCurrSize() {
        return mFragments.size();
    }

    /**
     * 切换当前显示的fragment
     *
     * @param to        当前需要显示的fragment
     * @param isForward 是否是新增界面 决定界面跳转动画
     */
    private void switchFragment(BaseFragment to, boolean isForward) {
        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                to.getEnterAnimId(isForward),
                to.getExitAnimId(isForward));

        if (!to.isAdded()) {
            if (mFragments.size() == 1) {
                transaction.add(R.id.fl_frame, to);
            } else
                transaction.hide(currFragment).add(R.id.fl_frame, to);
        } else {
            transaction.hide(currFragment).show(to);
        }

        currFragment = to;
        transaction.commit();
    }

}
