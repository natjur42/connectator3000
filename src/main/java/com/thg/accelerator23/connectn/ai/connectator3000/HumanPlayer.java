package com.thg.accelerator23.connectn.ai.connectator3000;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(Counter counter) {
        //TODO: fill in your name here
        super(counter, "Natalia");
    }

    @Override
    public int makeMove(Board board) {
        Scanner input = new Scanner(System.in);
        System.out.println("input your move: ");
        int move = input.nextInt();

        return move;
    }
}
