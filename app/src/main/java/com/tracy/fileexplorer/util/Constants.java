package com.tracy.fileexplorer.util;

import android.os.Environment;

public class Constants {

	// 文件下载目录
	public static final String FILE_BASEPATH = Environment.getExternalStorageDirectory() + "/tracyZhang/";// 基本目录
	public static final String AVATAR_SAVEPATH = FILE_BASEPATH + "avatar/"; // 头像目录
	public static final String DOWNLOAD_PATH = FILE_BASEPATH + "download/"; // 下载目录
	public static final String FILERECV_PATH = FILE_BASEPATH + "fileRecv/"; // 接收文件目录
	
}
