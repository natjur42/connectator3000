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
import static org.mockito.Mockito.*;

class MinimaxTest {

    static private GameConfig mockGameConfig = mock(GameConfig.class);
    static private Board mockBoard = mock(Board.class);
    static private int boardWidth = 10;
    static private int boardHeight = 8;

    static Counter player1Counter = Counter.O;
    static Counter player2Counter = Counter.X;

    @BeforeAll
    static public void setup() {
        when(mockBoard.getConfig()).thenReturn(mockGameConfig);
        when(mockGameConfig.getWidth()).thenReturn(boardWidth);
        when(mockGameConfig.getHeight()).thenReturn(boardHeight);
        when(mockGameConfig.getnInARowForWin()).thenReturn(4);

    }

    @Test
    public void getOtherCounter() {
        assertSame(Minimax.getOtherCounter(Counter.O), Counter.X);
        assertSame(Minimax.getOtherCounter(Counter.X), Counter.O);
    }

    @Test
    public void getsCorrectCounterPositionFromX() {
        assertEquals(Minimax.getCounterPositionFromMoveOnX(mockBoard, 4), new Position(4, 0));
    }

    @Test
    public void getsCorrectCounterPositionFromX2() {
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];
        boardSetup[4][0] = player1Counter;
        boardSetup[4][1] = player1Counter;
        boardSetup[4][2] = player1Counter;

        Board testBoard = new Board(boardSetup, mockGameConfig);

        assertEquals(Minimax.getCounterPositionFromMoveOnX(testBoard, 4), new Position(4, 3));
        assertEquals(Minimax.getCounterPositionFromMoveOnX(testBoard, 5), new Position(5, 0));
    }

    @Test
    public void knowsWhenItsFinished() {
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];

        for (int i = 0; i < boardWidth; i++) {
            boardSetup[i][boardHeight - 1] = player1Counter;
        }

        Board testBoard = new Board(boardSetup, mockGameConfig);
        assertTrue(Minimax.hasFinished(testBoard));
        assertFalse(Minimax.hasFinished(mockBoard));
    }

    @Test
    public void getsTheRightScoreForTerminalStates() {
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
    public void zigzagWorksEven() {
        Minimax testMinimax = new Minimax(mockBoard, player2Counter, 7, -1000, 1000);
        //this is for mockBoard of width 10;
        int[] expectedZigzag = {5, 4, 6, 3, 7, 2, 8, 1, 9, 0};

        assertArrayEquals(expectedZigzag, testMinimax.zigzag);
    }

    @Test
    public void zigzagWorksOdd() {
        Board testBoard = mock(Board.class);
        GameConfig testConfig = mock(GameConfig.class);

        when(testBoard.getConfig()).thenReturn(testConfig);
        when(testConfig.getWidth()).thenReturn(13);

        Minimax testMinimax = new Minimax(testBoard, player2Counter, 7, -1000, 1000);
        int[] expectedZigzag = {6, 7, 5, 8, 4, 9, 3, 10, 2, 11, 1, 12, 0};

        assertArrayEquals(expectedZigzag, testMinimax.zigzag);
    }

    @Test
    public void hasWonTest1() {
        Counter[][] diagonal2Win = new Counter[boardWidth][boardHeight];
        diagonal2Win[7][0] = player1Counter;
        diagonal2Win[6][1] = player1Counter;
        diagonal2Win[5][2] = player1Counter;
        diagonal2Win[4][3] = player1Counter;

        Position mockWinningPosition = mock(Position.class);
        when(mockWinningPosition.getX()).thenReturn(4);
        when(mockWinningPosition.getY()).thenReturn(3);

        Board testBoard = new Board(diagonal2Win, mockGameConfig);

        assertTrue(Minimax.hasWon(testBoard, mockWinningPosition));

    }

    @Test
    public void hasWonTest2() {
        Counter[][] rowWin = new Counter[boardWidth][boardHeight];
        rowWin[2][0] = player1Counter;
        rowWin[3][0] = player1Counter;
        rowWin[4][0] = player1Counter;
        rowWin[5][0] = player1Counter;

        Position mockWinningPosition = mock(Position.class);
        when(mockWinningPosition.getX()).thenReturn(5);
        when(mockWinningPosition.getY()).thenReturn(0);

        Board testBoard = new Board(rowWin, mockGameConfig);

        assertTrue(Minimax.hasWon(testBoard, mockWinningPosition));
    }

    @Test
    public void hasWonTest3() {
        Counter[][] columnWin = new Counter[boardWidth][boardHeight];
        columnWin[4][0] = player1Counter;
        columnWin[4][1] = player1Counter;
        columnWin[4][2] = player1Counter;
        columnWin[4][3] = player1Counter;

        Position mockWinningPosition = mock(Position.class);
        when(mockWinningPosition.getX()).thenReturn(4);
        when(mockWinningPosition.getY()).thenReturn(3);

        Board testBoard = new Board(columnWin, mockGameConfig);

        assertTrue(Minimax.hasWon(testBoard, mockWinningPosition));
    }

    @Test
    public void hasWonTest4() {
        Counter[][] diagonal1Win = new Counter[boardWidth][boardHeight];
        diagonal1Win[4][0] = player1Counter;
        diagonal1Win[5][1] = player1Counter;
        diagonal1Win[6][2] = player1Counter;
        diagonal1Win[7][3] = player1Counter;

        Position mockWinningPosition = mock(Position.class);
        when(mockWinningPosition.getX()).thenReturn(7);
        when(mockWinningPosition.getY()).thenReturn(3);

        Board testBoard = new Board(diagonal1Win, mockGameConfig);

        assertTrue(Minimax.hasWon(testBoard, mockWinningPosition));
    }

    @Test
    public void isEmptyTest1(){
        Board testBoardFalse = mock(Board.class);
        when(testBoardFalse.getConfig()).thenReturn(mockGameConfig);
        when(testBoardFalse.hasCounterAtPosition(any(Position.class))).thenReturn(false);

        Board testBoardTrue = mock(Board.class);
        when(testBoardTrue.getConfig()).thenReturn(mockGameConfig);
        when(testBoardTrue.hasCounterAtPosition(any(Position.class))).thenReturn(true);

        assertFalse(Minimax.isEmpty(testBoardTrue, 1));
        assertTrue(Minimax.isEmpty(testBoardFalse, 1));
    }



}