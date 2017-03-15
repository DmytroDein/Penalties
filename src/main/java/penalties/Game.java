package penalties;


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


    public List<Integer> makeShotByPersonal(String player, String commandMark, int attemptScore) {

        makeShot(commandMark, attemptScore);
        return null;

    }

    public List<Integer> lastBy(String player) {
        return null;
    }
}


























