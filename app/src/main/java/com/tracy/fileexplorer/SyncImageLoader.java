package com.tracy.fileexplorer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


/***
 * 图片加载器
 * 
 * @author zhanglei
 *
 */
public class SyncImageLoader {
	
	private String tag = "SyncImageLoader";
	private Object lock = new Object();
	private boolean mAllowLoad = true;
	private boolean firstLoad = true;
	private int mStartLoadLimit = 0;
	private int mStopLoadLimit = 0;
	final Handler handler = new Handler();
	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService pool = Executors.newScheduledThreadPool(20);

	public interface OnImageLoadListener {
		public void onImageLoad(Integer t, Drawable drawable);
		public void onError(Integer t);
	}
	
	public void setLoadLimit(int startLoadLimit,int stopLoadLimit){
		if(startLoadLimit > stopLoadLimit){
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}
	
	public void restore(){
		mAllowLoad = true;
		firstLoad = true;
	}
		
	public void lock(){
		mAllowLoad = false;
		firstLoad = false;
	}
	
	public void unlock(){
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	//从网络url加载
	public void loadImageFromUrl(final Integer mt,final String mImageUrl,
			final OnImageLoadListener mListener) {
		pool.execute((new Runnable() {

			@Override
			public void run() {
				if(!mAllowLoad){
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							Log.e(tag, Log.getStackTraceString(e));
						}
					}
				}
				
				if(mAllowLoad && firstLoad){
					loadImage(mImageUrl, mt, mListener);
				}
				
				if(mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit){
					loadImage(mImageUrl, mt, mListener);
				}
			}
		}));
	}
	
	private void loadImage(final String mImageUrl,final Integer mt,final OnImageLoadListener mListener){
		
		if (imageCache.containsKey(mImageUrl)) {  
            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);  
            final Drawable d = softReference.get();  
            if (d != null) {  
            	handler.post(new Runnable() {
    				@Override
    				public void run() {
    					if(mAllowLoad){
    						mListener.onImageLoad(mt, d);
    					}
    				}
    			});
                return;  
            }  
        }  
		try {
			final Drawable d = loadImageFromUrl(mImageUrl);
			if(d != null){
                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(mAllowLoad){
						mListener.onImageLoad(mt, d);
					}
				}
			});
		} catch (Exception e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					mListener.onError(mt);
				}
			});
			Log.e(tag, Log.getStackTraceString(e));
		}
	}

	private Drawable loadImageFromUrl(String url) throws Exception {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			String FileUrl = Environment.getExternalStorageDirectory()+"/BoXiao/cache/";
			File folder = new File(FileUrl);
			if(!folder.exists()){
				folder.mkdir();
			}
			File f = new File(FileUrl);
			if(f.exists()){
				return Drawable.createFromStream(new FileInputStream(f), "src");
			}
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			DataInputStream in = new DataInputStream(i);
			FileOutputStream out = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int   byteread=0;
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			in.close();
			out.close();
			return Drawable.createFromStream(i, "src");
		}
		else{
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
			return d;
		}
		
	}
	
	
	
	
	
	
	//加载本地图片
	public void loadDiskImage(final Integer t, final String localeImagePath,
			final OnImageLoadListener listener) {
		pool.execute((new Runnable() {
			@Override
			public void run() {
				if(!mAllowLoad){
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							Log.e(tag, Log.getStackTraceString(e));
						}
					}
				}
				
				if(mAllowLoad && firstLoad){
					loadLocaleImage(localeImagePath, t, listener);
				}
				
				if(mAllowLoad && t <= mStopLoadLimit && t >= mStartLoadLimit){
					loadLocaleImage(localeImagePath, t, listener);
				}
			}
		}));
	}
	
	private void loadLocaleImage(final String localeImagePath,final Integer mt,final OnImageLoadListener mListener){
		if (imageCache.containsKey(localeImagePath)) {  
            SoftReference<Drawable> softReference = imageCache.get(localeImagePath);  
            final Drawable d = softReference.get();  
            if (d != null) {  
            	handler.post(new Runnable() {
    				@Override
    				public void run() {
    					if(mAllowLoad){
    						mListener.onImageLoad(mt, d);
    					}
    				}
    			});
                return;  
            }  
        }  
		try {
			final Drawable d = loadImageFromDisk(localeImagePath , 100);
			if(d != null){
                imageCache.put(localeImagePath, new SoftReference<Drawable>(d));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(mAllowLoad){
						mListener.onImageLoad(mt, d);
					}
				}
			});
		} catch (Exception e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					mListener.onError(mt);
				}
			});
			Log.e(tag, Log.getStackTraceString(e));
		}
	}
	
	public Drawable loadImageFromDisk(String path , int requiredSize) throws Exception {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return decodeFile(path , requiredSize);
		else
			return null;
	}
	
	private Drawable decodeFile(String path , int requiredSize) throws FileNotFoundException {
		// Decode image size
		File f = new File(path);
		if(!f.exists())
			return null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(new FileInputStream(f), null, o);

		// The new size we want to scale to

		// Find the correct scale value. It should be the power of 2.
		int scale = 1;
		while (o.outWidth / scale / 2 >= requiredSize
				&& o.outHeight / scale / 2 >= requiredSize)
			scale *= 2;

		// Decode with inSampleSize
//		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o.inJustDecodeBounds = false;
		o.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f),
				null, o);
		o = null;
		return new BitmapDrawable(bitmap);
    }
}
