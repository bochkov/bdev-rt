package sb.bdev.util;

import java.io.File;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FileEx {

    private final File file;

    public String extension() {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

}
