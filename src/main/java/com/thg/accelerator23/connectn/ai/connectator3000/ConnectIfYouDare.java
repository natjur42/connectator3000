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

    int min = 0;
    int max = 9;

    int move = (int)Math.floor(Math.random() * (max - min + 1) + min);
    while (board.hasCounterAtPosition(new Position(7, move))){
      move = (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    return move;
  }
}
