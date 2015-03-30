package com.tracy.fileexplorer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;

public class FEApplication extends Application {

	private ExecutorService es = Executors.newFixedThreadPool(3);

	// app对外 执行任务入口
	public void execRunnable(Runnable r) {
		if (!es.isShutdown()) {
			es.execute(r);
		}
	}

}
