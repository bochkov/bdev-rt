package sb.bdev.util;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@RequiredArgsConstructor
public final class Exec {

    private final int threads;
    private final List<Data> data = new ArrayList<>();

    public void add(Callable<?> callable, Consumer<ExecutionException> ex) {
        data.add(new Data(callable, ex));
    }

    public void run() {
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        for (Data d : data) {
            d.future.set(
                    exec.submit(d.callable)
            );
        }
        for (Data d : data) {
            try {
                Future<?> future = d.future.get();
                future.get();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException ex) {
                d.ex.accept(ex);
            }
        }
        exec.shutdown();
    }

    @RequiredArgsConstructor
    private static final class Data {
        private final Callable<?> callable;
        private final Consumer<ExecutionException> ex;
        private final AtomicReference<Future<?>> future = new AtomicReference<>();
    }
}
