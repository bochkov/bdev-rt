package sb.bdev.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PluralizeTest {

    @Test
    void testCows() {
        String[] arr = new String[]{"коров"};
        Assertions.assertThat(new Pluralize(1).format(arr)).isEqualTo("1 коров");
        Assertions.assertThat(new Pluralize(2).format(arr)).isEqualTo("2 коров");
        Assertions.assertThat(new Pluralize(12).format(arr)).isEqualTo("12 коров");
    }

    @Test
    void testError() {
        String[] arr = new String[]{"ошибка", "ошибки", "ошибок"};
        Assertions.assertThat(new Pluralize(1).format(arr)).isEqualTo("1 ошибка");
        Assertions.assertThat(new Pluralize(2).format(arr)).isEqualTo("2 ошибки");
        Assertions.assertThat(new Pluralize(3).format(arr)).isEqualTo("3 ошибки");
        Assertions.assertThat(new Pluralize(4).format(arr)).isEqualTo("4 ошибки");
        Assertions.assertThat(new Pluralize(5).format(arr)).isEqualTo("5 ошибок");
        Assertions.assertThat(new Pluralize(10).format(arr)).isEqualTo("10 ошибок");
        Assertions.assertThat(new Pluralize(11).format(arr)).isEqualTo("11 ошибок");
        Assertions.assertThat(new Pluralize(12).format(arr)).isEqualTo("12 ошибок");
        Assertions.assertThat(new Pluralize(13).format(arr)).isEqualTo("13 ошибок");
        Assertions.assertThat(new Pluralize(15).format(arr)).isEqualTo("15 ошибок");
        Assertions.assertThat(new Pluralize(20).format(arr)).isEqualTo("20 ошибок");
        Assertions.assertThat(new Pluralize(21).format(arr)).isEqualTo("21 ошибка");
        Assertions.assertThat(new Pluralize(22).format(arr)).isEqualTo("22 ошибки");
        Assertions.assertThat(new Pluralize(25).format(arr)).isEqualTo("25 ошибок");
        Assertions.assertThat(new Pluralize(100).format(arr)).isEqualTo("100 ошибок");
        Assertions.assertThat(new Pluralize(1000).format(arr)).isEqualTo("1000 ошибок");
        Assertions.assertThat(new Pluralize(1001).format(arr)).isEqualTo("1001 ошибка");
        Assertions.assertThat(new Pluralize(1011).format(arr)).isEqualTo("1011 ошибок");
        Assertions.assertThat(new Pluralize(10000).format(arr)).isEqualTo("10000 ошибок");
        Assertions.assertThat(new Pluralize(10000000).format(arr)).isEqualTo("10000000 ошибок");
    }

    @Test
    void testDays() {
        String[] arr = new String[]{"день", "дней"};
        Assertions.assertThat(new Pluralize(61).format(arr)).isEqualTo("61 день");
    }

}