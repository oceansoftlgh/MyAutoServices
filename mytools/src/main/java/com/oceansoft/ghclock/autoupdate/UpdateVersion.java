package com.oceansoft.ghclock.autoupdate;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class UpdateVersion {
	private String pfuv;
	private FTPClient client;
	private Boolean judge;
//	private String ftpA, ftpU,ftpP,ftpPat;
//	 UpdateVersion(String ftpA,String ftpU,String ftpP,String ftpPat ){
//		this.ftpA=ftpA;
//		this.ftpU=ftpU;
//		this.ftpP=ftpP;
//		this.ftpPat=ftpPat;
//	}
	 public Boolean connectFtp(String ftpA,String ftpU,String ftpP) {
				// TODO Auto-generated method stub
			
				try {
					client = new FTPClient();
					client.setConnectTimeout(7000);
					client.connect(ftpA);
//					Boolean loginResult =
					client.login(ftpU, ftpP);
					client.setControlEncoding("GBK");
					client.setFileType(FTP.BINARY_FILE_TYPE);
					int reply = this.client.getReplyCode();  
					if (!FTPReply.isPositiveCompletion(reply)) {  
					    judge=false; 
					}else{
					    judge = true;
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					Log.e("aa", e.toString());
					judge=false;
					return judge;
				}
				
		return judge;
	}
	 public String getNewVersionCode(String ftpA,String ftpU,String ftpP,String ftpPat) throws IOException {
		// 连接ftp
				judge=this.connectFtp(ftpA, ftpU, ftpP);
					if (judge) {
						client.changeWorkingDirectory(ftpPat);
						FTPFile[] l = client.listFiles();
						for (FTPFile ftpFile : l) {
							if (ftpFile.getName().endsWith(".ver")) {
								pfuv = ftpFile.getName().substring(0,
										ftpFile.getName().length() - 4);
								break;
							}
						}
					} else {
						pfuv = null;
					}
		return pfuv;
	}
	 public String saveVersionCode(String versionCode){
		
		return null;
	}
	 public InputStream getInputStream(String ftpA,String ftpU,String ftpP,String ftpPat,String name) throws IOException{
		 judge=this.connectFtp(ftpA, ftpU, ftpP);
		 InputStream is = null;
		 if(judge){
				client.changeWorkingDirectory(ftpPat);
				String ftpFileName = isFileExist(name);
				if(null != ftpFileName){ 
				    is = client.retrieveFileStream(ftpFileName);
				}
				
		 }
		
		 return is;
	 }
	 
	 public String isFileExist(String fileName){
		FTPFile[] l;
		try {
		    l = client.listFiles();
		
	            for (FTPFile ftpFile : l) {
	            	if ("cssd.apk".equalsIgnoreCase(ftpFile.getName())) {
	            		return ftpFile.getName().toString();
	            	}
	            }
	        } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                return null ;
	        }
		return null;
	     
	 }
	public void disconnect(){

		try {
			if (client != null) {
				client.disconnect();
				client = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
