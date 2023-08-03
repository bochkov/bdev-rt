package sb.bdev.text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class HumanSpeed {

    public static final Symbols US = new Symbols.US();
    public static final Symbols RU = new Symbols.RU();

    public interface Symbols {

        String[] symbols();

        class US implements Symbols {
            private static final String[] SPEEDS = {"B/s", "kB/s", "MB/s", "GB/s", "TB/s"};

            @Override
            public String[] symbols() {
                return SPEEDS;
            }
        }

        class RU implements Symbols {
            private static final String[] SPEEDS = {"Б/с", "кБ/с", "МБ/с", "ГБ/с", "ТБ/с"};

            @Override
            public String[] symbols() {
                return SPEEDS;
            }
        }
    }

    private final Number speed;
    private final Symbols symbols;
    private final int round;

    public HumanSpeed(Number size) {
        this(size, US, -1);
    }

    public HumanSpeed(String speed, Symbols symbols, int round) {
        this.symbols = symbols;
        String[] parts = speed.split(" ");
        var multy = 0;
        var value = Double.parseDouble(parts[0].replace(",", "."));
        for (String sp : symbols.symbols()) {
            if (parts[1].equals(sp))
                break;
            ++multy;
        }
        this.speed = multy == 0 ?
                value :
                value * multy * 1000;
        this.round = round;
    }

    @Override
    public String toString() {
        double res = speed.doubleValue();
        var rang = 0;
        while (res >= 1000) {
            res /= 1000;
            ++rang;
        }

        if (round >= 0) {
            BigDecimal bd = new BigDecimal(Double.toString(res));
            bd = bd.setScale(round, RoundingMode.HALF_UP);
            res = bd.doubleValue();
        }

        return String.format("%s %s", res, symbols.symbols()[rang]);
    }
}
