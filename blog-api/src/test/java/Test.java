import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 17:11
 */

public class Test {

    private static final  String salt = "Sivan819!@#";

    public static void main(String[] args) {
        String password = "123456";
        password = DigestUtils.md5Hex(password + salt);
        System.out.println(password);
    }

}

