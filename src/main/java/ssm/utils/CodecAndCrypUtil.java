package ssm.utils;


import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.util.Assert;

import java.security.Key;

/**
 * Created by dxgong on 2017/6/29.
 */
public class CodecAndCrypUtil {
    //设定加密盐值
    public static final String SALT = "adfd324nj5923493825kjkjg94230";



   /**MD5加密
    * @Prama string 需要加密的字符串
    *
    * */
   public static String MD5(String string){
       return new Md5Hash(string, SALT).toString();
   }

   /**
    * SHA256加密
    * @Prama string 需要加密的字符串
    * */
   public static String SHA256(String string){
       return new Sha256Hash(string, SALT).toString();
   }


   /* *//**
     * BlowFish加密
     * @Prama string 需要加密的字符串
     * *//*
    public static String blowfishCipher(String str) {
        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
        blowfishCipherService.setKeySize(128);
        //加密
        String encrptText = blowfishCipherService.encrypt(SALT.getBytes(),str.getBytes()).toString();
        return encrptText;
        //解密
        //String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

    }

    *//**
     * BlowFish解密
     * @Prama string 需要解密的字符串
     * *//*
    public static String blowfishCipherDecode(String str) {
        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
        blowfishCipherService.setKeySize(128);

        //解密
        return String.valueOf(blowfishCipherService.decrypt(SALT.getBytes(),str.getBytes()).toHex());

    }*/

    public static void main(String[] args){
        //System.out.println("BlowFish加密："+blowfishCipher("3213123"));  //86de6100f3c40f424fd886e5c47055f6881f565a97feb8c9a15092c615714637de8d0444a25b7978


       // System.out.println("BlowFish解密："+blowfishCipherDecode("86de6100f3c40f424fd886e5c47055f6881f565a97feb8c9a15092c615714637de8d0444a25b7978"));
    }

}
