package be.kiop.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import be.kiop.UI.Board;

public class RepaintTimer implements ActionListener {
	Timer timer;
	Board board;

	public RepaintTimer(Board board) {
		this.board = board;
		timer = new Timer(100, this);
		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			board.repaint();
		}
	}
}
