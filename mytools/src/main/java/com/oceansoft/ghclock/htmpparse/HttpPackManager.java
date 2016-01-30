package com.oceansoft.ghclock.htmpparse;


/**
 * Created by Administrator on 2016/1/25.
 */
public class HttpPackManager {

	public static boolean ConnecttoServer(String url) {
//		try {
//			//实例化HttpClient
//			HttpClient client = new HttpClient();
//			//Stream页面里面有Host地址 端口是80
//			client.getHostConfiguration().setHost("http://easybug.org", 80);
//			//用目标地址 实例一个POST方法
//			PostMethod post = new PostMethod("http://easybug.org/Member/Login");
//			//将需要的键值对写出来
//			NameValuePair email = new NameValuePair("email", "liuguanghui@oceansoftware.com.cn");
//			NameValuePair password = new NameValuePair("password", "lgh123456");
//			
//			//给POST方法加入上述键值对
//			post.setRequestBody(new NameValuePair[]{email,password});
//			//执行POST方法
//			client.executeMethod(post);
//			//将POST返回的数据以流的形式读入，再把输入流流至一个buff缓冲字节数组
//			//StreamTool类是我自己写的一个工具类，其内容将在下文附出
//			byte[] buff = StreamTools.readInputStream(post.getResponseBodyAsStream());
//			//将返回的内容格式化为String存在html中
//			String html = new String(buff);
//			//任务完成了，释放连接
//			post.releaseConnection();
//			System.out.println(html);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return true;
	}

}
