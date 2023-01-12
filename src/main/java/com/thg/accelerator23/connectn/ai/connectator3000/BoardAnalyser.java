package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import static java.lang.Math.abs;

public class BoardAnalyser {
    private final Board board;
    private final Position lastCounterPosition;
    private final Counter counter;
    private final int winningNumber;

    public BoardAnalyser(Board board, Position lastCounterPosition) {
        this.board = board;
        this.lastCounterPosition = lastCounterPosition;
        this.counter = board.getCounterAtPosition(lastCounterPosition);
        this.winningNumber = board.getConfig().getnInARowForWin();
//        System.out.println("winning number: " + winningNumber);
    }

    public BoardAnalyser(Board board, Position lastCounterPosition, Counter counter, int winningNumber) {
        this.board = board;
        this.lastCounterPosition = lastCounterPosition;
        this.counter = counter;
        this.winningNumber = winningNumber;
    }

    public Board getBoard() {
        return board;
    }

    public Position getLastCounterPosition() {
        return lastCounterPosition;
    }

    public Counter getCounter() {
        return counter;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public boolean hasWon() {
        return hasWonRow() || hasWonColumn() || hasWonDiagonal1() || hasWonDiagonal2();
    }

    protected boolean hasWonRow() {
        int connectedSoFar = 1 +
                connectedRight(this.counter) +
                connectedLeft(this.counter);

        return connectedSoFar >= getWinningNumber();
    }

    private int getBoardWidth() {
        return board.getConfig().getWidth();
    }

    private int getBoardHeight() {
        return board.getConfig().getHeight();
    }

    private Counter getOtherCounter(){
        if (this.counter == Counter.X){
            return Counter.O;
        } else if (this.counter == Counter.O){
            return Counter.X;
        } else {
            throw new RuntimeException("Unknown Counter." + this.counter);
        }
    }

    public int evaluateBoard() {

        int connectedInRow = connectedRight(this.counter) + connectedLeft(this.counter);
        int connectedInColumn = connectedUp(this.counter) + connectedDown(this.counter);
        int connectedDiagonally1 = connectedDiagonal1Up(this.counter) + connectedDiagonal1Down(this.counter);
        int connectedDiagonally2 = connectedDiagonal2Up(this.counter) + connectedDiagonal2Down(this.counter);

//        int connectedInRowOpponent = connectedRight(getOtherCounter()) + connectedLeft(getOtherCounter());
//        int connectedInColumnOpponent = connectedUp(getOtherCounter()) + connectedDown(getOtherCounter());
//        int connectedDiagonally1Opponent = connectedDiagonal1Up(getOtherCounter()) + connectedDiagonal1Down(getOtherCounter());
//        int connectedDiagonally2Opponent = connectedDiagonal2Up(getOtherCounter()) + connectedDiagonal2Down(getOtherCounter());

        int connectedInRowOpponent1 = connectedRight(getOtherCounter());
        int connectedInColumnOpponent1 = connectedUp(getOtherCounter());
        int connectedDiagonally1Opponent1 = connectedDiagonal1Up(getOtherCounter());
        int connectedDiagonally2Opponent1 = connectedDiagonal2Up(getOtherCounter());

        int connectedInRowOpponent2 = connectedLeft(getOtherCounter());
        int connectedInColumnOpponent2 = connectedDown(getOtherCounter());
        int connectedDiagonally1Opponent2 = connectedDiagonal1Down(getOtherCounter());
        int connectedDiagonally2Opponent2 = connectedDiagonal2Down(getOtherCounter());


        int closerToCentreBias = 0;

        int proximityToCentre = abs(lastCounterPosition.getX() - getBoardWidth() / 2);

        if (proximityToCentre <= 3) {
            closerToCentreBias = 3;
        }

        int score = (int)Math.pow(connectedInRow * 2, 2) +
                (int)Math.pow(connectedInColumn*2, 2) +
                (int)Math.pow(connectedDiagonally1*2, 2) +
                (int)Math.pow(connectedDiagonally2*2, 2) +
                closerToCentreBias +
                (int)Math.pow(connectedInRowOpponent1, 2) +
                (int)Math.pow(connectedInColumnOpponent1, 2) +
                (int)Math.pow(connectedDiagonally1Opponent1, 2) +
                (int)Math.pow(connectedDiagonally2Opponent1, 2) +
                (int)Math.pow(connectedInRowOpponent2, 2) +
                (int)Math.pow(connectedInColumnOpponent2, 2) +
                (int)Math.pow(connectedDiagonally1Opponent2, 2) +
                (int)Math.pow(connectedDiagonally2Opponent2, 2);


        return score;
    }

    private int connectedRight(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentX = x + 1;

        while (counter == counterCheck && currentX <= getBoardWidth() - 1) {

            Position position = new Position(currentX, y);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);

            currentX++;
        }

        return connectedSoFar;
    }

    private int connectedLeft(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentX = x - 1;

        while (counter == counterCheck && currentX >= 0) {

            Position position = new Position(currentX, y);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);

            currentX--;
        }

        return connectedSoFar;
    }

    protected boolean hasWonColumn() {
        int connectedSoFar = 1 +
                connectedUp(this.counter) +
                connectedDown(this.counter);

        return connectedSoFar >= winningNumber;
    }

    private int connectedUp(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;

        while (counter == counterCheck && currentY <= board.getConfig().getHeight() - 1) {

            Position position = new Position(x, currentY);

            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);

            currentY++;
        }

        return connectedSoFar;
    }

    private int connectedDown(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;

        while (counter == counterCheck && currentY >= 0) {

            Position position = new Position(x, currentY);
            counterCheck = board.getCounterAtPosition(position);

            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);
            currentY--;
        }

        return connectedSoFar;
    }

    protected boolean hasWonDiagonal1() {
        int connectedSoFar = 1 +
                connectedDiagonal1Up(this.counter) +
                connectedDiagonal1Down(this.counter);

        return connectedSoFar >= winningNumber;
    }

    private int connectedDiagonal1Down(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;
        int currentX = x - 1;

        while (counter == counterCheck && currentY >= 0 && currentX >= 0) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);
            currentY--;
            currentX--;
        }

        return connectedSoFar;
    }

    private int connectedDiagonal1Up(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;
        int currentX = x + 1;

        while (counter == counterCheck && currentY < board.getConfig().getHeight() && currentX < board.getConfig().getWidth()) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);
            currentY++;
            currentX++;
        }

        return connectedSoFar;
    }

    protected boolean hasWonDiagonal2() {
        int connectedSoFar = 1 +
                connectedDiagonal2Up(this.counter) +
                connectedDiagonal2Down(this.counter);

        return connectedSoFar >= winningNumber;
    }

    private int connectedDiagonal2Up(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;
        int currentX = x - 1;

        while (counter == counterCheck && currentY < board.getConfig().getHeight() && currentX >= 0) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);
            currentY++;
            currentX--;
        }

        return connectedSoFar;
    }

    private int connectedDiagonal2Down(Counter counter) {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;
        int currentX = x + 1;

        while (counter == counterCheck && currentY >= 0 && currentX < board.getConfig().getWidth()) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position, counter);
            currentY--;
            currentX++;
        }

        return connectedSoFar;
    }

    private int updateConnectedSoFar(int connectedSoFar, Position position, Counter counter) {
        if (board.isWithinBoard(position)) {
            Counter counterCheck = board.getCounterAtPosition(position);
            if (counterCheck == counter) {
                return connectedSoFar + 1;
            } else {
                return connectedSoFar;
            }
        } else {
            throw new RuntimeException("position out of bounds.");
        }
    }
}
