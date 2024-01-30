package edu.ucsd.cse110.successorator.lib.util;

import androidx.annotation.Nullable;

public interface Subject<T> {
    /**
     * Gets the current value of the subject. May be null.
     *
     * @return The current (possibly null) value of the subject.
     */
    @Nullable
    T getValue();


    /**
     * Adds an observer to the subject. The observer will be notified immediately of the subject's
     * current value (will be null if the subject has no value yet).
     *
     * @param observer The observer to add.
     */
    void observe(Observer<T> observer);

    /**
     * Removes an observer from the subject.
     *
     * @param observer The observer to remove.
     */
    void removeObserver(Observer<T> observer);
}
