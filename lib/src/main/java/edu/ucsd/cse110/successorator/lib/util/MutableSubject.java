package edu.ucsd.cse110.successorator.lib.util;

public interface MutableSubject<T> extends Subject<T> {
    /**
     * Sets the value of the subject and notifies all observers immediately.
     * @param value The new value of the subject.
     */
    void setValue(T value);
}
