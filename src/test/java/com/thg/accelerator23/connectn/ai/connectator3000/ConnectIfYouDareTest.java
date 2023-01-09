package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectIfYouDareTest {

    @Mock
    Board mockBoard;

    @Test
    public void moveWithinBoundsTest(){
        ConnectIfYouDare testAI = new ConnectIfYouDare(Counter.O);
        int testMove = testAI.makeMove(mockBoard);
        assertTrue(testMove > 0);
        assertTrue(testMove < 10);
    }

}