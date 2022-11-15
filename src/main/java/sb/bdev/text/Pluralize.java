package sb.bdev.text;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Pluralize {

    public static final int ONE = 0;
    public static final int MUL1 = 1;
    public static final int MUL2 = 2;

    private final int count;

    private int asCase() {
        int c = count;
        if ((c % 10 == 1) && (c % 100 != 11)) {
            return ONE;
        } else if ((c % 10 >= 2) && (c % 10 <= 4) && (c % 100 < 10 || c % 100 >= 20)) {
            return MUL1;
        } else {
            return MUL2;
        }
    }

    public String format(String... dict) {
        String[] txt = new String[3];
        System.arraycopy(dict, 0, txt, 0, dict.length);
        String last = null;
        for (int i = 0; i < txt.length; ++i) {
            if (txt[i] != null)
                last = txt[i];
            else
                txt[i] = last;
        }
        return String.format("%d %s", count, txt[asCase()]);
    }
}
