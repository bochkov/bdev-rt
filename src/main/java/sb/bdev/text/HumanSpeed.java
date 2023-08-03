package sb.bdev.text;

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

    public HumanSpeed(Number size) {
        this(size, US);
    }

    public HumanSpeed(String speed, Symbols symbols) {
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
    }

    @Override
    public String toString() {
        double res = speed.doubleValue();
        var rang = 0;
        while (res >= 1000) {
            res /= 1000;
            ++rang;
        }
        return String.format("%.2f %s", res, symbols.symbols()[rang]);
    }
}
