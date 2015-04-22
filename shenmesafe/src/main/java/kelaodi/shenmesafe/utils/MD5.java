package kelaodi.shenmesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2015/4/22.
 */
public class MD5 {

    public static String MD5(String MD5string) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] begin = digest.digest(MD5string.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte middle : begin) {
                int number = middle & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            String result = sb.toString();
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
