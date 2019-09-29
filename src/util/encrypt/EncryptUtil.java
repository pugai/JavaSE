package util.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lji on 2016/12/29.
 * 加解密工具类
 */
public class EncryptUtil {

    public static String DEFAULT_ENCODING = "UTF-8";

    private final static String digestKeyStr = "3go8&$8*3*3h0k(2)2";
    private final static int DIGEST_KEY_LEN = digestKeyStr.length();
    private static byte[] digestKey;
    private static MessageDigest alg;

    private final static String HmacSHA256 = "HmacSHA256";

    private final static String salt = "yuedu_file_suffix_";

    static {
        try {
            digestKey = digestKeyStr.getBytes("ISO_8859_1");
            alg = MessageDigest.getInstance("MD5");
        } catch (UnsupportedEncodingException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    /**
     * NOS的加密方式
     * @param src
     * @return
     */
    public synchronized static String base64edDigest(String src) {
        String dgst = base64Enc(MD5Digest(simpleKeyXOR(src, digestKey,
                DIGEST_KEY_LEN)));
        return dgst;
    }

    private static byte[] simpleKeyXOR(String src, byte[] key, int keyLen) {
        byte[] bsrc = null;
        try {
            bsrc = src.getBytes("ISO_8859_1");
            for (int i = 0; i < bsrc.length; i++) {
                bsrc[i] = (byte) (bsrc[i] ^ key[i % keyLen]);
            }
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return bsrc;
    }

    private static byte[] MD5Digest(byte[] bsrc) {
        alg.update(bsrc);
        return alg.digest();
    }

    private static String base64Enc(byte[] bsrc) {
        String retVal = Base64.encodeBase64String(bsrc);
        retVal = replaceSpecChars(retVal);
        return retVal;
    }

    private static String replaceSpecChars(String in) {
        char[] ca = in.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == '/')
                ca[i] = '_';
            else if (ca[i] == '+')
                ca[i] = '-';
        }
        return new String(ca);
    }

    public static String AESEncrypt(String data, String key) {
        try {
            String _key = Base64.encodeBase64String((key + salt).getBytes(DEFAULT_ENCODING)).substring(0, 16);
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(_key.getBytes(DEFAULT_ENCODING), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, zeroIv);
            byte[] encryptedData = cipher.doFinal(data.getBytes(DEFAULT_ENCODING));
            return Base64.encodeBase64String(encryptedData);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        long contentId = 8796093024057603960L;
        String contentKey = EncryptUtil.base64edDigest(String.valueOf(contentId)) + "/" + contentId;
        System.out.println(contentKey);
    }
}
