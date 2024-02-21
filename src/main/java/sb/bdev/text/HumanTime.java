package sb.bdev.text;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class HumanTime {

    public static final int SECOND = 1000;
    public static final int MINUTE = SECOND * 60;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;

    public static final Symbols US = new Symbols.US();
    public static final Symbols RU = new Symbols.RU();

    public interface Symbols {
        String[] symbols();

        class US implements Symbols {
            private static final String[] TIMES = {"d", "h", "m", "s"};

            @Override
            public String[] symbols() {
                return TIMES;
            }
        }

        class RU implements Symbols {
            private static final String[] TIMES = {"дн", "ч", "мин", "с"};

            @Override
            public String[] symbols() {
                return TIMES;
            }
        }
    }

    private final long times;
    private final Symbols symbols;

    public HumanTime(long millis) {
        this(millis, US);
    }

    @Override
    public String toString() {
        long diff = Math.abs(times);
        long d = diff / DAY;
        diff %= DAY;
        long h = diff / HOUR;
        diff %= HOUR;
        long m = diff / MINUTE;
        diff %= MINUTE;
        long s = diff / SECOND;
        long ss = diff % SECOND;

        StringBuilder str = new StringBuilder();
        if (d > 0)
            str.append(String.format("%d %s ", d, symbols.symbols()[0]));
        if (h > 0 || (d > 0 && (m > 0 || ss > 0)))
            str.append(String.format("%d %s ", h, symbols.symbols()[1]));
        if (m > 0 || (h > 0 && ss > 0))
            str.append(String.format("%d %s ", m, symbols.symbols()[2]));
        if (ss > 0)
            str.append(String.format("%d.%03d %s", s, ss, symbols.symbols()[3]));
        return str.toString().trim();
    }
}
