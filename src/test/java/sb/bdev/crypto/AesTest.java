package sb.bdev.crypto;

import java.security.GeneralSecurityException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AesTest {

    String pass = "f0v20fv sdvkejrt2fsd";
    String salt = "cvmm2vscgdfhy";

    @Test
    void test() throws GeneralSecurityException {
        String plainText = "hello_world";
        String password = "njg2n03rqsdsdg";
        String salt = "vcnvwetwehfgjghj";
        Aes aes = new Aes(password, salt);
        String cipherText = aes.encrypt(plainText);
        String decryptText = aes.decrypt(cipherText);
        Assertions.assertThat(decryptText).isEqualTo(plainText);
    }

    @Test
    void testEncrypt() throws GeneralSecurityException {
        String text = "hello world";
        String enc = new Aes(pass, salt).encrypt(text);
        Assertions.assertThat(enc).isEqualTo("3HCXlRRDpq/m2z0V8yba3A==");
    }

    @Test
    void testDecrypt() throws GeneralSecurityException {
        String dec = new Aes(pass, salt).decrypt("3HCXlRRDpq/m2z0V8yba3A==");
        Assertions.assertThat(dec).isEqualTo("hello world");
    }
}