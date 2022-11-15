package sb.bdev.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomListTest {

    private List<String> source;

    @BeforeEach
    void setUp() {
        this.source = new ArrayList<>(ITEMS);
    }

    @Test
    void shuffleNotContainDups() {
        List<String> shuffled = new RandomList<>(source);
        for (int i = 0; i < shuffled.size(); ++i) {
            for (int j = i + 1; j < shuffled.size(); ++j) {
                if (shuffled.get(i).equals(shuffled.get(j)))
                    Assertions.fail("Contain dups");
            }
        }
    }

    @Test
    void shuffledNotEqualsSourceAndTarget() {
        List<String> shuffled = new RandomList<>(source);
        Assertions.assertThat(source.toArray()).isNotEqualTo(shuffled.toArray());
    }

    private static final List<String> ITEMS = Arrays.asList(
            "Автомобиль", "Квартира", "Компьютер", "Стул", "Стол",
            "Кресло", "Шкаф", "Ботинки", "Брюки", "Свитер", "Телевизор",
            "Диван", "Кукла", "Мяч", "Вилка", "Ложка", "Кружка",
            "Тарелка", "Насос", "Флаг", "Значок"
    );
}