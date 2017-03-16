package penalties;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    public final int MAX_SERIES = 5;
    int leftCommandScore;
    int leftCommandAttepmts;
    int rightCommandScore;
    int rightCommandAttempts;
    boolean finished;

    public void Game() {
        leftCommandScore = 0;
        rightCommandScore = 0;
        finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getLeftCommandScore() {
        return leftCommandScore;
    }

    public int getRightCommandScore() {
        return rightCommandScore;
    }

    public void makeShot(String commandMark, int attemptScore) {
        if (!finished) {
            switch (commandMark) {
                case "LeftCommand":
                    if (leftCommandAttepmts < MAX_SERIES) {
                        leftCommandScore += attemptScore;
                        leftCommandAttepmts++;
                    }
                    break;
                case "RightCommand":
                    if (rightCommandAttempts < MAX_SERIES) {
                        rightCommandScore += attemptScore;
                        rightCommandAttempts++;
                    }
                    break;
                default:
            }
            if (checkEarlyTerminationConditions() || (leftCommandAttepmts == MAX_SERIES && rightCommandAttempts == MAX_SERIES)) {
                finished = true;
            }
        }
    }

    public int getLeftCommandAttempts() {
        return leftCommandAttepmts;
    }

    public int getRightCommandAttempts() {
        return rightCommandAttempts;
    }

    public boolean checkEarlyTerminationConditions() {
        int scoreDifference = Math.abs(leftCommandScore - rightCommandScore);
        //int attemptsDifference = Math.abs(leftCommandAttempts - rightCommandAttempts);
        if (leftCommandScore > rightCommandScore){
            if (scoreDifference > MAX_SERIES - rightCommandAttempts){
                return true;
            }
        } else if (leftCommandScore < rightCommandScore){
            if (scoreDifference > MAX_SERIES - leftCommandAttepmts){
                return true;
            }
        } else {
            if (scoreDifference > MAX_SERIES - leftCommandAttepmts){
                return true;
            }
        }
        //equal score
        return false;
    }

    protected int getPlayerCost(){
        return 0;
    }

    // Spied service call
    protected List<Integer> getPlayerStat(String playerName){
        return null;
    }


    public List<Integer> makeShotByPersonal(String player, String commandMark, int attemptScore) {
        makeShot(commandMark, attemptScore);
        return getPlayerStat("Messi");

    }

    /*public List<Integer> lastBy(String player) {
        return null;
    }*/

    public int costOfMissedPlayers(String commandName) {
        return 0;
    }

    public String getScore() {
        if (leftCommandAttepmts >= 7 && rightCommandAttempts >= 7){
            int leftCommandLoosersCost = costOfMissedPlayers("LeftCommand");
            int rightCommandLoosersCost = costOfMissedPlayers("RightCommand");

            return String.format("LeftCommand (%d) [%d] : RightCommand (%d) [%d]",
                    leftCommandScore, leftCommandLoosersCost ,rightCommandScore, rightCommandLoosersCost);
        }
        return String.format("LeftCommand (%d) : RightCommand (%d)", leftCommandScore, rightCommandScore);
    }
}


























