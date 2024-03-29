package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SimpleGoalLists implements GoalLists {
    private List<Goal> unfinished;
    private List<Goal> finished;
    private int size;

    public SimpleGoalLists() {
        unfinished = new ArrayList<Goal>();
        finished = new ArrayList<Goal>();
        size = 0;
    }

    public SimpleGoalLists(List<Goal> unfinished, List<Goal> finished) {
        this.unfinished = unfinished;
        this.finished = finished;
        size = unfinished.size() + finished.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int unfinishedSize() {
        return unfinished.size();
    }

    @Override
    public int finishedSize() {
        return finished.size();
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public Goal get(int index) {
        if(index >= unfinished.size())
            return finished.get(index - unfinished.size());
        return unfinished.get(index);
    }

    @Override
    public List<Goal> getFinishedGoals() {
        return finished;
    }

    @Override
    public List<Goal> getUnfinishedGoals() { return unfinished; }

    @Override
    public void add(Goal goal) {
        unfinished.add(goal);
        size++;
    }

    @Override
    public void finishTask(Goal goal) {
        var index = unfinished.indexOf(goal);
        if (index < unfinished.size()) {
            unfinished.remove(index);
            finished.add(goal.withFinished(true));
        }
    }

    @Override
    public void undoFinishTask(Goal goal) {
        var index = finished.indexOf(goal);
        if(index < finished.size()) {
            finished.remove(index);
            unfinished.add(goal.withFinished(false));
        }
    }

    @Override
    public void clearFinished() {
        finished.clear();
        size = unfinished.size();
    }

    @Override
    public void clearUnfinished() {
        unfinished.clear();
        size = finished.size();
    }

    public List<Goal> getGoalsByContext(String context, boolean isFinished) {
        if (isFinished) {
            return finished.stream()
                    .filter(goal -> goal.getContext().equalsIgnoreCase(context))
                    .collect(Collectors.toList());
        } else {
            return unfinished.stream()
                    .filter(goal -> goal.getContext().equalsIgnoreCase(context))
                    .collect(Collectors.toList());
        }
    }

    public String toString() {
        String retval = "unfinished: \n";
        for(int i = 0; i < size; i++) {
            if(i == unfinishedSize())
                retval += "finished: \n";
            retval += i + get(i).toString() + "\n";
        }
        return retval;
    }

    public List<Goal> getFinishedGoalsByContext(String context) {
        return finished.stream()
                .filter(goal -> goal.getContext().equalsIgnoreCase(context))
                .collect(Collectors.toList());
    }

    public List<Goal> getUnfinishedGoalsByContext(String context) {
        return unfinished.stream()
                .filter(goal -> goal.getContext().equalsIgnoreCase(context))
                .collect(Collectors.toList());
    }

}
