package be.com.learn.adminsys.b3q1_androidproject_jm.view;


import android.content.Context;
import android.widget.FrameLayout;

import java.io.Serializable;

public class MainViewController {

    private FrameLayout rootView; // root view

    public MainViewController(Context context) {
        rootView = new FrameLayout(context);
        // New elements to be added here
    }

    public FrameLayout getRootView() {
        return rootView;
    }
}
