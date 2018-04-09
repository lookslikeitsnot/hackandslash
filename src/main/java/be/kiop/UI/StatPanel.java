package be.kiop.UI;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import be.kiop.characters.GameCharacter;
import be.kiop.characters.heroes.Hero;
import be.kiop.events.HealthEvent;
import be.kiop.listeners.HealthListener;

class StatPanel extends JPanel implements HealthListener{
	private static final long serialVersionUID = 1L;
	
	private JProgressBar healthBar;
	private LifeBar lifeBar;
	private JLabel nameLabel;
	
	private GameCharacter gc;
	
	StatPanel(GameCharacter gc) {
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		UIManager.put("ProgressBar.background", Color.RED);
		UIManager.put("ProgressBar.foreground", Color.GREEN);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		
		nameLabel = new JLabel(gc.getName());
		nameLabel.setForeground(Color.green);
		
		
		add(Box.createHorizontalStrut(10));
		
		this.gc = gc;
		this.gc.addHealthListener(this);
		
		if(gc instanceof Hero) {
			lifeBar = new LifeBar((Hero) gc);
		}
		
		
		healthBar = new JProgressBar(0, (int) gc.getMaxHealth());
		healthBar.setValue((int) gc.getHealth());
		healthBar.setStringPainted(true);
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
		setOpaque(false);
		add(nameLabel);
		add(healthBar);
		add(lifeBar);
	}
	
	@Override
	public void healthChanged(HealthEvent event) {
		healthBar.setValue((int) gc.getHealth());
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
	}
}
