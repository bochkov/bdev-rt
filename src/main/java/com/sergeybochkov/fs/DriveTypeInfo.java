package com.sergeybochkov.fs;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DriveTypeInfo {

    public enum Info {
        FLOPPY, OPTICAL, NOT_WRITABLE
    }

    public static String[] findByInfo(File[] roots, Info info) {
        List<String> drives = new ArrayList<>();
        switch (info) {
            case FLOPPY:
                FileSystemView fsv = FileSystemView.getFileSystemView();
                for (File root: roots)
                    if (fsv.isFloppyDrive(root))
                        drives.add(root.toString());
                break;
            case OPTICAL:
            case NOT_WRITABLE:
                for (File root : roots)
                    if (!root.canWrite())
                        drives.add(root.toString());
                break;
        }

        String[] ret = new String[drives.size()];
        return drives.toArray(ret);
    }

    public static String[] findByInfo(Info info) {
        return findByInfo(File.listRoots(), info);
    }
}
