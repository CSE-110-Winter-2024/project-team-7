package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

public interface RecurringGoalLists {
    int size();

    boolean empty();

    List<RecurringGoal> getRecurringGoals();

    List<RecurringGoal> getRecurringGoalsByContext(String context);

    int add(RecurringGoal rgoal);

    void delete(RecurringGoal rgoal);

    RecurringGoal get(int i);
}
