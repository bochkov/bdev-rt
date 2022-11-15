package sb.bdev.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RegexpTest {

    @Test
    void testUrlValid() {
        Assertions.assertThat(Regexp.isUrlValid("https://bitbucket.org/snippets/bochkov/89qE7/edit/")).isTrue();
    }
}
