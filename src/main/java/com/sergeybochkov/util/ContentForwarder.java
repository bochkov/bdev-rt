package com.sergeybochkov.util;

import javax.swing.text.JTextComponent;
import java.io.*;

public class ContentForwarder {

    private static final int BUFFER_SIZE = 4089;

    public static void startForwarding(final InputStream from, final OutputStream to) {
        new Thread(() -> {
            byte[] buffer = new byte[BUFFER_SIZE];
            try {
                for (int r = 0; r >= 0; r = from.read(buffer))
                    if (r > 0)
                        to.write(buffer, 0, r);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }, "ContentForwarder").start();
    }

    public static void startForwarding(final InputStream from, final JTextComponent to) throws IOException {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(from, System.getProperty("file.encoding")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    to.setText(to.getText() + "\n" + line);
                    to.setCaretPosition(to.getDocument().getLength());
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }, "ContentForwarder").start();
    }
}
