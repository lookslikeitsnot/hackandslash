package be.kiop.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import be.kiop.UI.BoardDrawing;

public class RepaintTimer implements ActionListener {
	Timer timer;
	BoardDrawing map;

	public RepaintTimer(BoardDrawing map) {
		this.map = map;
		timer = new Timer(100, this);
		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			map.repaint();
		}
	}
}
