package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.*;


public class ConnectIfYouDare extends Player {
    public ConnectIfYouDare(Counter counter) {
        super(counter, "Connectator3000");
    }

    @Override
    public int makeMove(Board board) {
        Minimax minimax = new Minimax(board, getCounter(), 11, -1000, 1000);
        // max depth must be odd to evaluate winning

        return minimax.runMinimax();
    }

}
