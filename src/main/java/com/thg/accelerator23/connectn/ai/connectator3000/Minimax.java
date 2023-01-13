package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.stream.IntStream;

public class Minimax {
    Board board;
    Counter myCounter;
    int maxDepth;
    int alpha;
    int beta;
    int[] zigzag;

    public Minimax(Board board, Counter counter, int maxDepth, int alpha, int beta) {
        this.board = board;
        this.myCounter = counter;
        this.maxDepth = maxDepth;
        this.alpha = alpha;
        this.beta = beta;
        this.zigzag = makeZigzag();
    }

    public int runMinimax() {
        int[] result = maximize(board, null, myCounter, alpha, beta, 0);
//        System.out.println("best score: " + getScoreFromScoreMoveArray(result));
//        System.out.println("best move: " + getMoveFromScoreMoveArray(result));
        return getMoveFromScoreMoveArray(result);

    }

    private int[] makeZigzag() {
        int[] arrayInOrder = new int[board.getConfig().getWidth()];

        boolean addition = arrayInOrder.length % 2 == 0;
        int numberForArray = arrayInOrder.length / 2;

        int index = 0;
        for (int i = 0; i < arrayInOrder.length; i++) {
            if (addition) {
                numberForArray = numberForArray + i;
                arrayInOrder[index] = numberForArray;
                addition = false;
            } else {
                numberForArray = numberForArray - i;
                arrayInOrder[index] = numberForArray;
                addition = true;
            }

            index++;
        }
        return arrayInOrder;
    }


    private int[] maximize(Board board, Position lastPosition, Counter counter, int alpha, int beta, int depth) {
        int score;
        int bestScore = -100000;
        int bestMove;
        int[] returnVals = new int[2];

        if (depth == this.maxDepth) {
            BoardAnalyser analyser = new BoardAnalyser(board, lastPosition);
            score = analyser.evaluateBoard();
            returnVals[0] = score;
            returnVals[1] = lastPosition.getX();

            return returnVals;
        }


        for (int i = 0; i < board.getConfig().getWidth(); i++) {
            int possibleMove = zigzag[i];
            if (isEmpty(board, possibleMove)) {
                try {
                    Position possibleCounterPosition = getCounterPositionFromMoveOnX(board, possibleMove);
                    Board possibleBoard = new Board(board, possibleMove, counter);
                    ResultType result = getResult(possibleBoard, possibleCounterPosition, counter);

                    if (!(result == null)) {
                        score = getScore(result);
                    } else {
                        score = getScoreFromScoreMoveArray(minimize(possibleBoard, possibleCounterPosition, getOtherCounter(counter), alpha, beta, depth + 1));
                    }

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = possibleMove;
                        returnVals[0] = bestScore;
                        returnVals[1] = bestMove;
                    }


                    if (score > alpha) {
                        alpha = score;
                    }

                    if (beta <= alpha) {
                        return returnVals;
                    }


                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }

            }
        }
        return returnVals;
    }

    private static int[] getEvaluatedScore(Board board, Position lastPosition) {
        int score;
        BoardAnalyser analyser = new BoardAnalyser(board, lastPosition);
        score = analyser.evaluateBoard();
        int[] returnVals = new int[2];
        returnVals[0] = score;
        returnVals[1] = lastPosition.getX();

        return returnVals;
    }

    private int[] minimize(Board board, Position lastPosition, Counter counter, int alpha, int beta, int depth) {
        int score;
        int bestScore = 100000000;
        int bestMove;
        int[] returnVals = new int[2];

        if (depth == maxDepth) {
            BoardAnalyser analyser = new BoardAnalyser(board, lastPosition);
            score = analyser.evaluateBoard();
            returnVals[0] = score;
            returnVals[1] = lastPosition.getX();
            return returnVals;
        }

        for (int i = 0; i < board.getConfig().getWidth(); i++) {
            int possibleMove = zigzag[i];

            if (isEmpty(board, possibleMove)) {
                try {
                    Position possibleCounterPosition = getCounterPositionFromMoveOnX(board, possibleMove);
                    Board possibleBoard = new Board(board, possibleMove, counter);
                    ResultType result = getResult(possibleBoard, possibleCounterPosition, counter);

                    if (!(result == null)) {
                        score = getScore(result);
                    } else {
                        score = getScoreFromScoreMoveArray(maximize(possibleBoard, possibleCounterPosition, getOtherCounter(counter), alpha, beta, depth + 1));
                    }

                    if (score < bestScore) {
                        bestScore = score;
                        bestMove = possibleMove;
                        returnVals[0] = bestScore;
                        returnVals[1] = bestMove;
                    }

                    if (score < beta) {
                        beta = score;
                    }

                    if (beta <= alpha) {
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

    static public Counter getOtherCounter(Counter counter) {
        if (counter == Counter.O) {
            return Counter.X;
        } else if (counter == Counter.X) {
            return Counter.O;
        } else {
            throw new RuntimeException("Invalid counter type.");
        }
    }

    static protected boolean isEmpty(Board board, int moveX) {
        return !board.hasCounterAtPosition(new Position(moveX, board.getConfig().getHeight() - 1));
    }

    static protected boolean hasWon(Board board, Position lastCounterPosition) {
        BoardAnalyser analyser = new BoardAnalyser(board, lastCounterPosition);
        return analyser.hasWon();
    }

    protected ResultType getResult(Board board, Position lastCounterPosition, Counter counter) {
//        System.out.println("current counter: " + counter);
//        System.out.println("my counter: " + getCounter());
        if (hasWon(board, lastCounterPosition)) {
            if (counter == getMyCounter()) {
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

    public Counter getMyCounter() {
        return myCounter;
    }

    static protected int getScore(ResultType result) {
        if (result == ResultType.WIN) {
            return 1000;
        } else if (result == ResultType.LOSS) {
            return -1000;
        } else if (result == ResultType.DRAW) {
            return 0;
        } else {
            throw new RuntimeException("invalid result: " + result);
        }
    }

    static protected boolean hasFinished(Board board) {
        for (int i = 0; i < board.getConfig().getWidth(); i++) {
            if (!board.hasCounterAtPosition(new Position(i, board.getConfig().getHeight() - 1))) {
                return false;
            }
        }

        return true;
    }

    static protected Position getCounterPositionFromMoveOnX(Board board, int x) {
        for (int i = 0; i < board.getConfig().getHeight(); i++) {
            if (!board.hasCounterAtPosition(new Position(x, i))) {
                return new Position(x, i);
            }
        }

        throw new IndexOutOfBoundsException("No room in this column: " + x);
    }


}
