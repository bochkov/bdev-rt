package sb.bdev.ui.directorybrowser;

import java.awt.*;
import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DirectoryBrowserTest {

    @Test
    void testDefault() {
        try {
            DirectoryBrowser dbro = new DirectoryBrowser(null, "Hello world", Dialog.ModalityType.MODELESS);
            dbro.expand(new File(System.getProperty("user.dir")).getAbsolutePath());
            Assertions.assertThat(dbro.selected().get(0)).isEqualTo(new File(System.getProperty("user.dir")));
        } catch (HeadlessException ex) {
            // skip test
        }
    }
}