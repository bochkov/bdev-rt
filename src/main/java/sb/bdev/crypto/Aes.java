package sb.bdev.crypto;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Aes {

    private static final IvParameterSpec SPEC = new IvParameterSpec(new byte[16]);
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private final String password;
    private final String salt;

    public SecretKey key(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    public String encrypt(String source) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key(password, salt), SPEC);
        return Base64.getEncoder().encodeToString(cipher.doFinal(source.getBytes()));
    }

    public String decrypt(String source) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key(password, salt), SPEC);
        return new String(cipher.doFinal(Base64.getDecoder().decode(source)));
    }

}
