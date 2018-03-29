package be.kiop.UI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import be.kiop.characters.GameCharacter;
import be.kiop.events.HealthEvent;
import be.kiop.listeners.HealthListener;

public class StatPanel extends JPanel implements HealthListener{
	private static final long serialVersionUID = 1L;
	
	private JProgressBar healthBar;
	private GameCharacter gc;
	
	public StatPanel(GameCharacter gc) {
		this.gc = gc;
		this.gc.addHealthListener(this);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(new JLabel(gc.getName()));
		add(new JLabel("Health"));
		healthBar = new JProgressBar(0, (int) gc.getMaxHealth());
		healthBar.setValue((int) gc.getHealth());
		healthBar.setStringPainted(true);
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
		add(healthBar);
	}
	
	@Override
	public void healthChanged(HealthEvent event) {
		healthBar.setValue((int) gc.getHealth());
		healthBar.setString((int)gc.getHealth() + "/" + (int)gc.getMaxHealth());
		System.out.println("cou");
	}
}
