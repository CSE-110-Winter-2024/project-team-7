package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Just a dummy domain model that does nothing in particular. Delete me.
 */
public class Dummy {
    private final @Nullable Integer id;
    private final @Nullable String foo;
    private final @NotNull String bar;

    public Dummy(
        @Nullable Integer id,
        @Nullable String foo,
        @NotNull String bar
    ) {
        this.id = id;
        this.foo = foo;
        this.bar = bar;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getFoo() {
        return foo;
    }

    @NotNull
    public String getBar() {
        return bar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dummy dummy = (Dummy) o;
        return Objects.equals(id, dummy.id) && Objects.equals(foo, dummy.foo) && Objects.equals(bar, dummy.bar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foo, bar);
    }
}
