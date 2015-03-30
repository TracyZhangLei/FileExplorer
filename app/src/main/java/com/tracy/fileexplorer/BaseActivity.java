package com.tracy.fileexplorer;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by tracy on 15/3/30.
 */
public class BaseActivity extends ActionBarActivity {

    //show toast
    protected void showToast(final int strId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, getString(strId), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
