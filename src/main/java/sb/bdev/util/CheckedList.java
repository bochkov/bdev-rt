package sb.bdev.util;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public final class CheckedList {

    private final Object obj;

    public <T> List<T> itemClass(Class<T> clz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?> list) {
            for (Object item : list) {
                try {
                    result.add(clz.cast(item));
                } catch (Exception ex) {
                    LOG.debug("Cannot cast {} to {}", item, clz);
                }
            }
        }
        return result;
    }

}
