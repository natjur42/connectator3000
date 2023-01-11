package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.*;


public class ConnectIfYouDare extends Player {
    public ConnectIfYouDare(Counter counter) {
        //TODO: fill in your name here
        super(counter, ConnectIfYouDare.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        //TODO: some crazy analysis
        //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

        Minimax minimax = new Minimax(board, getCounter(), 3, -1000, 1000);
        // max depth must be odd to evaluate winning

        return minimax.runMinimax();
    }

    //TODO: now exceeds time limit - need to build some sort of memory for reflections and repeated boards



}
