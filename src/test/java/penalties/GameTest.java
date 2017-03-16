package penalties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    public void makeShotTest(){
        penaltieSerie.makeShot("LeftCommand", 1);
        penaltieSerie.makeShot("RightCommand", 0);
        penaltieSerie.makeShot("RightCommand", 1);
        penaltieSerie.makeShot("RightCommand", 1);
        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), 1);
        Assert.assertEquals(penaltieSerie.getRightCommandScore(), 2);
    }

    @Test
    public void attemptsCountTest(){
        penaltieSerie.makeShot("LeftCommand", 1);
        penaltieSerie.makeShot("RightCommand", 0);
        penaltieSerie.makeShot("RightCommand", 1);
        Assert.assertEquals(penaltieSerie.getLeftCommandAttempts(), 1);
        Assert.assertEquals(penaltieSerie.getRightCommandAttempts(), 2);
    }

    @Test
    public void fullGameScoreTest(){
        for(int i = 1; i <= penaltieSerie.MAX_SERIES; i++){
            penaltieSerie.makeShot("LeftCommand", 1);
            penaltieSerie.makeShot("RightCommand", 1);
        }
        Assert.assertEquals(penaltieSerie.isFinished(), true);
        Assert.assertEquals(penaltieSerie.getLeftCommandScore(), 5);
        Assert.assertEquals(penaltieSerie.getRightCommandScore(), 5);
    }

    @Test
    public void earlyTerminationTest() {
        for (int i = 1; i <= 3; i++) {
            penaltieSerie.makeShot("LeftCommand", 1);
            penaltieSerie.makeShot("RightCommand", 0);
        }

        Assert.assertEquals(penaltieSerie.checkEarlyTerminationConditions(), true);
        Assert.assertEquals(penaltieSerie.getLeftCommandAttempts(), 3);
        Assert.assertEquals(penaltieSerie.getRightCommandAttempts(), 3);
    }

    @Test
    public void checkEarlyTerminationMethod(){
        for (int i = 1; i <= 3; i++) {
            penaltieSerie.makeShot("LeftCommand", 1);
            penaltieSerie.makeShot("RightCommand", 0);
        }
        penaltieSerie.makeShot("LeftCommand", 1);
        System.out.println(penaltieSerie.getLeftCommandAttempts());
        System.out.println(penaltieSerie.getRightCommandAttempts());
        Assert.assertTrue(penaltieSerie.checkEarlyTerminationConditions());
    }

    @Test
    public void makeShotByPersonalTest(){
        //Game spiedStatService = Mockito.spy(new Game());
        //when(spiedStatService.getPlayerStat("Messi")).thenReturn(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0)));
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
        checkingResult = "LeftCommand (3)[70000000] : RightCommand (5) [20000000]";

        Assert.assertEquals(expectedResult, checkingResult);
    }


    @Ignore
    @Test
    public void overallCostOfMissedPlayersTest(){
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);


        int testedCost = penaltieSerie.costOfMissedPlayers("LeftCommand");
        int expectedCost = 0;
        Assert.assertEquals(testedCost, expectedCost);

        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Messi", "LeftCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);
        penaltieSerie.makeShotByPersonal("Ronaldu", "RightCommand", 0);

        testedCost = penaltieSerie.costOfMissedPlayers("LeftCommand");
        expectedCost = 70000000;
    }


}






















