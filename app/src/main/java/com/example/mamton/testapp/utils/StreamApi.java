package com.example.mamton.testapp.utils;

import com.annimon.stream.Stream;

public class StreamApi {

    /**
     * Use {@link Stream#ofNullable(Iterable)} instead.
     */
    @Deprecated
    public static <T> Stream<T> safeOf(Iterable<T> iterable) {
        return iterable == null ? Stream.empty() : Stream.of(iterable);
    }

    @SafeVarargs
    public static <T> Stream<T> safeOf(final T... iterable) {
        return iterable == null ? Stream.empty() : Stream.of(iterable);
    }
}
