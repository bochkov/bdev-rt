package sb.bdev.ui.common;

import java.awt.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorChooseTest {

    @Test
    void test() {
        try {
            Assertions.assertThat(new ColorChoose(null).selectedColor()).isEqualTo(Color.WHITE);
        } catch (HeadlessException ex) {
            // skip test
        }
    }

    @Test
    void selectedIsInit() {
        try {
            Assertions.assertThat(new ColorChoose(null, Color.RED).selectedColor()).isEqualTo(Color.RED);
        } catch (HeadlessException ex) {
            // skip test
        }
    }
}