package com.dxj.common.util;

import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 加密
 *
 * @author dxj
 * @date 2019-04-23
 */
public class EncryptUtils {

    private static String strKey = "Passw0rd", strParam = "Passw0rd";

    /**
     * 对称加密
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String source) throws Exception {
        Comm comm = new Comm(source).invoke();
        if (comm.is()) return null;
        Cipher cipher = comm.getCipher();
        SecretKey secretKey = comm.getSecretKey();
        IvParameterSpec iv = comm.getIv();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return byte2hex(
                cipher.doFinal(source.getBytes(StandardCharsets.UTF_8))).toUpperCase();
    }

    private static String byte2hex(byte[] inStr) {
        String stmp;
        StringBuilder out = new StringBuilder(inStr.length * 2);
        for (byte b : inStr) {
            stmp = Integer.toHexString(b & 0xFF);
            if (stmp.length() == 1) {
                // 如果是0至F的单位字符串，则添加0
                out.append("0").append(stmp);
            } else {
                out.append(stmp);
            }
        }
        return out.toString();
    }


    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 对称解密
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static String desDecrypt(String source) throws Exception {
        if (source == null || source.length() == 0) {
            return null;
        }
        byte[] src = hex2byte(source.getBytes());
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(strParam.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(src);
        return new String(retByte);
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("123456"));
    }

    private static class Comm {
        private boolean myResult;
        private String source;
        private Cipher cipher;
        private SecretKey secretKey;
        private IvParameterSpec iv;

        Comm(String source) {
            this.source = source;
        }

        boolean is() {
            return myResult;
        }

        Cipher getCipher() {
            return cipher;
        }

        SecretKey getSecretKey() {
            return secretKey;
        }

        IvParameterSpec getIv() {
            return iv;
        }

        Comm invoke() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
            if (source == null || source.length() == 0) {
                myResult = true;
                return this;
            }
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            secretKey = keyFactory.generateSecret(desKeySpec);
            iv = new IvParameterSpec(strParam.getBytes(StandardCharsets.UTF_8));
            myResult = false;
            return this;
        }
    }
}
