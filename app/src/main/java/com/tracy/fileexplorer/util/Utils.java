package com.tracy.fileexplorer.util;

import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;

public class Utils {
	
	/**
	 * @param cxt
	 * @return 屏幕宽
	 */
	public static int getScreenWidth(Activity cxt) {
		WindowManager m = cxt.getWindowManager();
		Display d = m.getDefaultDisplay();
		return d.getWidth();
	}

}
