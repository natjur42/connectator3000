package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

    static Counter testCounter = Counter.O;

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

        rowWin[2][0] = testCounter;
        rowWin[3][0] = testCounter;
        rowWin[4][0] = testCounter;
        rowWin[5][0] = testCounter;

        columnWin[4][0] = testCounter;
        columnWin[4][1] = testCounter;
        columnWin[4][2] = testCounter;
        columnWin[4][3] = testCounter;

        diagonal1Win[4][0] = testCounter;
        diagonal1Win[5][1] = testCounter;
        diagonal1Win[6][2] = testCounter;
        diagonal1Win[7][3] = testCounter;

        diagonal2Win[7][0] = testCounter;
        diagonal2Win[6][1] = testCounter;
        diagonal2Win[5][2] = testCounter;
        diagonal2Win[4][3] = testCounter;

    }

    @Test
    public void hasWonRowTest(){
        //setting last move to be a winning move
        when(mockRowWinPosition.getX()).thenReturn(4);
        when(mockRowWinPosition.getY()).thenReturn(0);

        Board testBoard = new Board(rowWin, mockGameConfig);
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockRowWinPosition, testCounter, 4);

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
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockColumnWinPosition, testCounter, 4);

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
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockDiagonal1WinPosition, testCounter, 4);

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
        BoardAnalyser testAnalyser = new BoardAnalyser(testBoard, mockDiagonal2WinPosition, testCounter, 4);

        assertTrue(testAnalyser.hasWonDiagonal2());
        assertFalse(testAnalyser.hasWonColumn());
        assertFalse(testAnalyser.hasWonDiagonal1());
        assertFalse(testAnalyser.hasWonRow());
        assertTrue(testAnalyser.hasWon());
    }



}