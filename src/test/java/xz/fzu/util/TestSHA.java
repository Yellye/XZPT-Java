package xz.fzu.util;


import org.junit.Test;

/**
 * @author Murphy
 * @date 2019/4/20 21:13
 */
public class TestSHA {

    @Test
    public void main() {
        try {
            String inputStr = "This is a password!";
            System.out.println("加密前的数据:" + inputStr);
            System.out.println("加密后的数据:" + Sha.encrypt(inputStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}