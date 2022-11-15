package sb.bdev.util.color;

import java.awt.*;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class HexColorTest {

    record ColorData(String str, Color color) {
    }

    private static Stream<ColorData> sources() {
        return Stream.of(
                new ColorData("#000000", Color.BLACK),
                new ColorData("FF0000", Color.RED),
                new ColorData("#00FF00", Color.GREEN),
                new ColorData("0000FF", Color.BLUE),
                new ColorData("#FFFFFF", Color.WHITE)
        );
    }

    @ParameterizedTest
    @MethodSource("sources")
    void testToHexString(ColorData data) {
        Assertions.assertThat(HexColor.asColor(data.str)).isEqualTo(data.color);
        String fullStr = data.str.startsWith("#") ?
                data.str :
                "#" + data.str;
        Assertions.assertThat(HexColor.asString(data.color)).isEqualTo(fullStr);
    }
}
