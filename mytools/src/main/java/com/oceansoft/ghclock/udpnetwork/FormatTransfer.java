package com.oceansoft.ghclock.udpnetwork;

/** 
* 通信格式转换 
* 
* Java和一些windows编程语言如c、c++、delphi所写的网络程序进行通讯时，需要进行相应的转换 
* 高、低字节之间的转换 
* windows的字节序为低字节开头 
* linux,unix的字节序为高字节开头 
* java则无论平台变化，都是高字节开头 
*/   
public class FormatTransfer {  
/** 
  * 将int转为低字节在前，高字节在后的byte数组 
  * @param n int 
  * @return byte[] 
  */  
public static byte[] toLH(int n) {  
  byte[] b = new byte[4];  
  b[0] = (byte) (n & 0xff);  
  b[1] = (byte) (n >> 8 & 0xff);  
  b[2] = (byte) (n >> 16 & 0xff);  
  b[3] = (byte) (n >> 24 & 0xff);  
  return b;  
}   
/** 
  * 将int转为高字节在前，低字节在后的byte数组 
  * @param n int 
  * @return byte[] 
  */  
public static byte[] toHH(int n) {  
  byte[] b = new byte[4];  
  b[3] = (byte) (n & 0xff);  
  b[2] = (byte) (n >> 8 & 0xff);  
  b[1] = (byte) (n >> 16 & 0xff);  
  b[0] = (byte) (n >> 24 & 0xff);  
  return b;  
}   
/** 
  * 将short转为低字节在前，高字节在后的byte数组 
  * @param n short 
  * @return byte[] 
  */  
public static byte[] toLH(short n) {  
  byte[] b = new byte[2];  
  b[0] = (byte) (n & 0xff);  
  b[1] = (byte) (n >> 8 & 0xff);  
  return b;  
}   
/** 
  * 将short转为高字节在前，低字节在后的byte数组 
  * @param n short 
  * @return byte[] 
  */  
public static byte[] toHH(short n) {  
  byte[] b = new byte[2];  
  b[1] = (byte) (n & 0xff);  
  b[0] = (byte) (n >> 8 & 0xff);  
  return b;  
}
/** 
 * 将高字节数组转换为int 
  * @param b byte[] 
  * @return int 
  */  
public static int hBytesToInt(byte[] b) {  
 int s = 0;  
  for (int i = 0; i < 3; i++) {  
   if (b[i] >= 0) {  
    s = s + b[i];  
    } else {  
    s = s + 256 + b[i];  
    }  
    s = s * 256;  
  }  
  if (b[3] >= 0) {  
    s = s + b[3];  
 } else {  
   s = s + 256 + b[3];  
  }  
  return s;  
}   
/** 
  * 将低字节数组转换为int 
  * @param b byte[] 
  * @return int 
  */  
public static int lBytesToInt(byte[] b) {  
  int s = 0;  
  for (int i = 0; i < 3; i++) {  
    if (b[3-i] >= 0) {  
    s = s + b[3-i];  
    } else {  
    s = s + 256 + b[3-i];  
    }  
    s = s * 256;  
  }  
  if (b[0] >= 0) {  
    s = s + b[0];  
  } else {  
    s = s + 256 + b[0];  
 }  
  return s;  
} 
}  
