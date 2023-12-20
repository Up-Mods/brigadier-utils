package dev.upcraft.brigadier.utils.util;

import com.mojang.datafixers.util.Either;

import java.util.function.Consumer;

public class Result<T, ERROR> {

    private final Either<T, ERROR> value;

    private Result(Either<T, ERROR> value) {
        this.value = value;
    }

    public static <T, ERROR> Result<T, ERROR> success(T value) {
        return new Result<T, ERROR>(Either.left(value));
    }

    public static <ERROR> Result<Result.Success, ERROR> empty_success() {
        return success(Success.INSTANCE);
    }

    public static <T, ERROR> Result<T, ERROR> error(ERROR value) {
        return new Result<T, ERROR>(Either.right(value));
    }

    public void ifSuccess(Consumer<T> consumer) {
        value.left().ifPresent(consumer);
    }

    public void ifError(Consumer<ERROR> consumer) {
        value.right().ifPresent(consumer);
    }

    public boolean success() {
        return value.left().isPresent();
    }

    public boolean failed() {
        return value.right().isPresent();
    }

    public T value() {
        return value.left().orElseThrow();
    }

    public ERROR error() {
        return value.right().orElseThrow();
    }

    public Either<T, ERROR> asEither() {
        return value;
    }

    /**
     * A singleton representing a successful result with no value.
     * This is necessary because {@link Void} cannot be instantiated for use in {@link java.util.Optional}.
     */
    public enum Success {
        INSTANCE;
    }
}
