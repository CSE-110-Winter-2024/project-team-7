package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;


public class GoalListTest {

    @Test
    public void addTest() {
        ArrayList<Goal> testList = new ArrayList<>();
        ArrayList<Goal> emptyList = new ArrayList<>();
        Goal goal1 = new Goal("one");
        Goal goal2 = new Goal("two");
        testList.add(goal1);
        testList.add(goal2);
        GoalLists actual = new GoalLists();
        GoalLists expected = new GoalLists(testList, emptyList);

        actual.add(goal1);
        actual.add(goal2);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void sizeTests() {
        ArrayList<Goal> testList = new ArrayList<Goal>();
        Goal goal1 = new Goal("one");
        Goal goal2 = new Goal("two");
        testList.add(goal1);
        testList.add(goal2);

        GoalLists goalLists = new GoalLists(testList, testList);

        assertEquals(4, goalLists.size());
        assertEquals(2, goalLists.unfinishedSize());

        for(int i = 1; i < 25; i++) {
            goalLists.add(new Goal("" + i));
            assertEquals(4 + i, goalLists.size());
            assertEquals(2 + i, goalLists.unfinishedSize());
        }
    }
}
