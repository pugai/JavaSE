package util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES 对称加密算法
 */
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    public static String encryptHex(byte[] encrypt, byte[] key) {
        return Hex.encodeHexString(encrypt(encrypt, key));
    }

    public static byte[] encrypt(byte[] encrypt, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(1, new SecretKeySpec(key, KEY_ALGORITHM));
            return cipher.doFinal(encrypt);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String decryptHex(char[] decrypt, byte[] key, String charset) {
        try {
            return new String(decryptHex(decrypt, key), charset);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static byte[] decryptHex(char[] decrypt, byte[] key) {
        try {
            return decrypt(Hex.decodeHex(decrypt), key);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] decrypt(byte[] decrypt, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(2, new SecretKeySpec(key, KEY_ALGORITHM));
            return cipher.doFinal(decrypt);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String encryptHexWithSalt(byte[] encrypt, byte[] salt, byte[] key) {
        return Hex.encodeHexString(encryptWithSalt(encrypt, salt, key));
    }

    public static byte[] encryptWithSalt(byte[] encrypt, byte[] salt, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(1, new SecretKeySpec(key, KEY_ALGORITHM));
            xor(encrypt, salt);
            return cipher.doFinal(encrypt);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static String decryptHexWithSalt(char[] decrypt, byte[] salt, byte[] key, String charset) {
        try {
            return new String(decryptHexWithSalt(decrypt, salt, key), charset);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static byte[] decryptHexWithSalt(char[] decrypt, byte[] salt, byte[] key) {
        try {
            return decryptWithSalt(Hex.decodeHex(decrypt), salt, key);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static byte[] decryptWithSalt(byte[] decrypt, byte[] salt, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(2, new SecretKeySpec(key, KEY_ALGORITHM));
            byte[] decryptResult = cipher.doFinal(decrypt);
            xor(decryptResult, salt);
            return decryptResult;
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    private static void xor(byte[] source, byte[] salt) {
        int saltLen = salt.length;

        for(int i = 0; i < source.length; ++i) {
            source[i] ^= salt[i % saltLen];
        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 使用示例
        String content = "test";
        byte[] key = "1234567812345678".getBytes("UTF-8");
        // 值为 ba572c602f340fd8be26038a0b79f107
        String encrypt = AESUtil.encryptHex(content.getBytes("UTF-8"), key);
        System.out.println(encrypt);
        // 值为 test
        String decrypt = AESUtil.decryptHex(encrypt.toCharArray(), key, "UTF-8");
        System.out.println(decrypt);
    }
}