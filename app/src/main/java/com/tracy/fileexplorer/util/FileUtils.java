package com.tracy.fileexplorer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * 文件操作工具包
 * 
 * @author tracyZhang
 * 
 */
public class FileUtils {
	static String tag = "FileUtils";

//	/**
//	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
//	 * 
//	 * @param context
//	 * @param msg
//	 */
//	public static void write(Context context, String fileName, String content) {
//		if (content == null)
//			content = "";
//
//		try {
//			FileOutputStream fos = context.openFileOutput(fileName,
//					Context.MODE_PRIVATE);
//			fos.write(content.getBytes());
//
//			fos.close();
//		} catch (Exception e) {
//			Log.e(tag, Log.getStackTraceString(e));
//		}
//	}

//	/**
//	 * 读取文本文件
//	 * 
//	 * @param context
//	 * @param fileName
//	 * @return
//	 */
//	public static String read(Context context, String fileName) {
//		try {
//			FileInputStream in = context.openFileInput(fileName);
//			return readInStream(in);
//		} catch (Exception e) {
//			Log.e(tag, Log.getStackTraceString(e));
//		}
//		return "";
//	}

//	private static String readInStream(FileInputStream inStream) {
//		try {
//			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//			byte[] buffer = new byte[512];
//			int length = -1;
//			while ((length = inStream.read(buffer)) != -1) {
//				outStream.write(buffer, 0, length);
//			}
//
//			outStream.close();
//			inStream.close();
//			return outStream.toString();
//		} catch (IOException e) {
//			Log.i("FileTest", e.getMessage());
//		}
//		return null;
//	}

	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

//	/**
//	 * 向手机写图片
//	 * 
//	 * @param buffer
//	 * @param folder
//	 * @param fileName
//	 * @return
//	 */
//	public static boolean writeFile(byte[] buffer, String folder,
//			String fileName) {
//		boolean writeSucc = false;
//
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				android.os.Environment.MEDIA_MOUNTED);
//
//		String folderPath = "";
//		if (sdCardExist) {
//			folderPath = Environment.getExternalStorageDirectory()
//					+ File.separator + folder + File.separator;
//		} else {
//			writeSucc = false;
//		}
//
//		File fileDir = new File(folderPath);
//		if (!fileDir.exists()) {
//			fileDir.mkdirs();
//		}
//
//		File file = new File(folderPath + fileName);
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream(file);
//			out.write(buffer);
//			writeSucc = true;
//		} catch (Exception e) {
//			Log.e(tag, Log.getStackTraceString(e));
//		} finally {
//			try {
//				out.close();
//			} catch (IOException e) {
//				Log.e(tag, Log.getStackTraceString(e));
//			}
//		}
//
//		return writeSucc;
//	}

//	/**
//	 * 根据文件绝对路径获取文件名
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//	public static String getFileName(String filePath) {
//		if (TextUtils.isEmpty(filePath))
//			return "";
//		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
//	}

//	/**
//	 * 根据文件的绝对路径获取文件名但不包含扩展名
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//	public static String getFileNameNoFormat(String filePath) {
//		if (TextUtils.isEmpty(filePath)) {
//			return "";
//		}
//		int point = filePath.lastIndexOf('.');
//		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
//				point);
//	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (TextUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

//	/**
//	 * 获取文件大小
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//	public static long getFileSize(String filePath) {
//		long size = 0;
//
//		File file = new File(filePath);
//		if (file != null && file.exists()) {
//			size = file.length();
//		}
//		return size;
//	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *            字节
	 * @return
	 */
	public static String getFileSizeStr(long size) {
		if (size <= 0)
			return "0.0B";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

//	/**
//	 * 转换文件大小
//	 * 
//	 * @param fileS
//	 * @return B/KB/MB/GB
//	 */
//	public static String formatFileSize(long fileS) {
//		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
//		String fileSizeString = "";
//		if (fileS < 1024) {
//			fileSizeString = df.format((double) fileS) + "B";
//		} else if (fileS < 1048576) {
//			fileSizeString = df.format((double) fileS / 1024) + "KB";
//		} else if (fileS < 1073741824) {
//			fileSizeString = df.format((double) fileS / 1048576) + "MB";
//		} else {
//			fileSizeString = df.format((double) fileS / 1073741824) + "G";
//		}
//		return fileSizeString;
//	}

//	/**
//	 * 获取目录文件大小
//	 * 
//	 * @param dir
//	 * @return
//	 */
//	public static long getDirSize(File dir) {
//		if (dir == null) {
//			return 0;
//		}
//		if (!dir.isDirectory()) {
//			return 0;
//		}
//		long dirSize = 0;
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			if (file.isFile()) {
//				dirSize += file.length();
//			} else if (file.isDirectory()) {
//				dirSize += file.length();
//				dirSize += getDirSize(file); // 递归调用继续统计
//			}
//		}
//		return dirSize;
//	}

//	/**
//	 * 获取目录文件个数
//	 * 
//	 * @param f
//	 * @return
//	 */
//	public long getFileList(File dir) {
//		long count = 0;
//		File[] files = dir.listFiles();
//		count = files.length;
//		for (File file : files) {
//			if (file.isDirectory()) {
//				count = count + getFileList(file);// 递归
//				count--;
//			}
//		}
//		return count;
//	}

//	public static byte[] toBytes(InputStream in) throws IOException {
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		int ch;
//		while ((ch = in.read()) != -1) {
//			out.write(ch);
//		}
//		byte buffer[] = out.toByteArray();
//		out.close();
//		return buffer;
//	}

//	/**
//	 * 检查文件是否存在
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static boolean checkFileExists(String name) {
//		boolean status;
//		if (!name.equals("")) {
//			File path = Environment.getExternalStorageDirectory();
//			File newPath = new File(path.toString() + name);
//			status = newPath.exists();
//		} else {
//			status = false;
//		}
//		return status;
//	}

//	/**
//	 * 检查路径是否存在
//	 * 
//	 * @param path
//	 * @return
//	 */
//	public static boolean checkFilePathExists(String path) {
//		return new File(path).exists();
//	}

//	/**
//	 * 计算SD卡的剩余空间
//	 * 
//	 * @return 返回-1，说明没有安装sd卡
//	 */
//	public static long getFreeDiskSpace() {
//		String status = Environment.getExternalStorageState();
//		long freeSpace = 0;
//		if (status.equals(Environment.MEDIA_MOUNTED)) {
//			try {
//				File path = Environment.getExternalStorageDirectory();
//				StatFs stat = new StatFs(path.getPath());
//				long blockSize = stat.getBlockSize();
//				long availableBlocks = stat.getAvailableBlocks();
//				freeSpace = availableBlocks * blockSize / 1024;
//			} catch (Exception e) {
//				Log.e(tag, Log.getStackTraceString(e));
//			}
//		} else {
//			return -1;
//		}
//		return (freeSpace);
//	}

//	/**
//	 * 新建目录
//	 * 
//	 * @param directoryName
//	 * @return
//	 */
//	public static boolean createDirectory(String directoryName) {
//		boolean status;
//		if (!directoryName.equals("")) {
//			File path = Environment.getExternalStorageDirectory();
//			File newPath = new File(path.toString() + directoryName);
//			status = newPath.mkdir();
//			status = true;
//		} else
//			status = false;
//		return status;
//	}

//	/**
//	 * 检查是否安装SD卡
//	 * 
//	 * @return
//	 */
//	public static boolean checkSaveLocationExists() {
//		String sDCardStatus = Environment.getExternalStorageState();
//		boolean status;
//		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
//			status = true;
//		} else
//			status = false;
//		return status;
//	}

//	/**
//	 * 删除目录(包括：目录里的所有文件)
//	 * 
//	 * @param fileName
//	 * @return
//	 */
//	public static boolean deleteDirectory(String fileName) {
//		boolean status;
//		SecurityManager checker = new SecurityManager();
//
//		if (!fileName.equals("")) {
//
//			File path = Environment.getExternalStorageDirectory();
//			File newPath = new File(path.toString() + fileName);
//			checker.checkDelete(newPath.toString());
//			if (newPath.isDirectory()) {
//				String[] listfile = newPath.list();
//				// delete all files within the specified directory and then
//				// delete the directory
//				try {
//					for (int i = 0; i < listfile.length; i++) {
//						File deletedFile = new File(newPath.toString() + "/"
//								+ listfile[i].toString());
//						deletedFile.delete();
//					}
//					newPath.delete();
//					Log.i("DirectoryManager deleteDirectory", fileName);
//					status = true;
//				} catch (Exception e) {
//					Log.e(tag, Log.getStackTraceString(e));
//					status = false;
//				}
//
//			} else
//				status = false;
//		} else
//			status = false;
//		return status;
//	}

//	/**
//	 * 删除文件
//	 * 
//	 * @param fileName
//	 * @return
//	 */
//	public static boolean deleteFile(String fileName) {
//		boolean status;
//		SecurityManager checker = new SecurityManager();
//
//		if (!fileName.equals("")) {
//
//			File path = Environment.getExternalStorageDirectory();
//			File newPath = new File(path.toString() + fileName);
//			checker.checkDelete(newPath.toString());
//			if (newPath.isFile()) {
//				try {
//					Log.i("DirectoryManager deleteFile", fileName);
//					newPath.delete();
//					status = true;
//				} catch (SecurityException se) {
//					sLog.e(tag, Log.getStackTraceString(e));
//					status = false;
//				}
//			} else
//				status = false;
//		} else
//			status = false;
//		return status;
//	}

//	/**
//	 * 删除空目录
//	 * 
//	 * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
//	 * 
//	 * @return
//	 */
//	public static int deleteBlankPath(String path) {
//		File f = new File(path);
//		if (!f.canWrite()) {
//			return 1;
//		}
//		if (f.list() != null && f.list().length > 0) {
//			return 2;
//		}
//		if (f.delete()) {
//			return 0;
//		}
//		return 3;
//	}

//	/**
//	 * 重命名
//	 * 
//	 * @param oldName
//	 * @param newName
//	 * @return
//	 */
//	public static boolean reNamePath(String oldName, String newName) {
//		File f = new File(oldName);
//		return f.renameTo(new File(newName));
//	}

//	/**
//	 * 删除文件
//	 * 
//	 * @param filePath
//	 */
//	public static boolean deleteFileWithPath(String filePath) {
//		SecurityManager checker = new SecurityManager();
//		File f = new File(filePath);
//		checker.checkDelete(filePath);
//		if (f.isFile()) {
//			Log.i("DirectoryManager deleteFile", filePath);
//			f.delete();
//			return true;
//		}
//		return false;
//	}

//	/**
//	 * 获取SD卡的根目录，末尾带\
//	 * 
//	 * @return
//	 */
//	public static String getSDRoot() {
//		return Environment.getExternalStorageDirectory().getAbsolutePath()
//				+ File.separator;
//	}

	/**
	 * 列出root目录下所有子目录
	 * 
	 * @param path
	 * @return 绝对路径
	 */
	public static List<String> listPath(String root) {
		List<String> allDir = new ArrayList<String>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory()) {
					allDir.add(f.getAbsolutePath());
				}
			}
		}
		return allDir;
	}
	
	public static List<File> getChild(String root) {
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		if (path.isDirectory()) 
			return  Arrays.asList(path.listFiles());
		else
			return null;
			
	}

	/**
	 * 获取下载文件
	 * 
	 * @return 文件
	 * @throws MessageException
	 *             异常信息
	 */
	public static File getDownloadDir() throws Exception {
		File downloadFile = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			downloadFile = new File(Constants.DOWNLOAD_PATH);
			if (!downloadFile.exists()) {
				downloadFile.mkdirs();
			}
		}
		if (downloadFile == null) {
			throw new Exception("can not make dir");
		}
		return downloadFile;
	}
	
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	public static boolean isDir(String filePath) {
		File file = new File(filePath);
		return file.exists() && file.isDirectory();
	}
	
	//获取后缀
	public static String getExspansion(String fileName){
		if(TextUtils.isEmpty(fileName))
			return null;
		int index = fileName.lastIndexOf(".");
		if(-1==index || index==(fileName.length()-1))
			return null;
		return fileName.substring(index);
	}

	public static void prepareFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void delete(String filePath) {
		if (filePath == null) {
			return;
		}
		try {
			File file = new File(filePath);
			if (file == null || !file.exists()) {
				return;
			}
			if (file.isDirectory()) {
				deleteDirRecursive(file);
			} else {
				file.delete();
			}
		} catch (Exception e) {
			Log.e(tag, e.toString());
		}
	}

	/*
	 * 递归删除目录
	 */
	public static void deleteDirRecursive(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isFile()) {
				f.delete();
			} else {
				deleteDirRecursive(f);
			}
		}
		dir.delete();
	}

	/**
	 * 判断SD卡是否已经准备好
	 * 
	 * @return 是否有SDCARD
	 */
	public static boolean isSDCardReady() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 可扩展卡路径
	 * @return
	 */
	public static String getExtSdCardPath(){
		File file = new File("/mnt/external_sd/");
		if(file.exists()){
			return file.getAbsolutePath();
		}else{
			file = new File("/mnt/extSdCard/");
			if(file.exists())
				return file.getAbsolutePath();
		}
		return null;
	}

}