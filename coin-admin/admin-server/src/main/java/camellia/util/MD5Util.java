package camellia.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 0:22
 */
public class MD5Util {
    public static String stringToMD5 (String content) {
        MessageDigest messageDigest = null;
        String contentMD5 = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes());
            contentMD5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return contentMD5;
    }
}
