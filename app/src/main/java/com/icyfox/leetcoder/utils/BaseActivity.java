package com.icyfox.leetcoder.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by icyfox on 2015/3/29.
 */
public class BaseActivity extends Activity {

    public void shortToast(CharSequence msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void longToast(CharSequence msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
