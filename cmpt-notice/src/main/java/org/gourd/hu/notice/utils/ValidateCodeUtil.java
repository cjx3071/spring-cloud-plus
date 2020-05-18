package org.gourd.hu.notice.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 作用：用于发送短信/邮箱验证码
 *
 * @author gourd.hu
 */
public class ValidateCodeUtil {

	/**
	 * 数字
	 */
	private static final String SYMBOLS = "0123456789";

	/**
	 * 字符串
	 */
	// private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
    private static final Random RANDOM = new SecureRandom();
	
	/**
	 * 获取长度为 num 的随机数字
	 * @return 随机数字
	 */
	public static String getCode(int num) {
		
		// 如果需要4位，那 new char[4] 即可，其他位数同理可得
		char[] nonceChars = new char[num];
		for (int index = 0; index < nonceChars.length; ++index) {
			nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
		}
		return new String(nonceChars);
	}

	/**
	 * 获取长度为 4 的随机数字
	 * @return 随机数字
	 */
	public static String getCode() {
		char[] nonceChars = new char[4];
		for (int index = 0; index < nonceChars.length; ++index) {
			nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
		}
		return new String(nonceChars);
	}

	public static void main(String[] args) {
		System.out.println(getCode());
	}
 
}