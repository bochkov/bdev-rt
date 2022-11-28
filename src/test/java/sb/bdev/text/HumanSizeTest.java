package sb.bdev.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HumanSizeTest {

    @Test
    void test() {
        HumanSize sz = new HumanSize(100L);
        Assertions.assertThat(sz).hasToString("100.0 B");
    }

    @Test
    void testNegative() {
        HumanSize sz = new HumanSize(-100L);
        Assertions.assertThat(sz.toString()).isNotNull();
    }

    @Test
    void testKBytes() {
        HumanSize sz = new HumanSize(2048L);
        Assertions.assertThat(sz).hasToString("2.0 kB");
    }

    @Test
    void testMBytesRu() {
        HumanSize sz = new HumanSize(65300411L, HumanSize.RU);
        Assertions.assertThat(sz).hasToString("62.27532482147217 МБ");
    }

    @Test
    void testGBytes() {
        HumanSize sz = new HumanSize(2365300416L);
        Assertions.assertThat(sz).hasToString("2.2028576731681824 GB");
    }

    @Test
    void testRound() {
        HumanSize sz = new HumanSize(2365300416L, HumanSize.US, 2);
        Assertions.assertThat(sz).hasToString("2.2 GB");
    }

    @Test
    void testOverflow() {
        HumanSize sz = new HumanSize((long) Math.pow(2, 64));
        Assertions.assertThat(sz.toString()).isNotNull();
    }

}