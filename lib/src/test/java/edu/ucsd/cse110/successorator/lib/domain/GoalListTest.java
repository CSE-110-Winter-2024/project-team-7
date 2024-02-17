package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;


public class GoalListTest {

    /* OUTDATED TESTS
    @Test
    public void addTest() {
        ArrayList<Goal> testList = new ArrayList<>();
        ArrayList<Goal> emptyList = new ArrayList<>();
        Goal goal1 = new Goal(null, "one", false);
        Goal goal2 = new Goal(null,"two", false);
        testList.add(goal1);
        testList.add(goal2);
        SimpleGoalLists actual = new SimpleGoalLists();
        SimpleGoalLists expected = new SimpleGoalLists(testList, emptyList);

        actual.add(goal1);
        actual.add(goal2);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void sizeTests() {
        ArrayList<Goal> testList = new ArrayList<Goal>();
        Goal goal1 = new Goal(null,"one", false);
        Goal goal2 = new Goal(null, "two", false);
        testList.add(goal1);
        testList.add(goal2);

        GoalLists goalLists = new SimpleGoalLists(testList, testList);

        assertEquals(4, goalLists.size());
        assertEquals(2, goalLists.unfinishedSize());

        for(int i = 1; i < 25; i++) {
            goalLists.add(new Goal(null,"" + i, false));
            assertEquals(4 + i, goalLists.size());
            assertEquals(2 + i, goalLists.unfinishedSize());
        }
    }

    @Test
    public void testGetFinishedGoals() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(0,100);

        GoalLists testList = new SimpleGoalLists();
        ArrayList<Goal> expectedFinishedList = new ArrayList<>();

        for(int j = 0; j < randomNumber; j++){
            testList.add(new Goal(null,"goal" + j, false));
        }

        //finish goals
        for(int i = 0; i < randomNumber; i++){
            testList.finishTask(new Goal(null, "goal" + i, false));
        }

        for(int j = 0; j < randomNumber; j++){
            expectedFinishedList.add(new Goal(null,"goal" + j, false));
        }

        int i = 0;
        //check getFinishedGoals
        for(Goal expectedGoal:expectedFinishedList){
            assertEquals(expectedGoal, testList.getFinishedGoals().get(i));
            i++;
        }
    }

    @Test
    public void testGetUnfinishedGoals() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(0,100);

        GoalLists testList = new SimpleGoalLists();
        ArrayList<Goal> expectedUnfinishedList = new ArrayList<>();

        for(int j = 0; j < randomNumber; j++){
            testList.add(new Goal(null,"goal" + j, false));
        }

        for(int j = 0; j < randomNumber; j++){
            expectedUnfinishedList.add(new Goal(null,"goal" + j, false));
        }

        int i = 0;
        //check getUnfinishedGoals
        for(Goal expectedGoal:expectedUnfinishedList){
            assertEquals(expectedGoal, testList.getUnfinishedGoals().get(i));
            i++;
        }
    }

    @Test
    public void testUndoFinishTask() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(0,100);

        GoalLists testList = new SimpleGoalLists();
        ArrayList<Goal> expectedUnfinishedList = new ArrayList<>();

        for(int j = 0; j < randomNumber; j++){
            expectedUnfinishedList.add(new Goal(null, "goal" + j, false));
        }

        for(int j = 0; j < randomNumber; j++){
            testList.add(new Goal(null, "goal" + j, false));
        }

        //finish goals
        for(int i = 0; i < randomNumber; i++){
            testList.finishTask(new Goal(null, "goal" + i, false));
        }

        //undo finish goals
        for(int i = 0; i < randomNumber; i++){
            testList.undoFinishTask(new Goal(null, "goal" + i, false));
        }

        int i = 0;
        //check getUnfinishedGoals
        for(Goal expectedGoal:expectedUnfinishedList){
            assertEquals(expectedGoal, testList.getUnfinishedGoals().get(i));
            i++;
        }
    }

    @Test
    public void testClearFinished() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(0,100);

        GoalLists testList = new SimpleGoalLists();

        for(int j = 0; j < randomNumber; j++){
            testList.add(new Goal(null, "goal" + j, false));
        }

        //finish goals
        for(int i = 0; i < randomNumber; i++){
            testList.finishTask(new Goal(null, "goal" + i, false));
        }

        testList.clearFinished();
        assertEquals(0, testList.finishedSize());
    }
     */
}
