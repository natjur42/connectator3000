package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectIfYouDareTest {

    @Mock
    Board mockBoard;
    GameConfig mockGameConfig;

//    @Test
//    public void moveWithinBoundsTest(){
//        when(mockGameConfig.getHeight()).thenReturn(8);
//        when(mockGameConfig.getWidth()).thenReturn(10);
//        Board testBoard = new Board(mockGameConfig);
//
//
//        when(testBoard.hasCounterAtPosition(Mockito.any(Position.class))).thenReturn(false);
//        ConnectIfYouDare testAI = new ConnectIfYouDare(Counter.O);
//        int testMove = testAI.makeMove(testBoard);
//        assertTrue(testMove > 0);
//        assertTrue(testMove < 10);
//    }

}