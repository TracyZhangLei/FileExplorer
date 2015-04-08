# FileExplorer
* android file explorer,we can choose files by mime type such as photos,audios,videos,by sdcard path and extsdcard. it's easy to use and configure chosen file limit and file size.

* android 本地文件浏览选择器，可配置选择文件数量、大小限制，并根据不同类型进行展示


## Usage

```
startActivity(cxt , LocaleFileMain.class),get chosen files list by FileManager.getInstance().getChoosedFiles()
```

## Configuration

```
set chosen limits by FileManager.getInstance().initConfiguration(int maxChoosedCnt , long maxFileSize), default is DEFAULT_MAX_CHOOSED_CNT, DEFAULT_MAX_FILESIZE, reset to default by FileManager.getInstance().reSetDefaultConfiguration()
```


![screenshot](https://github.com/TracyZhangLei/FileExplorer/blob/master/screenshot.png)