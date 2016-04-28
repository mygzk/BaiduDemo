package com.example.demo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by guozhk on 16-4-25.
 */
public class BaseActivity extends FragmentActivity {
    public static String TAG = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TAG = this.getLocalClassName();
    }


}
