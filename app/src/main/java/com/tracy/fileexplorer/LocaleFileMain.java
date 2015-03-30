package com.tracy.fileexplorer;

import java.io.File;

import com.tracy.fileexplorer.util.FileUtils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 本地文件 首页
 * 
 * @author zhanglei
 *
 */

public class LocaleFileMain extends Activity {
	
	private String extSdCardPath;
	private TextView localefile_bottom_tv;
	private Button localefile_bottom_btn;
	private FileManager bfm;
	
	private final int REQUEST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localefile_main);
		setTitle(getString(R.string.localeFile));
		bfm = FileManager.getInstance();
		localefile_bottom_btn = (Button) findViewById(R.id.localefile_bottom_btn);
		localefile_bottom_tv = (TextView) findViewById(R.id.localefile_bottom_tv);
		localefile_bottom_btn.setText(String.format(getString(R.string.bxfile_choosedCnt),0));
		
		TextView picCnt = (TextView) findViewById(R.id.localefile_pic_cnt);
		picCnt.setText(String.format(getString(R.string.bxfile_media_cnt), bfm.getMediaFilesCnt(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
		TextView musicCnt = (TextView) findViewById(R.id.localefile_music_cnt);
		musicCnt.setText(String.format(getString(R.string.bxfile_media_cnt), bfm.getMediaFilesCnt(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)));
		TextView videoCnt = (TextView) findViewById(R.id.localefile_video_cnt);
		videoCnt.setText(String.format(getString(R.string.bxfile_media_cnt), bfm.getMediaFilesCnt(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)));
		
		extSdCardPath = FileUtils.getExtSdCardPath();
		if(!TextUtils.isEmpty(extSdCardPath)){
			View localefile_sdcard = findViewById(R.id.localefile_sdcard);
			View localefile_sdcard2 = findViewById(R.id.localefile_sdcard2);
			View localefile_extSdcard = findViewById(R.id.localefile_extSdcard);
			localefile_sdcard2.setVisibility(View.VISIBLE);
			localefile_extSdcard.setVisibility(View.VISIBLE);
			localefile_sdcard.setVisibility(View.GONE);
		}
	}
	
	//显示底部 已选文件大小 数目
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		localefile_bottom_tv.setText(bfm.getFilesSizes());
		int cnt = bfm.getFilesCnt();
		localefile_bottom_btn.setText(String.format(getString(R.string.bxfile_choosedCnt), cnt));
		localefile_bottom_btn.setEnabled(cnt>0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, getString(R.string.cancel));
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(0 == item.getItemId()){
			bfm.clear();
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.localefile_music:
			Intent intent5 = new Intent(this,LocaleMediaFileBrowser.class);
			intent5.putExtra("title", getString(R.string.bxfile_music));
			intent5.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent5,REQUEST);
			break;
		case R.id.localefile_video:
			Intent intent6 = new Intent(this,LocaleMediaFileBrowser.class);
			intent6.putExtra("title", getString(R.string.bxfile_video));
			intent6.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent6,REQUEST);
			break;
		case R.id.localefile_picture:
			Intent intent7 = new Intent(this,LocaleFileGallery.class);
			intent7.putExtra("title", getString(R.string.bxfile_image));
			startActivityForResult(intent7,REQUEST);
			break;
		case R.id.localefile_download:
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				Intent intent1 = new Intent(this,LocaleFileBrowser.class);
				intent1.putExtra("title", getString(R.string.bxfile_download));
				String base = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
				String downloadPath = base+"download";
				File file = new File(downloadPath);
				if(!file.exists())
					file.mkdir();
				intent1.putExtra("startPath", downloadPath);
				startActivityForResult(intent1,REQUEST);
			}else{
				Toast.makeText(this, getString(R.string.SDCardNotMounted), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.localefile_ram:
			Intent intent2 = new Intent(this,LocaleFileBrowser.class);
			intent2.putExtra("title", getString(R.string.bxfile_ram));
			intent2.putExtra("startPath", "/");
			startActivityForResult(intent2,REQUEST);
			break;
		case R.id.localefile_sdcard:
		case R.id.localefile_sdcard2:
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				Intent intent3 = new Intent(this,LocaleFileBrowser.class);
				intent3.putExtra("title", getString(R.string.bxfile_sdcard));
				intent3.putExtra("startPath", Environment.getExternalStorageDirectory().getAbsolutePath());
				startActivityForResult(intent3,REQUEST);
			}else{
				Toast.makeText(this, getString(R.string.SDCardNotMounted), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.localefile_extSdcard:
			Intent intent4 = new Intent(this,LocaleFileBrowser.class);
			intent4.putExtra("title", getString(R.string.bxfile_extsdcard));
			intent4.putExtra("startPath", extSdCardPath);
			startActivityForResult(intent4,REQUEST);
			break;
			
		case R.id.localefile_bottom_btn:
			finish();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(REQUEST == requestCode && 1==resultCode){
			bfm.clear();
			finish();
		}else if(REQUEST == requestCode && 2==resultCode){
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}

}
