package sb.bdev.img;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import sb.bdev.util.FileEx;

@RequiredArgsConstructor
public final class Img {

    private final File source;

    public String asBase64() throws IOException {
        String header = String.format(
                "data:image/%s;base64,",
                new FileEx(source).extension()
        );
        String encodedBytes = new String(
                Base64.getEncoder().encode(
                        Files.readAllBytes(source.toPath())
                )
        ).replaceAll("\r?\n", "");
        return String.format("%s%s", header, encodedBytes);
    }
}
