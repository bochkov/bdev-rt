package sb.bdev.crypto;

import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@RequiredArgsConstructor
public final class Aes {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final String FACTORY = "PBKDF2WithHmacSHA256";
    private static final int IV_LENGTH = 12;
    private static final int SALT_LENGTH = 16;
    private static final int ITER_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    private final String password;

    public String encrypt(String source) throws GeneralSecurityException {
        byte[] iv = randomBytes(IV_LENGTH);
        AlgorithmParameterSpec spec = new GCMParameterSpec(128, iv);

        byte[] salt = randomBytes(SALT_LENGTH);
        SecretKey secretKey = key(password, salt);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        byte[] encodedBytes = cipher.doFinal(source.getBytes());
        byte[] out = ByteBuffer.allocate(IV_LENGTH + SALT_LENGTH + encodedBytes.length)
                .put(iv)
                .put(salt)
                .put(encodedBytes)
                .array();
        return Base64.getEncoder().encodeToString(out);
    }

    public String decrypt(String source) throws GeneralSecurityException {
        byte[] in = Base64.getDecoder().decode(source);
        ByteBuffer buffer = ByteBuffer.wrap(in);

        byte[] iv = new byte[IV_LENGTH];
        buffer.get(iv);

        byte[] salt = new byte[SALT_LENGTH];
        buffer.get(salt);

        byte[] encodedBytes = new byte[buffer.remaining()];
        buffer.get(encodedBytes);

        SecretKey secretKey = key(password, salt);
        AlgorithmParameterSpec spec = new GCMParameterSpec(128, iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        return new String(cipher.doFinal(encodedBytes));
    }

    public SecretKey key(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITER_COUNT, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(FACTORY);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

}
