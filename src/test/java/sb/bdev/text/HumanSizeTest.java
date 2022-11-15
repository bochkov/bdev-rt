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
        Assertions.assertThat(sz).hasToString("2.048 kB");
    }

    @Test
    void testMBytesRu() {
        HumanSize sz = new HumanSize(65300411L, new HumanSize.Symbols.RU());
        Assertions.assertThat(sz).hasToString("65.300411 МБ");
    }

    @Test
    void testGBytes() {
        HumanSize sz = new HumanSize(2365300416L);
        Assertions.assertThat(sz).hasToString("2.365300416 GB");
    }

    @Test
    void testOverflow() {
        HumanSize sz = new HumanSize((long) Math.pow(2, 64));
        Assertions.assertThat(sz.toString()).isNotNull();
    }

}