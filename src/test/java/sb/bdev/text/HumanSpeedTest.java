package sb.bdev.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

final class HumanSpeedTest {

    @Test
    void test() {
        HumanSpeed speed = new HumanSpeed(233921);
        Assertions.assertThat(speed.toString()).isEqualTo("233,92 kB/s");
    }
}
