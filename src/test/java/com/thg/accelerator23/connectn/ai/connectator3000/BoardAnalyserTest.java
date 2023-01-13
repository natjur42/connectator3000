package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardAnalyserTest {

    static Board mockBoard = mock(Board.class);

    static Position mockRowWinPosition = mock(Position.class);
    static Position mockColumnWinPosition = mock(Position.class);
    static Position mockDiagonal1WinPosition = mock(Position.class);
    static Position mockDiagonal2WinPosition = mock(Position.class);

    static Counter player1Counter = Counter.O;
    static Counter player2Counter = Counter.X;

    static GameConfig mockGameConfig = mock(GameConfig.class);

    static int boardWidth = 10;
    static int boardHeight = 8;
    static Counter[][] rowWin = new Counter[boardWidth][boardHeight];
    static Counter[][] columnWin = new Counter[boardWidth][boardHeight];
    static Counter[][] diagonal1Win = new Counter[boardWidth][boardHeight];
    static Counter[][] diagonal2Win = new Counter[boardWidth][boardHeight];

    @BeforeAll
    static public void setup(){
        when(mockBoard.getConfig()).thenReturn(mockGameConfig);

        when(mockGameConfig.getWidth()).thenReturn(boardWidth);
        when(mockGameConfig.getHeight()).thenReturn(boardHeight);
        when(mockGameConfig.getnInARowForWin()).thenReturn(4);

        rowWin[2][0] = player1Counter;
        rowWin[3][0] = player1Counter;
        rowWin[4][0] = player1Counter;
        rowWin[5][0] = player1Counter;

        columnWin[4][0] = player1Counter;
        columnWin[4][1] = player1Counter;
        columnWin[4][2] = player1Counter;
        columnWin[4][3] = player1Counter;

        diagonal1Win[4][0] = player1Counter;
        diagonal1Win[5][1] = player1Counter;
        diagonal1Win[6][2] = player1Counter;
        diagonal1Win[7][3] = player1Counter;

        diagonal2Win[7][0] = player1Counter;
        diagonal2Win[6][1] = player1Counter;
        diagonal2Win[5][2] = player1Counter;
        diagonal2Win[4][3] = player1Counter;

    }

    @Test
    public void hasWonRowTest(){
        //setting last move to be a winning move
        when(mockRowWinPosition.getX()).thenReturn(4);
        when(mockRowWinPosition.getY()).thenReturn(0);

        Board testBoard = new Board(rowWin, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockRowWinPosition, player1Counter, 4);

        assertTrue(testAnalyser.hasWonRow());
        assertFalse(testAnalyser.hasWonColumn());
        assertFalse(testAnalyser.hasWonDiagonal1());
        assertFalse(testAnalyser.hasWonDiagonal2());
        assertTrue(testAnalyser.hasWon());
    }

    @Test
    public void hasWonColumnTest(){
        //setting last move to be a winning move
        when(mockColumnWinPosition.getX()).thenReturn(4);
        when(mockColumnWinPosition.getY()).thenReturn(3);

        Board testBoard = new Board(columnWin, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockColumnWinPosition, player1Counter, 4);

        assertTrue(testAnalyser.hasWonColumn());
        assertFalse(testAnalyser.hasWonRow());
        assertFalse(testAnalyser.hasWonDiagonal1());
        assertFalse(testAnalyser.hasWonDiagonal2());
        assertTrue(testAnalyser.hasWon());
    }

    @Test
    public void hasWonDiagonal1Test(){
        //setting last move to be a winning move
        when(mockDiagonal1WinPosition.getX()).thenReturn(7);
        when(mockDiagonal1WinPosition.getY()).thenReturn(3);

        Board testBoard = new Board(diagonal1Win, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockDiagonal1WinPosition, player1Counter, 4);

        assertTrue(testAnalyser.hasWonDiagonal1());
        assertFalse(testAnalyser.hasWonColumn());
        assertFalse(testAnalyser.hasWonRow());
        assertFalse(testAnalyser.hasWonDiagonal2());
        assertTrue(testAnalyser.hasWon());
    }

    @Test
    public void hasWonDiagonal2Test(){
        //setting last move to be a winning move
        when(mockDiagonal2WinPosition.getX()).thenReturn(4);
        when(mockDiagonal2WinPosition.getY()).thenReturn(3);

        Board testBoard = new Board(diagonal2Win, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockDiagonal2WinPosition, player1Counter, 4);

        assertTrue(testAnalyser.hasWonDiagonal2());
        assertFalse(testAnalyser.hasWonColumn());
        assertFalse(testAnalyser.hasWonDiagonal1());
        assertFalse(testAnalyser.hasWonRow());
        assertTrue(testAnalyser.hasWon());
    }

    @Test
    public void hasWonFalseTest(){
        Counter[][] columnLoss = new Counter[boardWidth][boardHeight];
        columnLoss[4][0] = player1Counter;
        columnLoss[4][1] = player1Counter;
        columnLoss[4][2] = player1Counter;
        columnLoss[4][3] = player2Counter;

        Position mockBlockPosition = mock(Position.class);
        when(mockBlockPosition.getX()).thenReturn(4);
        when(mockBlockPosition.getY()).thenReturn(3);

        Board testBoard = new Board(columnLoss, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockBlockPosition, player2Counter, 4);

        assertFalse(testAnalyser.hasWon());
    }

    @Test
    public void hasWonFalseTest2(){
        Counter[][] columnLoss = new Counter[boardWidth][boardHeight];
        columnLoss[4][0] = player1Counter;

        Position mockBlockPosition = mock(Position.class);
        when(mockBlockPosition.getX()).thenReturn(4);
        when(mockBlockPosition.getY()).thenReturn(3);

        Board testBoard = new Board(columnLoss, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockBlockPosition, player1Counter, 4);

        assertFalse(testAnalyser.hasWon());
    }

    @Test
    public void calculatingScore(){
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];
        boardSetup[4][0] = player1Counter;
        boardSetup[4][1] = player1Counter;
        boardSetup[4][2] = player1Counter;
        boardSetup[4][3] = player1Counter;

        Position mockBlockPosition = mock(Position.class);
        when(mockBlockPosition.getX()).thenReturn(4);
        when(mockBlockPosition.getY()).thenReturn(3);

        Board testBoard = new Board(boardSetup, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockBlockPosition, player1Counter, 4);

        System.out.println(testAnalyser.evaluateBoard());
    }

    @Test
    public void calculatingScore2(){
        Counter[][] boardSetup = new Counter[boardWidth][boardHeight];
        boardSetup[4][0] = player1Counter;
        boardSetup[4][1] = player1Counter;
        boardSetup[4][2] = player1Counter;

        Position mockBlockPosition = mock(Position.class);
        when(mockBlockPosition.getX()).thenReturn(4);
        when(mockBlockPosition.getY()).thenReturn(2);

        Board testBoard = new Board(boardSetup, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockBlockPosition, player1Counter, 4);

        System.out.println(testAnalyser.evaluateBoard());
    }


}