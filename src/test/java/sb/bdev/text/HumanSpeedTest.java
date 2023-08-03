package sb.bdev.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

final class HumanSpeedTest {

    @Test
    void test() {
        HumanSpeed speed = new HumanSpeed(233921);
        Assertions.assertThat(speed).hasToString("233.921 kB/s");
    }

    @Test
    void testRound() {
        HumanSpeed speed = new HumanSpeed(34234234234L, HumanSpeed.US, 2);
        Assertions.assertThat(speed).hasToString("34.23 GB/s");
    }
}
