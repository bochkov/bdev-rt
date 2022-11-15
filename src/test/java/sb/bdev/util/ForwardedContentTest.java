package sb.bdev.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ForwardedContentTest {

    private final byte[] buffer = new byte[]{'T', 'E', 'S', 'T'};

    private final ByteArrayInputStream bin = new ByteArrayInputStream(buffer);
    private final ByteArrayOutputStream bout = new ByteArrayOutputStream();

    @Test
    void testForward() {
        ForwardedContent fc = new ForwardedContent(bout, bin);
        fc.forward();
        Assertions.assertThat(bout.toByteArray()).isEqualTo(buffer);
    }

}