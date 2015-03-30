package com.tracy.fileexplorer;

import java.io.File;
import java.io.Serializable;

import android.text.TextUtils;

import com.tracy.fileexplorer.util.FileUtils;
import com.tracy.fileexplorer.util.TimeUtils;

/***
 * 
 * @author zhanglei
 *
 */
public class TFile implements Comparable<TFile>, Serializable {
	
	public enum MimeType{
		APK,//apk
		TXT,//文本文件
		IMAGE,//图片
		RAR,//压缩文件
		DOC,//doc
		PPT,//ppt
		XLS,//xls
		HTML,//html
		MUSIC,//mp3
		VIDEO,//video
		PDF,//pdf
		UNKNOWN//未知
	}
	
	//文件发送接收状态
	public enum FileState{
		DOWNLOADED,//已下载
		UNLOAD,//未下载
		SENDED,//已发送
		UNSEND//发送失败
	}
	
	private TFile(){}
	
	private String fileName; //文件名
	private String fileUrl; //文件url下载路径
	private String filePath; //文件本地路径
	private boolean isDir;//是否是文件夹
	private long lastModifyTime;//最后修改时间
	private long fileSize;//大小
	private String fileSizeStr;//大小字符串
	private String lastModifyTimeStr;//最后修改时间字符串
	private MimeType mimeType;//mimeType
	private FileState fileState;//文件状态
	
	public FileState getFileState() {
		return fileState;
	}
	public void setFileState(FileState fileState) {
		this.fileState = fileState;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public boolean isDir() {
		return isDir;
	}
	public long getLastModifyTime() {
		return lastModifyTime;
	}
	public long getFileSize() {
		return fileSize;
	}
	public String getFileSizeStr() {
		return fileSizeStr;
	}
	public MimeType getMimeType() {
		return mimeType;
	}
	public String getLastModifyTimeStr() {
		return lastModifyTimeStr;
	}

	//本地文件builder模式
	public static class Builder{
		TFile bxFile;
		
		public Builder(String path){
			if(FileUtils.isFileExist(path)){
				File file = new File(path); 
				bxFile = new TFile();
				bxFile.fileName = file.getName();
				bxFile.filePath = file.getAbsolutePath();
				boolean isDir = file.isDirectory();
				bxFile.isDir = isDir;
				if(!isDir){
					bxFile.fileSize = file.length();
					bxFile.fileSizeStr = FileUtils.getFileSizeStr(bxFile.fileSize);
					bxFile.lastModifyTime = file.lastModified();
					bxFile.lastModifyTimeStr = TimeUtils.getDateTime(bxFile.lastModifyTime);
					String exspansion = FileUtils.getExspansion(bxFile.fileName);
					if(TextUtils.isEmpty(exspansion))
						bxFile.mimeType = MimeType.UNKNOWN;
					else{
						MimeType mimeType = FileManager.getInstance().getMimeType(exspansion);
						bxFile.mimeType = null==mimeType?MimeType.UNKNOWN:mimeType;
					}
				}
			}
		}
		
		public TFile build(){
			return bxFile;
		}
	}
	
	//url文件builder模式(用于短消息附带的文件信息初始化)
	public static class UrlBuilder{
		TFile bxFile;
		
		public UrlBuilder(String fileUrl , String fileName , long fileSize , String savedPath , FileState fileState){
			bxFile = new TFile();
			bxFile.fileUrl = fileUrl;
			bxFile.fileName = fileName;
			bxFile.fileSize = fileSize;
			bxFile.fileState = fileState;
			bxFile.fileSizeStr = FileUtils.getFileSizeStr(fileSize);
			bxFile.filePath = savedPath;
			String exspansion = FileUtils.getExspansion(fileName);
			if(TextUtils.isEmpty(exspansion))
				bxFile.mimeType = MimeType.UNKNOWN;
			else{ 
				MimeType mimeType = FileManager.getInstance().getMimeType(exspansion);
				bxFile.mimeType = null==mimeType?MimeType.UNKNOWN:mimeType;
			}
		}
		
		public TFile build(){
			return bxFile;
		}
	}

	@Override
	public int compareTo(TFile another) {
		if(isDir()){
			if(another.isDir())
				return fileName.compareToIgnoreCase(another.getFileName());
			else
				return -1;
		}else{
			if(another.isDir())
				return 1;
			else
				return fileName.compareToIgnoreCase(another.getFileName());
		}
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(null == o)
			return false;
		if(o instanceof TFile){
			TFile other = (TFile)o;
			return other.filePath.equals(filePath);
		}else{
			return false;
		}
	}
	@Override
	public int hashCode() {
		return filePath.hashCode();
	}
	
	public boolean isFileSizeValid(){
		return fileSize<FileManager.getInstance().getMaxFileSize();
	}
}
