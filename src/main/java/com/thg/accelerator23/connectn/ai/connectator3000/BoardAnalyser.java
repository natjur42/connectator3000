package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

public class BoardAnalyser {
    private final Board board;
    private final Position lastCounterPosition;
    private final Counter counter;
    private final int winningNumber;

    public BoardAnalyser(Board board, Position lastCounterPosition){
        this.board = board;
        this.lastCounterPosition = lastCounterPosition;
        this.counter = board.getCounterAtPosition(lastCounterPosition);
        this.winningNumber = board.getConfig().getnInARowForWin();
//        System.out.println("winning number: " + winningNumber);
    }

    public BoardAnalyser(Board board, Position lastCounterPosition, Counter counter, int winningNumber){
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

    public boolean hasWon(){
        return hasWonRow() || hasWonColumn() || hasWonDiagonal1() || hasWonDiagonal2();
    }

    protected boolean hasWonRow() {
        int connectedSoFar = 1 +
                connectedRight() +
                connectedLeft();

        return connectedSoFar >= getWinningNumber();
    }

    private int getBoardWidth(){
        return board.getConfig().getWidth();
    }

    private int getBoardHeight(){
        return board.getConfig().getHeight();
    }

    public int evaluateBoard(){

        //TODO: blocking opponent +points
        //TODO: check around the last token you inserted
        int connectedInRow = connectedRight() + connectedLeft();
        int connectedInColumn = connectedUp() + connectedDown();
        int connectedDiagonally1 = connectedDiagonal1Up() + connectedDiagonal1Down();
        int connectedDiagonally2 = connectedDiagonal2Up() + connectedDiagonal2Down();

        int score = connectedInRow * 2 + connectedInColumn * 2 + connectedDiagonally1 * 3 + connectedDiagonally2 * 3;

        return score;
    }

    private int connectedRight() {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentX = x + 1;

        while (counter == counterCheck && currentX <= getBoardWidth() -1) {

            Position position = new Position(currentX, y);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);

            currentX++;
        }

        return connectedSoFar;
    }

    private int connectedLeft() {
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentX = x - 1;

        while (counter == counterCheck && currentX >= 0) {

            Position position = new Position(currentX, y);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);

            currentX--;
        }

        return connectedSoFar;
    }

    protected boolean hasWonColumn() {
        int connectedSoFar = 1 +
                connectedUp() +
                connectedDown();

        return connectedSoFar >= winningNumber;
    }

    private int connectedUp(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;

        while (counter == counterCheck && currentY <= board.getConfig().getHeight() - 1) {

            Position position = new Position(x, currentY);

            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);

            currentY++;
        }

        return connectedSoFar;
    }

    private int connectedDown(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;

        while (counter == counterCheck && currentY >= 0) {

            Position position = new Position(x, currentY);
            counterCheck = board.getCounterAtPosition(position);

            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);
            currentY--;
        }

        return connectedSoFar;
    }

    protected boolean hasWonDiagonal1() {
        int connectedSoFar = 1 +
                connectedDiagonal1Up() +
                connectedDiagonal1Down();

        return connectedSoFar >= winningNumber;
    }

    private int connectedDiagonal1Down(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;
        int currentX = x - 1;

        while (counter == counterCheck && currentY >= 0 && currentX >=0) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);
            currentY--;
            currentX--;
        }

        return connectedSoFar;
    }

    private int connectedDiagonal1Up(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;
        int currentX = x + 1;

        while (counter == counterCheck && currentY < board.getConfig().getHeight() && currentX < board.getConfig().getWidth()) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);
            currentY++;
            currentX++;
        }

        return connectedSoFar;
    }

    protected boolean hasWonDiagonal2() {
        int connectedSoFar = 1 +
                connectedDiagonal2Up() +
                connectedDiagonal2Down();

        return connectedSoFar >= winningNumber;
    }

    private int connectedDiagonal2Up(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y + 1;
        int currentX = x - 1;

        while (counter == counterCheck && currentY < board.getConfig().getHeight() && currentX >= 0) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);
            currentY++;
            currentX--;
        }

        return connectedSoFar;
    }

    private int connectedDiagonal2Down(){
        int x = lastCounterPosition.getX();
        int y = lastCounterPosition.getY();
        int connectedSoFar = 0;
        Counter counterCheck = counter;
        int currentY = y - 1;
        int currentX = x + 1;

        while (counter == counterCheck && currentY >= 0 && currentX < board.getConfig().getWidth()) {

            Position position = new Position(currentX, currentY);
            counterCheck = board.getCounterAtPosition(position);
            connectedSoFar = updateConnectedSoFar(connectedSoFar, position);
            currentY--;
            currentX++;
        }

        return connectedSoFar;
    }


    private int updateConnectedSoFar(int connectedSoFar, Position position){
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
