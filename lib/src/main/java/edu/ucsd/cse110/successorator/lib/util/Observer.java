package edu.ucsd.cse110.successorator.lib.util;

import org.jetbrains.annotations.Nullable;

/**
 * An observer of a subject.
 *
 * @param <T> The type of the subject.
 */
public interface Observer<T> {
    /**
     * Called when the subject changes with the new value.
     *
     * @param value The new value of the subject.
     * @apiNote A null value indicates that the subject has no value yet.
     */
    void onChanged(@Nullable T value);
}
