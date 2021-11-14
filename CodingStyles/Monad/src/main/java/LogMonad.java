import com.sun.media.jfxmedia.logging.Logger;

import java.util.Objects;
import java.util.function.Function;

public class LogMonad<T> {
    private T value;

    private LogMonad(T value) {
        this.value = value;
    }

    private LogMonad() {
        this.value = null;
    }

    public static <T> LogMonad<T> of(T value) {
        return new LogMonad<T>(value);
    }

    public <M> LogMonad<M> flatMap(Function<T, LogMonad<M>> function) {
        Objects.requireNonNull(function, "function is null");
        try {
            return function.apply(this.value);
        } catch (Exception e) {
            return new LogMonad<M>();
        }
    }

    public LogMonad<T> log() {
        System.out.println(String.format("value: %s",value.toString()));
        return this;
    }
}
