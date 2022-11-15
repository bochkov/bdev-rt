package sb.bdev.ui.common;

import java.awt.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorChooseTest {

    @Test
    void test() {
        Assertions.assertThat(new ColorChoose(null).selectedColor()).isEqualTo(Color.WHITE);
    }

    @Test
    void selectedIsInit() {
        Assertions.assertThat(new ColorChoose(null, Color.RED).selectedColor()).isEqualTo(Color.RED);
    }
}