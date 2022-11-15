package sb.bdev.util;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.text.JTextComponent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public final class ForwardedContent {

    public interface WriteTo {

        void write(InputStream is) throws IOException;

        @RequiredArgsConstructor
        class DEFAULT implements WriteTo {

            private final OutputStream out;

            @Override
            public void write(InputStream is) throws IOException {
                byte[] buffer = new byte[BUFFER_SIZE];
                for (int r = 0; r >= 0; r = is.read(buffer)) {
                    if (r > 0)
                        out.write(buffer, 0, r);
                }
            }
        }

        class NULL implements WriteTo {
            @Override
            public void write(InputStream is) {
                //
            }
        }

        @RequiredArgsConstructor
        class COMPONENT implements WriteTo {

            private final JTextComponent cmp;

            @Override
            public void write(InputStream is) throws IOException {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, System.getProperty("file.encoding")))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        cmp.setText(cmp.getText() + "\n" + line);
                        cmp.setCaretPosition(cmp.getDocument().getLength());
                    }
                }
            }
        }
    }

    private static final int BUFFER_SIZE = 4089;
    private static final AtomicInteger COUNT = new AtomicInteger(0);

    private final WriteTo to;
    private final List<InputStream> from;

    public ForwardedContent(OutputStream out, InputStream... from) {
        this(
                new WriteTo.DEFAULT(out),
                Collections.unmodifiableList(Arrays.asList(from))
        );
    }

    public void forward() {
        ExecutorService service = Executors.newFixedThreadPool(
                from.size(),
                runnable -> new Thread(
                        runnable,
                        String.format("forwarded-content-%d", COUNT.getAndIncrement())
                )
        );
        CompletionService<Object> complete = new ExecutorCompletionService<>(service);
        for (final InputStream is : from) {
            complete.submit(() -> {
                to.write(is);
                return new Object();
            });
        }
        for (InputStream ignored : from) {
            try {
                complete.take().get();
            } catch (Exception ex) {
                LOG.warn(ex.getCause().getMessage(), ex.getCause());
                Thread.currentThread().interrupt();
            }
        }
        service.shutdown();
    }
}
