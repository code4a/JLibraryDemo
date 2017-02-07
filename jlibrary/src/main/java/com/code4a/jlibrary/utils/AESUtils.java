package com.code4a.jlibrary.utils;

import android.text.TextUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by code4a on 2017/2/6.
 */

public final class AESUtils {

	private final static String HEX = "0123456789ABCDEF";
	private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";// AES是加密方式
																			// CBC是工作模式
																			// PKCS5Padding是填充模式
	private static final String AES = "AES";// AES 加密
	private static final String SHA1PRNG = "SHA1PRNG";// // SHA1PRNG 强随机种子算法,
														// 要区别4.2以上版本的调用方法

    /** 
     * 加密 
     * 
     * @param key 密钥 
     * @param cleartext 加密文本 
     * @return 
     */ 
	public static String encrypt(String key, String cleartext) {
		if (TextUtils.isEmpty(cleartext)) {
			return cleartext;
		}
		try {
			byte[] result = encrypt(key, cleartext.getBytes());
			return toHex(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /** 
     * 解密 
     * 
     * @param key       密钥 
     * @param encrypted 待揭秘文本 
     * @return 
     */ 
	public static String decrypt(String key, String encrypted) {
		if (TextUtils.isEmpty(encrypted)) {
			return encrypted;
		}
		try {
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(key, enc);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 解密
	 */
	private static byte[] decrypt(String key, byte[] encrypted)
			throws Exception {
		byte[] raw = getRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	/*
	 * 加密
	 */
	private static byte[] encrypt(String key, byte[] clear) throws Exception {
		byte[] raw = getRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[cipher.getBlockSize()]));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	// 对密钥进行处理
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(AES);
		// for android
		SecureRandom sr = null;
		// 在4.2以上版本中，SecureRandom获取方式发生了改变
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
		} else {
			sr = SecureRandom.getInstance(SHA1PRNG);
		}
		// for Java
		// secureRandom = SecureRandom.getInstance(SHA1PRNG);
		sr.setSeed(seed);
		kgen.init(128, sr); // 256 bits or 128 bits,192bits
		// AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	/*
	 * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
	 */
	public static String generateKey() {
		try {
			SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
			byte[] bytes_key = new byte[20];
			localSecureRandom.nextBytes(bytes_key);
			String str_key = toHex(bytes_key);
			return str_key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    } 
 
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    } 
 
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    } 
 
    public static String toHex(byte[] buf) {
        if (buf == null)
            return ""; 
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        } 
        return result.toString();
    } 
 
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
