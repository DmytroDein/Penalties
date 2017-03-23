package penalties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import penalties.exceptions.ShotException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameTest {

    private Game penaltieSerie;

    @Before
    public void initTest(){
        penaltieSerie = spy(new Game());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isFinishedTest(){
        penaltieSerie.finished = false;
        Assert.assertFalse(penaltieSerie.isFinished());
        penaltieSerie.finished = true;
        Assert.assertTrue(penaltieSerie.isFinished());
    }

    @Test
    public void getScoreTestForNewGame(){
        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), 0);
        Assert.assertEquals(penaltieSerie.getRightCommandScore(), 0);
    }

    @Test
    public void commandsScoreTest(){
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);

        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), 1);
        Assert.assertEquals(penaltieSerie.getRightCommandScore(), 2);
    }

    @Test
    public void attemptsCountTest(){
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);

        Assert.assertEquals(penaltieSerie.getLeftCommandAttempts(), 1);
        Assert.assertEquals(penaltieSerie.getRightCommandAttempts(), 2);
    }

    @Test
    public void fullGameScoreTest(){
        for(int i = 1; i <= penaltieSerie.MAX_SERIES; i++){
            penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
            penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        }
        Assert.assertEquals(penaltieSerie.isFinished(), true);
        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), penaltieSerie.MAX_SERIES);
        Assert.assertEquals(penaltieSerie.getRightCommandScore(), penaltieSerie.MAX_SERIES);
    }

    @Test
    public void earlyTerminationConditionTest() {
        final int fastEnd = penaltieSerie.MAX_SERIES / 2;
        for (int i = 1; i <= fastEnd; i++) {
            penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
            penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        }
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        Assert.assertEquals(penaltieSerie.checkEarlyTerminationConditions(), true);
        Assert.assertEquals(penaltieSerie.getLeftCommandAttempts(), fastEnd + 1);
        Assert.assertEquals(penaltieSerie.getRightCommandAttempts(), fastEnd);
    }

    @Test
    public void makeShotByPersonalTest(){
        when(penaltieSerie.getPlayerStat("Messi")).thenReturn(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0)));
        List<Integer> testedStat = penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        List<Integer> expectedStat = new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0));
        Assert.assertEquals(penaltieSerie.getLeftCommandAttempts(), 1);
        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), 1);
        Assert.assertEquals(testedStat, expectedStat);
    }

    @Test
    public void scoreTest(){
        when(penaltieSerie.costOfMissedPlayers("LeftCommand")).thenReturn(70000000);
        when(penaltieSerie.costOfMissedPlayers("RightCommand")).thenReturn(20000000);

        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 1);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);

        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);

        String expectedResult = penaltieSerie.getScore();
        String checkingResult = "LeftCommand (3) : RightCommand (4)";
        Assert.assertEquals(expectedResult, checkingResult);

        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        expectedResult = penaltieSerie.getScore();
        checkingResult = "LeftCommand (3) [70000000] : RightCommand (5) [20000000]";
        Assert.assertEquals(expectedResult, checkingResult);
    }

    @Test(expected= ShotException.class)
    public void exceptionOnKickAfterGameFinished (){
        final int fastEnd = penaltieSerie.MAX_SERIES / 2 + 1;
        for (int i = 1; i <= fastEnd; i++) {
            penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
            penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 1);
        }
        penaltieSerie.makeShot("LeftCommand", 0);
    }
}






















