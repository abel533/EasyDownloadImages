package com.isea.basic;

import java.security.MessageDigest;

public class Md5Utils {
	/** 
     * @param args 
     */  
    public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',  
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    public static void main(String[] args) {  
        try {
			System.out.println(Md5Utils.getMd5("asdfasdfasdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
  
    public static String getMd5(String str)  
            throws Exception {  
        byte[] buffer = str.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(buffer, 0, buffer.length);  
        return toHexString(md5.digest());  
    }  
  
    public static String toHexString(byte[] b) {  
        StringBuilder sb = new StringBuilder(b.length * 2);  
        for (int i = 0; i < b.length; i++) {  
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);  
            sb.append(hexChar[b[i] & 0x0f]);  
        }  
        return sb.toString();  
    }  
}
