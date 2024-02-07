package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Goal implements Serializable {
    private final @NonNull String content;

    public Goal(@NonNull String content) {
        this.content = content;
    }

    public @NonNull String getContent() {
        return content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(content, goal.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
