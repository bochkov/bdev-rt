package sb.bdev.text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class HumanSize {

    public static final Symbols US = new Symbols.US();
    public static final Symbols RU = new Symbols.RU();

    public interface Symbols {
        String[] symbols();

        class US implements Symbols {
            private static final String[] SIZES = {"B", "kB", "MB", "GB", "TB"};

            @Override
            public String[] symbols() {
                return SIZES;
            }
        }

        class RU implements Symbols {
            private static final String[] SIZES = {"Б", "кБ", "МБ", "ГБ", "ТБ"};

            @Override
            public String[] symbols() {
                return SIZES;
            }
        }
    }

    private final Number size;
    private final Symbols symbols;
    private final int round;

    public HumanSize(Number size) {
        this(size, US);
    }

    public HumanSize(Number size, Symbols symbols) {
        this(size, symbols, -1);
    }

    @Override
    public String toString() {
        int rang = 0;
        double b = size.doubleValue();
        while (b > 1024 && rang < symbols.symbols().length - 1) {
            ++rang;
            b = b / 1024.;
        }
        if (round >= 0) {
            BigDecimal bd = new BigDecimal(Double.toString(b));
            bd = bd.setScale(round, RoundingMode.HALF_UP);
            b = bd.doubleValue();
        }
        return String.format("%s %s", b, symbols.symbols()[rang]);
    }
}
