package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinimaxTest {

    static private GameConfig mockGameConfig = mock(GameConfig.class);
    static private Board mockBoard = mock(Board.class);
    static private int boardWidth = 10;
    static private int boardHeight = 8;

    static Counter player1Counter = Counter.O;
    static Counter player2Counter = Counter.X;

    @BeforeAll
    static public void setup(){
        when(mockBoard.getConfig()).thenReturn(mockGameConfig);
        when(mockGameConfig.getWidth()).thenReturn(boardWidth);
        when(mockGameConfig.getHeight()).thenReturn(boardHeight);
        when(mockGameConfig.getnInARowForWin()).thenReturn(4);

    }

    @Test
    public void getOtherCounter() {
        assertTrue(Minimax.getOtherCounter(Counter.O) == Counter.X);
        assertTrue(Minimax.getOtherCounter(Counter.X) == Counter.O);
    }

    @Test
    public void getsCorrectCounterPositionFromX(){
        assertTrue(Minimax.getCounterPositionFromMoveOnX(mockBoard, 4).equals(new Position(4, 0)));
    }

    @Test
    public void getsCorrectCounterPositionFromX2(){
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];
        boardSetup[4][0] = player1Counter;
        boardSetup[4][1] = player1Counter;
        boardSetup[4][2] = player1Counter;

        Board testBoard = new Board(boardSetup, mockGameConfig);

        assertTrue(Minimax.getCounterPositionFromMoveOnX(testBoard, 4).equals(new Position(4, 3)));
        assertTrue(Minimax.getCounterPositionFromMoveOnX(testBoard, 5).equals(new Position(5, 0)));
    }

    @Test
    public void knowsWhenItsFinished(){
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];

        for (int i = 0; i < boardWidth; i++){
            boardSetup[i][boardHeight-1] = player1Counter;
        }

        Board testBoard = new Board(boardSetup, mockGameConfig);
        assertTrue(Minimax.hasFinished(testBoard));
        assertFalse(Minimax.hasFinished(mockBoard));
    }

    @Test
    public void getsTheRightScoreForTerminalStates(){
        ResultType win = ResultType.WIN;
        ResultType loss = ResultType.LOSS;
        ResultType draw = ResultType.DRAW;

        //expected scores
        int winScore = 1000;
        int lossScore = -1000;
        int drawScore = 0;

        assertEquals(Minimax.getScore(win), winScore);
        assertEquals(Minimax.getScore(loss), lossScore);
        assertEquals(Minimax.getScore(draw), drawScore);
    }

    @Test
    public void zigzagWorks(){
        Minimax testMinimax = new Minimax(mockBoard, player2Counter, 7, -1000, 1000);

        for (int i = 0; i < 10; i++){
            System.out.println(testMinimax.zigzag[i]);
        }

    }



}