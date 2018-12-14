package com.stylefeng.guns.core.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    public static final String password = "55AATA";

    public AESUtil() {
    }

    private static byte[] encrypt(String content, String password) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, key);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
        } catch (NoSuchPaddingException var11) {
            var11.printStackTrace();
        } catch (InvalidKeyException var12) {
            var12.printStackTrace();
        } catch (UnsupportedEncodingException var13) {
            var13.printStackTrace();
        } catch (IllegalBlockSizeException var14) {
            var14.printStackTrace();
        } catch (BadPaddingException var15) {
            var15.printStackTrace();
        }

        return null;
    }

    private static byte[] decrypt(byte[] content) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed("55AATA".getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
            return null;
        } catch (NoSuchPaddingException var9) {
            var9.printStackTrace();
            return null;
        } catch (InvalidKeyException var10) {
            var10.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException var11) {
            var11.printStackTrace();
            return null;
        } catch (BadPaddingException var12) {
            var12.printStackTrace();
            return null;
        }
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) throws Exception {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

    public static String getCode(String Context) {
        byte[] jia = encrypt(Context, "55AATA");
        String jias = parseByte2HexStr(jia);
        return jias;
    }

    public static String getDecrypt(String code) throws Exception {
        byte[] jia2 = parseHexStr2Byte(code);
        byte[] decryptResult = decrypt(jia2);
        return new String(decryptResult);
    }
}
