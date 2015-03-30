package com.tracy.fileexplorer;

import java.util.List;

import com.tracy.fileexplorer.TFile.MimeType;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 本地文件adapter
 * 
 * @author zhanglei
 *
 */
public class LocaleFileAdapter extends BaseAdapter {
	
	private FileManager bfm;
	private List<TFile> data;
	private Context cxt;
	private List<TFile> choosedFiles;
	int w;
	private SyncImageLoader syncImageLoader;
	private SyncImageLoader.OnImageLoadListener imageLoadListener;
	
	public LocaleFileAdapter(List<TFile> data, Context cxt , SyncImageLoader syncImageLoader , SyncImageLoader.OnImageLoadListener imageLoadListener) {
		super();
		this.data = data;
		this.cxt = cxt;
		this.syncImageLoader = syncImageLoader;
		this.imageLoadListener = imageLoadListener;
		bfm = FileManager.getInstance();
		choosedFiles = bfm.getChoosedFiles();
		w = cxt.getResources().getDimensionPixelSize(R.dimen.view_36dp);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(null!=data)
			return data.size();
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//目录：显示目录view;文件：显示文件view及勾选状况
	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(null == view){
			view = LayoutInflater.from(cxt).inflate(R.layout.locale_file_item, null);
		}
		View dirView = view.findViewById(R.id.dirRl);
		TextView dirName = (TextView) view.findViewById(R.id.dirName);
		
		View fileView = view.findViewById(R.id.fileLl);
		
		view.setTag(pos);
		TFile bxFile = data.get(pos);
		if(bxFile.isDir()){
			dirView.setVisibility(View.VISIBLE);
			dirName.setText(bxFile.getFileName());
			fileView.setVisibility(View.GONE);
		}else{
			dirView.setVisibility(View.GONE);
			fileView.setVisibility(View.VISIBLE);
			
			CheckBox fileCheckBox = (CheckBox) view.findViewById(R.id.fileCheckBox);
			ImageView fileType = (ImageView) view.findViewById(R.id.fileType);
			TextView fileName = (TextView) view.findViewById(R.id.fileName);
			TextView fileSize = (TextView) view.findViewById(R.id.fileSize);
			TextView fileModifyDate = (TextView) view.findViewById(R.id.fileModifyDate);
			fileName.setText(bxFile.getFileName());
			fileSize.setText(bxFile.getFileSizeStr());
			fileModifyDate.setText(bxFile.getLastModifyTimeStr());
			if(bxFile.getMimeType().equals(MimeType.IMAGE)){
				fileType.setImageResource(R.drawable.bxfile_file_default_pic);
				if(null!=syncImageLoader && null!=imageLoadListener)
					syncImageLoader.loadDiskImage(pos, bxFile.getFilePath(), imageLoadListener);
			}else{
				fileType.setImageResource(bfm.getMimeDrawable(bxFile.getMimeType()));
			}
			fileCheckBox.setChecked(choosedFiles.contains(bxFile));//是否勾选chechBox
		}
		return view;
	}
	
}
