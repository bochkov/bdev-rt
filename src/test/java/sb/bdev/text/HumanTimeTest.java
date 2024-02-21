package sb.bdev.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HumanTimeTest {

    @Test
    void testSec() {
        HumanTime time = new HumanTime(2567L, HumanTime.RU);
        Assertions.assertThat(time).hasToString("2.567 с");
    }

    @Test
    void testMin() {
        HumanTime time = new HumanTime(257002L, HumanTime.RU);
        Assertions.assertThat(time).hasToString("4 мин 17.002 с");
    }

    @Test
    void testHour() {
        HumanTime time = new HumanTime(31257038L);
        Assertions.assertThat(time).hasToString("8 h 40 m 57.038 s");
    }

    @Test
    void testDays() {
        HumanTime time = new HumanTime(1231257038L);
        Assertions.assertThat(time).hasToString("14 d 6 h 0 m 57.038 s");
    }
}