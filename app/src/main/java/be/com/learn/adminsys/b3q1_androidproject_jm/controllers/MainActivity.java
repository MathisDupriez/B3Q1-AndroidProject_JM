package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.view.MainViewController;


public class MainActivity extends AppCompatActivity {

    private MainViewController mmainViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // creation de l'instance de MainViewController
        mmainViewController = new MainViewController(this);
        setContentView(mmainViewController.getRootView());

        // test action : change bg color
        mmainViewController.getRootView().setBackgroundColor(Color.BLUE);
    }
}