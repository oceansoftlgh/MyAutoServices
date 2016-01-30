package com.oceansoft.ghclock.recevier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ApkManager {
	private File file;

	/**
	 * 打开已经安装好的apk
	 */
	private void openApk(Context context, String url) {
		PackageManager manager = context.getPackageManager();
		// 这里的是你下载好的文件路径
		PackageInfo info = manager.getPackageArchiveInfo(Environment.getExternalStorageDirectory().getAbsolutePath()
				+ getFilePath(url), PackageManager.GET_ACTIVITIES);
		if (info != null) {
			Intent intent = manager.getLaunchIntentForPackage(info.applicationInfo.packageName);
			context.startActivity(intent);
		}
	}

	/**
	 * 根据传过来url创建文件
	 *
	 */
	private File getFile(String url) {
		File files = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), getFilePath(url));
		return files;
	}

	/**
	 * 截取出url后面的apk的文件名
	 *
	 * @param url
	 * @return
	 */
	private String getFilePath(String url) {
		return url.substring(url.lastIndexOf("/"), url.length());
	}


	/**
	 * 后台在下面一个Apk 下载完成后返回下载好的文件
	 *
	 * @param httpUrl
	 * @return
	 */
	private void downFile(final String httpUrl) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(httpUrl);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					FileOutputStream fileOutputStream = null;
					InputStream inputStream;
					if (connection.getResponseCode() == 200) {
						inputStream = connection.getInputStream();

						if (inputStream != null) {
							file = getFile(httpUrl);
							fileOutputStream = new FileOutputStream(file);
							byte[] buffer = new byte[1024];
							int length = 0;

							while ((length = inputStream.read(buffer)) != -1) {
								fileOutputStream.write(buffer, 0, length);
							}
							fileOutputStream.close();
							fileOutputStream.flush();
						}
						inputStream.close();
					}

					System.out.println("已经下载完成");
					// 往handler发送一条消息 更改button的text属性
//					Message message = handler.obtainMessage();
//					message.what = 1;
//					handler.sendMessage(message);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
