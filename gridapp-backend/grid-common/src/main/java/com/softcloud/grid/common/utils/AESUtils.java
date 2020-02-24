/**
 * Project Name:gridapp-common
 * File Name:AESUtils.java
 * Package Name:com.softcloud.grid.common.utils
 * Date:2019年7月3日下午6:46:14
 * Copyright (c) 2019, softcloud All Rights Reserved.
*/

package com.softcloud.grid.common.utils;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.Base64;

/**
 * ClassName:AESUtils <br/>
 * Function: AES加解密工具 <br/>
 * @since JDK 1.8
 */
public class AESUtils {

    private static final Logger log = LoggerFactory.getLogger(AESUtils.class);

    /**
     * SERVER_KEY:服务器密钥
     * @since JDK 1.8
     */
    private static final byte[] SERVER_KEY = { -38, -122, 84, 104, -92, 78, 50, -117, 29, -15, -74, -114, 58, 38, 30, 93 };

    /**
     * USER_KEY:用户密钥
     * @since JDK 1.8
     */
    private static final byte[] USER_KEY = { -21, -42, -7, -86, 94, 23, 107, -19, 123, -84, -45, -83, -1, 15, -81, -23 };

    /**
     * USER_KEY:配置密钥
     * @since JDK 1.8
     */
    private static final byte[] CONFIG_KEY = { -60, -94, 91, 12, 30, -116, 47, 51, 100, 53, -12, 93, 40, 92, -97, 105 };

    /**
     * aesServerEncrypt:服务器加密 <br/>
     *
     * date: 2019年7月4日 上午8:43:10
     * @author juvenbin
     * @param content 明文字符串
     * @return 密文字符串
     * @since JDK 1.8
     */
    public static String aesServerEncrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.EMPTY;
        }
        return base64Encode(generateEncryptCipher(content, SERVER_KEY));
    }

    /**
     * aesServerDecrypt:服务器解密 <br/>
     *
     * date: 2019年7月4日 上午8:40:45
     * @author juvenbin
     * @param encryptStr 密文字符串
     * @return 明文字符串
     * @since JDK 1.8
     */
    public static String aesServerDecrypt(String encryptStr) {
        if (StringUtils.isBlank(encryptStr)) {
            return StringUtils.EMPTY;
        }
        return generateDecryptCipher(Base64.base64ToByteArray(encryptStr), SERVER_KEY);
    }

    /**
     * aesUserEncrypt:用户加密 <br/>
     *
     * date: 2019年7月4日 上午8:43:10
     * @author juvenbin
     * @param content 明文字符串
     * @return 密文字符串
     * @since JDK 1.8
     */
    public static String aesUserEncrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.EMPTY;
        }
        return base64Encode(generateEncryptCipher(content, USER_KEY));
    }

    /**
     * aesUserDecrypt:用户解密 <br/>
     *
     * date: 2019年7月4日 上午8:40:45
     * @author juvenbin
     * @param encryptStr 密文字符串
     * @return 明文字符串
     * @since JDK 1.8
     */
    public static String aesUserDecrypt(String encryptStr) {
        if (StringUtils.isBlank(encryptStr)) {
            return StringUtils.EMPTY;
        }
        return generateDecryptCipher(Base64.base64ToByteArray(encryptStr), USER_KEY);
    }

    /**
     * aesConfigEncrypt:配置加密 <br/>
     *
     * date: 2019年7月4日 上午8:43:10
     * @author juvenbin
     * @param content 明文字符串
     * @return 密文字符串
     * @since JDK 1.8
     */
    public static String aesConfigEncrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.EMPTY;
        }
        return base64Encode(generateEncryptCipher(content, CONFIG_KEY));
    }

    /**
     * aesConfigDecrypt:配置解密 <br/>
     *
     * date: 2019年7月4日 上午8:40:45
     * @author juvenbin
     * @param encryptStr 密文字符串
     * @return 明文字符串
     * @since JDK 1.8
     */
    public static String aesConfigDecrypt(String encryptStr) {
        if (StringUtils.isBlank(encryptStr)) {
            return StringUtils.EMPTY;
        }
        return generateDecryptCipher(Base64.base64ToByteArray(encryptStr), CONFIG_KEY);
    }

    /**
     * base64Encode:将密文字节转换为密文字符串 <br/>
     *
     * date: 2019年7月4日 上午8:38:38
     * @author juvenbin
     * @param bytes 待编码的字节
     * @return 密文字符串
     * @since JDK 1.8
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.byteArrayToBase64(bytes);
    }

    /**
     * generateEncryptCipher:生成加密密文字节 <br/>
     *
     * date: 2019年7月4日 上午8:34:10
     * @author juvenbin
     * @param content 明文
     * @param encryptKey 密钥
     * @return 密文字节
     * @since JDK 1.8
     */
    private static byte[] generateEncryptCipher(String content, byte[] encryptKey) {
        byte[] encryResult = null;
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encryptKey);
            
            kgen.init(128, secureRandom);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            
            encryResult = cipher.doFinal(content.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("AES加密失败！！！", e);
        }
        return encryResult;
    }

    /**
     * generateDecryptCipher:生成解密密码字符串 <br/>
     *
     * date: 2019年7月4日 上午8:32:24
     * @author juvenbin
     * @param encryptBytes 密文
     * @param decryptKey 密钥
     * @return 明文字符串
     * @since JDK 1.8
     */
    private static String generateDecryptCipher(byte[] encryptBytes, byte[] decryptKey) {
        byte[] decryptBytes = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(decryptKey);

            kgen.init(128, secureRandom);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

            decryptBytes = cipher.doFinal(encryptBytes);
        } catch (Exception e) {
            log.error("AES解密失败！！！", e);
        } 
        return new String(decryptBytes);
    }
    
    /**
     * getSeedStr:字节种子转换为字符串 <br/>
     * 
     * @param seed
     *            字节种子
     * @return 字符串种子
     * @since JDK 1.6
     */
    public static String getKeyStr(byte[] seed) {
        return Arrays.toString(seed);
    }

    /**
     * getRandomSeed:获取16字节的随即种子 <br/>
     * 
     * @return 16字节的随即种子
     * @since JDK 1.6
     */
    public static byte[] getRandomKey() {
        SecureRandom sr = new SecureRandom();
        byte[] seed = new byte[16];
        sr.nextBytes(seed);
        return seed;
    }

}
