package sb.bdev.util;

import java.util.*;

/**
 * Список, перемешанный по алгоритму Фишера-Йетса
 */
public final class RandomList<T> implements List<T> {

    private final List<T> source;
    private final Random rnd = new Random();
    private final List<List<T>> cache = new ArrayList<>();

    public RandomList(List<T> source) {
        this.source = Collections.unmodifiableList(source);
    }

    private List<T> shuffle() {
        List<T> target = new ArrayList<>(source);
        for (int i = 0; i < source.size(); ++i) {
            int j = rnd.nextInt(i + 1);
            target.set(i, target.get(j));
            target.set(j, source.get(i));
        }
        return target;
    }

    @Override
    public int size() {
        return this.source.size();
    }

    @Override
    public boolean isEmpty() {
        return this.source.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.source.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).iterator();
    }

    @Override
    public Object[] toArray() {
        if (cache.isEmpty())
            cache.add(shuffle());
        return cache.get(0).toArray();
    }

    @Override
    public <S> S[] toArray(S[] a) {
        if (cache.isEmpty())
            cache.add(shuffle());
        return cache.get(0).toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(source).containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (List<T> cached : cache) {
            cached.clear();
        }
        cache.clear();
    }

    @Override
    public T get(int index) {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (cache.isEmpty()) {
            cache.add(shuffle());
        }
        return cache.get(0).subList(fromIndex, toIndex);
    }
}
