package com.npc.draw;
import javax.swing.JFrame;

public class PlayGame {

	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setTitle("My Game");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setVisible(true);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.Start();
	}
}
