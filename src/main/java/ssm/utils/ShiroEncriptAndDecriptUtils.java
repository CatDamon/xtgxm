package ssm.utils;

import java.security.Key;
import java.security.MessageDigest;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

/**
 * @author dxgong
 * 2017年3月15日
 * */
public class ShiroEncriptAndDecriptUtils {
	//盐值
	public static String SALT ="19940313";
	public static int COUNT = 2;
	public static AesCipherService aesCipherService = new AesCipherService();
	public static Key key = aesCipherService.generateNewKey();
	
	/**
	 * AES算法加密
	 * @param text 
	 * */
	public static String encriptForAes (String text) {
		aesCipherService.setKeySize(128); //设置key长度  
		//生成key 
		//Key key = aesCipherService.generateNewKey();  
		//加密  
		return aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();  
	}
	
	/**
	 * AES算法解密
	 * @param text 
	 * */
	public static String decriptForAes (String text) {
		return new String(aesCipherService.decrypt(Hex.decode(text), key.getEncoded()).getBytes()); 
	}
	
	/**
	 * base64加密
	 * @param text 
	 * */
	public static String encriptForBase64 (String text) {
		return Base64.encodeToString(text.getBytes());
	}
	
	/**
	 * base64解密
	 * @param text 
	 * */
	public static String decriptForBase64 (String text) {
		return Base64.decodeToString(text);
	}
	
	/**
	 * MD5加密
	 * @param text 
	 * */
	public static String encriptForMD5 (String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	
	
}	
