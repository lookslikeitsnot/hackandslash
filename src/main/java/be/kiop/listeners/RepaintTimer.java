package be.kiop.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import be.kiop.UI.Map;

public class RepaintTimer implements ActionListener{
	Timer timer;
	Map map;
	
	public RepaintTimer(Map map) {
		this.map = map;
		timer = new Timer(100,this);
		timer.start();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource()==timer) {
			map.repaint();
		}
	}
}