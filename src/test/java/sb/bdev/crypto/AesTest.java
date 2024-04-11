package sb.bdev.crypto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;

class AesTest {

    @Test
    void test() throws GeneralSecurityException {
        String plainText = "hello_world";
        String password = "njg2n03rqsdsdg";
        Aes aes = new Aes(password);
        String cipherText = aes.encrypt(plainText);
        String decryptText = aes.decrypt(cipherText);
        Assertions.assertThat(decryptText).isEqualTo(plainText);
    }

    @Test
    void testDecrypt() throws GeneralSecurityException {
        String dec = new Aes("f0v20fv sdvkejrt2fsd")
                .decrypt("QTQGiEERKX6gcLGzRai792SBnYuIInxk4xJmZwv7HBBv9WOsGsSZOZJJsqL6zjpjXPasSHDouQ==");
        Assertions.assertThat(dec).isEqualTo("hello world");
    }
}