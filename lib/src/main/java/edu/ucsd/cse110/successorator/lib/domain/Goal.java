package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Goal implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String content;

    private final @NonNull boolean finished;

    private final @NonNull boolean fromRecurring;

    public Goal(@Nullable Integer id, @NonNull String content, @NonNull boolean finished) {
        this.id = id;
        this.content = content;
        this.finished = finished;
        this.fromRecurring = false;
    }

    public Goal(@Nullable Integer id, @NonNull String content,
                @NonNull boolean finished, @NonNull boolean fromRecurring) {
        this.id = id;
        this.content = content;
        this.finished = finished;
        this.fromRecurring = fromRecurring;
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String content() {
        return content;
    }

    public @NonNull boolean finished() {
        return finished;
    }

    public @NonNull boolean getFinished() {
        return this.finished;
    }

    public Goal withId(int id) {
        return new Goal(id, this.content, this.finished);
    }

    public @NonNull boolean isFromRecurring() {
        return this.fromRecurring;
    }

    public Goal withFinished(boolean finished) {
        return new Goal(this.id, this.content, finished);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(content, goal.content) && (finished == goal.finished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, finished);
    }

    @Override
    public String toString() {
        return content;
    }
}
