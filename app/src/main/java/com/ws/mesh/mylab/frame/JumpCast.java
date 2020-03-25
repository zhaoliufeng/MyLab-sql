package com.ws.mesh.mylab.frame;

import com.ws.mesh.mylab.BaseFragment;

public class JumpCast {
    public boolean finish;
    public BaseFragment fragment;

    private JumpCast(Builder builder) {
        this.finish = builder.finish;
        this.fragment = builder.fragment;
    }

    public static class Builder {
        private boolean finish;
        private BaseFragment fragment;

        public Builder(){

        }

        public Builder setFragment(BaseFragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder finishCurrFragment(boolean finish) {
            this.finish = finish;
            return this;
        }

        public JumpCast build() {
            return new JumpCast(this);
        }
    }
}
