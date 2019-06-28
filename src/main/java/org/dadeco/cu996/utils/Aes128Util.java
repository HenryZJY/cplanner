package org.dadeco.cu996.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Aes128Util {
	private static Base64 base64 = new Base64();
	private static final String ENCRYPT_KEY = "65dg84deds1687a5";

	public static String encryptString(String str) {
		String enStr = null;

		if (!CommonUtil.isEmptyString(str)) {
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

			try {
				if (cipher != null) {
					byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));

					enStr = base64.encodeToString(encrypted);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}

		return enStr;
	}

	public static String decryptString(String str) {
		String deStr = null;

		if (!CommonUtil.isEmptyString(str)) {
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

			try {
				byte[] decryptedBytes = base64.decode(str);

				if (decryptedBytes != null) {
					byte[] originalTxtBytes = cipher.doFinal(decryptedBytes);

					if (originalTxtBytes != null) {
						deStr = new String(originalTxtBytes, "UTF-8");
					}
				}
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return deStr;
	}

	private static Cipher getCipher(int mode) {
		Cipher cipher = null;

		try {
			byte[] keyByte = Aes128Util.ENCRYPT_KEY.getBytes("UTF-8");

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte, "AES");

			if (secretKeySpec != null) {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

				cipher.init(mode, secretKeySpec);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		return cipher;
	}

	public static void main(String[] args) throws Exception {
		String openId = "ob3iawQX4PERQ1O-LPxxDJbaln-o";
		System.out.println(openId);

		String enOpenId = Aes128Util.encryptString(openId);
		System.out.println("enOpenId : " + enOpenId);
		System.out.println("enOpenId : " + URLEncoder.encode(enOpenId, "UTF-8"));

		String deOpenId = Aes128Util.decryptString(enOpenId);
		System.out.println("deOpenId : " + deOpenId);		
	}
}
