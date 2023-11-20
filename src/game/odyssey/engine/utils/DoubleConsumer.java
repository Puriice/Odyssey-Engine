package game.odyssey.engine.utils;

@FunctionalInterface
public interface DoubleConsumer<T, U> {
    void accept(T t, U u);
}
