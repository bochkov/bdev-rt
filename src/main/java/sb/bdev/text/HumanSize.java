package sb.bdev.text;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class HumanSize {

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

    private final Long size;
    private final Symbols symbols;

    public HumanSize(Long size) {
        this(size, new Symbols.US());
    }

    @Override
    public String toString() {
        int rang = 0;
        double b = size;
        while (b > 1000 && rang < symbols.symbols().length - 1) {
            ++rang;
            b = b / 1000.;
        }
        return String.format("%s %s", b, symbols.symbols()[rang]);
    }
}
