package be.kiop.UI;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import be.kiop.characters.GameCharacter;
import be.kiop.characters.heroes.Hero;
import be.kiop.events.HealthEvent;
import be.kiop.events.LifeEvent;
import be.kiop.listeners.HealthListener;
import be.kiop.listeners.LifeListener;

public class StatPanel extends JPanel implements HealthListener, LifeListener{
	private static final long serialVersionUID = 1L;
	
	private JProgressBar healthBar;
	private JLabel lifeLabel;
	private JLabel nameLabel;
	private JLabel healthLabel;
	
	private GameCharacter gc;
	
	public StatPanel(GameCharacter gc) {
		UIManager.put("ProgressBar.background", Color.RED);
		UIManager.put("ProgressBar.foreground", Color.GREEN);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		
		this.gc = gc;
		this.gc.addHealthListener(this);
		
		if(gc instanceof Hero) {
			((Hero) gc).addLifeListener(this);
			lifeLabel = new JLabel();
			lifeLabel.setText(String.valueOf(((Hero) gc).getLives()));
			lifeLabel.setForeground(Color.red);
			add(lifeLabel);
		}
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		nameLabel = new JLabel(gc.getName());
//		healthLabel = new JLabel("Health");
		nameLabel.setForeground(Color.red);
//		healthLabel.setForeground(Color.red);
		add(nameLabel);
//		add(healthLabel);
		healthBar = new JProgressBar(0, (int) gc.getMaxHealth());
		healthBar.setValue((int) gc.getHealth());
		healthBar.setStringPainted(true);
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
		setOpaque(false);
		add(healthBar);
	}
	
	@Override
	public void healthChanged(HealthEvent event) {
		healthBar.setValue((int) gc.getHealth());
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
	}

	@Override
	public void lifeChanged(LifeEvent event) {
		lifeLabel.setText(String.valueOf(((Hero)gc).getLives()));
		
	}
}
