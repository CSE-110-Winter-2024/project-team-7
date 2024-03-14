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
    private final @NonNull String context;

    // Constructor without 'fromRecurring' and 'context' for backward compatibility
    public Goal(@Nullable Integer id, @NonNull String content, boolean finished) {
        this(id, content, finished, false, "");
    }

    public Goal(@Nullable Integer id, @NonNull String content, boolean finished, boolean fromRecurring, @NonNull String context) {
        this.id = id;
        this.content = content;
        this.finished = finished;
        this.fromRecurring = fromRecurring;
        this.context = context;
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
        return new Goal(id, this.content, this.finished, this.fromRecurring, this.context);
    }

    public @NonNull boolean isFromRecurring() {
        return this.fromRecurring;
    }

    public Goal withFinished(boolean finished) {
        return new Goal(this.id, this.content, finished, this.fromRecurring, this.context);
    }

    public @NonNull Goal copyWithoutId() {
        return new Goal(null, this.content, this.finished, this.fromRecurring, this.context);
    }

    public @NonNull String getContext() {
        return context;
    }

    public @NonNull Goal copyWithoutIdFinished() {
        return new Goal(null, this.content, true, this.fromRecurring, this.context);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return finished == goal.finished &&
                fromRecurring == goal.fromRecurring &&
                Objects.equals(id, goal.id) &&
                Objects.equals(content, goal.content) &&
                Objects.equals(context, goal.context); // Include context in equality check
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, finished, fromRecurring, context);
    }

    @Override
    public String toString() {
        return content + " [" + context + "]";
    }
}
