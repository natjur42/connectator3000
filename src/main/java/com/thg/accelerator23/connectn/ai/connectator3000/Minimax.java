package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

public class Minimax {
    Board board;
    Counter myCounter;
    int maxDepth;
    int alpha;
    int beta;

    public Minimax(Board board, Counter counter, int maxDepth, int alpha, int beta){
        this.board = board;
        this.myCounter = counter;
        this.maxDepth = maxDepth;
        this.alpha = alpha;
        this.beta = beta;
    }

    public int runMinimax(){
        return getMoveFromScoreMoveArray(maximize(board, null, myCounter, alpha, beta, 0));

    }


    private int[] maximize(Board board, Position lastPosition, Counter counter, int alpha, int beta, int depth) {
        System.out.println(depth);
        int score;
        int bestScore = -100000;
        int bestMove;
        int[] returnVals = new int[2];

        if (depth == this.maxDepth){
            BoardAnalyser analyser = new BoardAnalyser(board, lastPosition);
            score = analyser.evaluateBoard();

            returnVals[0] = score;
            returnVals[1] = lastPosition.getX();

            return returnVals;
        }


        for (int possibleMove = 0; possibleMove < board.getConfig().getWidth(); possibleMove++) {
            //TODO: check if that move would be valid (if loop)
            if (isEmpty(board, possibleMove)) {
                try {
                    Position possibleCounterPosition = getCounterPositionFromMoveOnX(board, possibleMove);
                    Board possibleBoard = new Board(board, possibleMove, counter);
                    //TODO: if I play this move, does the game stop?
                    ResultType result = getResult(possibleBoard, possibleCounterPosition, counter);

//                    System.out.println("result = " + result);
                    if (!(result == null)) {
                        score = getScore(result);
//                        System.out.println("score: " + score);
                    } else {
                        score = getScoreFromScoreMoveArray(minimize(possibleBoard, possibleCounterPosition, getOtherCounter(counter), alpha, beta, depth + 1));
                    }

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = possibleMove;
                        returnVals[0] = bestScore;
                        returnVals[1] = bestMove;
                    }

                    if (score > alpha){
                        alpha = score;
                    }

                    if (beta <= alpha){
                        return returnVals;
                    }



                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }

            }
        }
        return returnVals;
    }

    private int[] minimize(Board board, Position lastPosition, Counter counter, int alpha, int beta, int depth) {
        int score;
        int bestScore = 100000000;
        int bestMove;
        int[] returnVals = new int[2];

        if (depth == maxDepth){
            BoardAnalyser analyser = new BoardAnalyser(board, lastPosition);
            score = analyser.evaluateBoard();

            returnVals[0] = score;
            returnVals[1] = lastPosition.getX();

            return returnVals;
        }

        for (int possibleMove = 0; possibleMove < board.getConfig().getWidth(); possibleMove++) {
            //TODO: check if that move would be valid (if loop)
            if (isEmpty(board, possibleMove)) {
                try {
                    Position possibleCounterPosition = getCounterPositionFromMoveOnX(board, possibleMove);
                    Board possibleBoard = new Board(board, possibleMove, counter);
                    //TODO: if I play this move, does the game stop?
                    ResultType result = getResult(possibleBoard, possibleCounterPosition, counter);

                    if (!(result == null)) {
                        score = getScore(result);
                    } else {
                        score = getScoreFromScoreMoveArray(maximize(possibleBoard, possibleCounterPosition, getOtherCounter(counter), alpha, beta, depth +1));
                    }

                    if (score < bestScore) {
                        bestScore = score;
                        bestMove = possibleMove;
                        returnVals[0] = bestScore;
                        returnVals[1] = bestMove;
                    }

                    if (score < beta){
                        beta = score;
                    }

                    if (beta <= alpha){
                        return returnVals;
                    }


                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnVals;
    }



    private int getScoreFromScoreMoveArray(int[] scoreMove) {
        return scoreMove[0];
    }

    private int getMoveFromScoreMoveArray(int[] scoreMove) {
        return scoreMove[1];
    }

    private Counter getOtherCounter(Counter counter) {
        if (counter == Counter.O) {
            return Counter.X;
        } else if (counter == Counter.X) {
            return Counter.O;
        } else {
            throw new RuntimeException("Invalid counter type.");
        }
    }

    private boolean isEmpty(Board board, int moveX) {
        return !board.hasCounterAtPosition(new Position(moveX, board.getConfig().getHeight() - 1));
    }

    private boolean hasWon(Board board, Position lastCounterPosition) {
//        System.out.println("last counter position: " + lastCounterPosition.getX() + ", " + lastCounterPosition.getY());
        BoardAnalyser analyser = new BoardAnalyser(board, lastCounterPosition);
        boolean hasWonBool = analyser.hasWon();
//        System.out.println("hasWonBool: " + hasWonBool);
        return hasWonBool;
    }

    private ResultType getResult(Board board, Position lastCounterPosition, Counter counter) {
//        System.out.println("current counter: " + counter);
//        System.out.println("my counter: " + getCounter());
        if (hasWon(board, lastCounterPosition)) {
            if (counter == myCounter) {
                return ResultType.WIN;
            } else {
                return ResultType.LOSS;
            }
        } else if (hasFinished(board)) {
            return ResultType.DRAW;
        } else {
            return null;
        }
    }

    private int getScore(ResultType result) {
        if (result == ResultType.WIN) {
            return 100;
        } else if (result == ResultType.LOSS) {
            return -100;
        } else if (result == ResultType.DRAW) {
            return 0;
        } else {
            throw new RuntimeException("invalid result: " + result);
        }
    }

    private boolean hasFinished(Board board) {
        for (int i = 0; i < board.getConfig().getWidth(); i++) {
            if (!board.hasCounterAtPosition(new Position(i, board.getConfig().getHeight() - 1))) {
                return false;
            }
        }

        return true;
    }

    private Position getCounterPositionFromMoveOnX(Board board, int x) {
        for (int i = 0; i < board.getConfig().getHeight(); i++) {
            if (!board.hasCounterAtPosition(new Position(x, i))) {
                return new Position(x, i);
            }
        }

        throw new IndexOutOfBoundsException("No room in this column: " + x);
    }


    private int makeRandomMove(Board board) {
        int min = 0;
        int max = board.getConfig().getWidth() - 1;

        int move = (int) Math.floor(Math.random() * (max - min + 1) + min);
        while (isEmpty(board, move)) {
            move = (int) Math.floor(Math.random() * (max - min + 1) + min);
        }

        return move;
    }

}
