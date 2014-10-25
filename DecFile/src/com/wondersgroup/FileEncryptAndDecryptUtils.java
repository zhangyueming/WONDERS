package com.wondersgroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author [lipengfei]
 * @version [1.0]
 */
public class FileEncryptAndDecryptUtils {
	
	
	/*
	 * 秘钥
	 */
	public  final static String myKey = "6BCD4F327FC89DC279D5E66826FEA2850E316275B07F94AE";
	
	 /** *//**
     * �?��文件加密�?
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /** *//**
     * �?��文件解密�?
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

	
	
	/**
	 * 加密函数 输入�?要加密的文件，密码（�?-F组成，共48个字符，表示3�?位的密码）如�?
	 * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746 其中�?AD67EA2F3BE6E5AD
	 * DES密码�?D368DFE03120B5DF DES密码�?92A8FD8FEC2F0746 DES密码�?输出�?
	 * 对输入的文件加密后，保存到同�?��件夹下增加了".tdes"扩展名的文件中�?
	 * 
	 * @param fileIn 临时文件
	 * @param fileOut 目标加密文件，输出流
	 * @param sKey 秘钥（加密和解密的秘钥�?应相同）
	 */
	public static void encrypt(File fileIn,File fileOut, String sKey) {
		try {
			if (sKey.length() == 48) {
				byte[] bytK1 = getKeyByStr(sKey.substring(0, 16));
				byte[] bytK2 = getKeyByStr(sKey.substring(16, 32));
				byte[] bytK3 = getKeyByStr(sKey.substring(32, 48));

				//FileInputStream fis = new FileInputStream(fileIn);
				//BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileIn));
				
				//byte[] bytIn = new byte[(int) fileIn.length()];
				/*for (int i = 0; i < fileIn.length(); i++) {
					bytIn[i] = (byte) fis.read();
				}*/
				//fis.read(bytIn);
				//fis.close();
				
				
				FileInputStream in = new FileInputStream(fileIn);
				// 加密
				/*byte[] bytOut = encryptByDES(encryptByDES(encryptByDES(bytIn,
						bytK1), bytK2), bytK3);*/
				
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(sKey.getBytes()));
				SecretKey skey = kgen.generateKey();
				byte[] raw = skey.getEncoded();
				SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				/*byte[] bytOut = null;
				try{
	               bytOut = cipher.doFinal(bytIn);
	              
				}catch(Exception e){
					e.printStackTrace();
				}*/
				
				
				
				
				String fileOutExt = fileOut.getPath() + ".tdes";
				//FileOutputStream fos = new FileOutputStream(fileOutExt);
				FileOutputStream out = new FileOutputStream(fileOutExt);   
				byte[] data = new byte[MAX_ENCRYPT_BLOCK];
		        byte[] encryptedData;    
		        while (in.read(data) != -1) {
		            encryptedData = cipher.doFinal(data);
		            out.write(encryptedData, 0, encryptedData.length);
		            out.flush();
		        }
		        out.close();
		        in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解密函数 输入�?要解密的文件，密码（�?-F组成，共48个字符，表示3�?位的密码）如�?
	 * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746 其中�?AD67EA2F3BE6E5AD
	 * DES密码�?D368DFE03120B5DF DES密码�?92A8FD8FEC2F0746 DES密码�?输出�?
	 * 对输入的文件解密后，保存到用户指定的文件中�?
	 * 
	 * @param fileIn 目标加密文件
	 * @param output response中获得，输出�?
	 * @param sKey 秘钥（加密和解密的秘钥�?应相同）
	 * 
	 */
	public static void decrypt(File fileIn,OutputStream output, String sKey) {
		try {
			if (sKey.length() == 48) {
				String strPath = fileIn.getPath();
				if (strPath.substring(strPath.length() - 5).toLowerCase()
						.equals(".tdes")){
					//strPath = strPath.substring(0, strPath.length() - 5);
				
					byte[] bytK1 = getKeyByStr(sKey.substring(0, 16));
					byte[] bytK2 = getKeyByStr(sKey.substring(16, 32));
					byte[] bytK3 = getKeyByStr(sKey.substring(32, 48));

					//FileInputStream fis = new FileInputStream(fileIn);
					//BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileIn));
					//byte[] bytIn = new byte[(int) fileIn.length()];
					/*for (int i = 0; i < fileIn.length(); i++) {
						bytIn[i] = (byte) fis.read();
					}*/
					//fis.read(bytIn);
					//fis.close();
					
					FileInputStream in = new FileInputStream(fileIn);
					// 解密
					/*byte[] bytOut = decryptByDES(decryptByDES(decryptByDES(
							bytIn, bytK3), bytK2), bytK1);*/
					    KeyGenerator kgen = KeyGenerator.getInstance("AES");
						kgen.init(128, new SecureRandom(sKey.getBytes()));
						SecretKey skey = kgen.generateKey();
						byte[] raw = skey.getEncoded();
						SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
						Cipher cipher = Cipher.getInstance("AES");
						cipher.init(Cipher.DECRYPT_MODE, skeySpec);
						/*byte[] bytOut = null;
						try{
							bytOut = cipher.doFinal(bytIn);
						}catch(Exception e){
							e.printStackTrace();
						}*/
			            
						OutputStream out = output;
					    byte[] data = new byte[MAX_DECRYPT_BLOCK];
					    byte[] decryptedData; 
					    while (in.read(data) != -1) {
					         decryptedData = cipher.doFinal(data);
					         out.write(decryptedData, 0, decryptedData.length); 
					         out.flush();
					    }
					   out.close();
					   in.close();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用DES方法加密输入的字�?bytKey�?��8字节长，是加密的密码
	 * @param [byte[],byte[]]
	 * @return byte[]
	 */
	private static byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
		return cip.doFinal(bytP);
	}

	/**
	 * 用DES方法解密输入的字�?bytKey�?��8字节长，是解密的密码
	 * @param [byte[],byte[]]
	 * @return byte[]
	 */
	private static byte[] decryptByDES(byte[] bytE, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk);
		return cip.doFinal(bytE);
	}

	/**
	 * 输入密码的字符形式，返回字节数组形式�?如输入字符串：AD67EA2F3BE6E5AD 返回字节数组：{
	 * 173,103,234,47,59,230,229,173 }
	 * @param [String]
	 * @return byte[]
	 */
	private static byte[] getKeyByStr(String str) {
		byte[] bRet = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
					+ getChrInt(str.charAt(2 * i + 1)));
			bRet[i] = itg.byteValue();
		}
		return bRet;
	}

	/**
	 * 计算�?��16进制字符�?0进制�?输入�?-F
	 * @param [char]
	 * @return int
	 */
	private static int getChrInt(char chr) {
		int iRet = 0;
		if (chr == "0".charAt(0))
			iRet = 0;
		if (chr == "1".charAt(0))
			iRet = 1;
		if (chr == "2".charAt(0))
			iRet = 2;
		if (chr == "3".charAt(0))
			iRet = 3;
		if (chr == "4".charAt(0))
			iRet = 4;
		if (chr == "5".charAt(0))
			iRet = 5;
		if (chr == "6".charAt(0))
			iRet = 6;
		if (chr == "7".charAt(0))
			iRet = 7;
		if (chr == "8".charAt(0))
			iRet = 8;
		if (chr == "9".charAt(0))
			iRet = 9;
		if (chr == "A".charAt(0))
			iRet = 10;
		if (chr == "B".charAt(0))
			iRet = 11;
		if (chr == "C".charAt(0))
			iRet = 12;
		if (chr == "D".charAt(0))
			iRet = 13;
		if (chr == "E".charAt(0))
			iRet = 14;
		if (chr == "F".charAt(0))
			iRet = 15;
		return iRet;
	}
	
	/*
	 * 随机产生48位key
	 * return String
	 */
	private String RanGetKey(){
		KeyGenerator kg;
		String key = null;
		try {
			kg = KeyGenerator.getInstance("DES");
			kg.init(56);
			Key ke = kg.generateKey();
			byte[] bytK1 = ke.getEncoded();
			ke = kg.generateKey();
			byte[] bytK2 = ke.getEncoded();
			ke = kg.generateKey();
			byte[] bytK3 = ke.getEncoded();
			key = getByteStr(bytK1) + getByteStr(bytK2)
			+ getByteStr(bytK3);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key;
	}
	
	/*
	 * 
	 * 获得DES密码�?
	 * @param [byte[]]
	 * @return String
	 */
	private String getByteStr(byte[] byt) {
		String strRet = "";
		for (int i = 0; i < byt.length; i++) {
			strRet += getHexValue((byt[i] & 240) / 16);
			strRet += getHexValue(byt[i] & 15);
		}
		return strRet;
	}
	
	/*
	 * 获得16进制�?
	 * @param[int]
	 * @return String
	 */

	private String getHexValue(int s) {
		String sRet = null;
		switch (s) {
		case 0:
			sRet = "0";
			break;
		case 1:
			sRet = "1";
			break;
		case 2:
			sRet = "2";
			break;
		case 3:
			sRet = "3";
			break;
		case 4:
			sRet = "4";
			break;
		case 5:
			sRet = "5";
			break;
		case 6:
			sRet = "6";
			break;
		case 7:
			sRet = "7";
			break;
		case 8:
			sRet = "8";
			break;
		case 9:
			sRet = "9";
			break;
		case 10:
			sRet = "A";
			break;
		case 11:
			sRet = "B";
			break;
		case 12:
			sRet = "C";
			break;
		case 13:
			sRet = "D";
			break;
		case 14:
			sRet = "E";
			break;
		case 15:
			sRet = "F";
		}
		return sRet;
	}
	
	public static void encrypt2(FileInputStream fileIn,File fileOut, String sKey) {
		try {
			if (sKey.length() == 48) {
				byte[] bytK1 = getKeyByStr(sKey.substring(0, 16));
				byte[] bytK2 = getKeyByStr(sKey.substring(16, 32));
				byte[] bytK3 = getKeyByStr(sKey.substring(32, 48));

				//FileInputStream fis = new FileInputStream(fileIn);
				//BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileIn));
				
				//byte[] bytIn = new byte[(int) fileIn.length()];
				/*for (int i = 0; i < fileIn.length(); i++) {
					bytIn[i] = (byte) fis.read();
				}*/
				//fis.read(bytIn);
				//fis.close();
				
				
				FileInputStream in = fileIn;
				// 加密
				/*byte[] bytOut = encryptByDES(encryptByDES(encryptByDES(bytIn,
						bytK1), bytK2), bytK3);*/
				
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(sKey.getBytes()));
				SecretKey skey = kgen.generateKey();
				byte[] raw = skey.getEncoded();
				SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				/*byte[] bytOut = null;
				try{
	               bytOut = cipher.doFinal(bytIn);
	              
				}catch(Exception e){
					e.printStackTrace();
				}*/
				
				
				
				
				String fileOutExt = fileOut.getPath() + ".tdes";
				//FileOutputStream fos = new FileOutputStream(fileOutExt);
				FileOutputStream out = new FileOutputStream(fileOutExt);   
				byte[] data = new byte[MAX_ENCRYPT_BLOCK];
		        byte[] encryptedData;    
		        while (in.read(data) != -1) {
		            encryptedData = cipher.doFinal(data);
		            out.write(encryptedData, 0, encryptedData.length);
		            out.flush();
		        }
		        out.close();
		        in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
