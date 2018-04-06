package be.kiop.UI;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import be.kiop.characters.heroes.Hero;
import be.kiop.events.LifeEvent;
import be.kiop.listeners.LifeListener;
import be.kiop.textures.HeartTextures;

public class LifeBar extends JPanel implements LifeListener{
	private static final long serialVersionUID = 1L;
	private final int heartWidth = 15;
	
	private Hero hero;

	public LifeBar(Hero hero) {
		setPreferredSize(new Dimension(heartWidth*Hero.MAX_LIVES, heartWidth));
		this.hero = hero;
		this.hero.addLifeListener(this);
		setOpaque(false);
	}
	
	@Override
	public void lifeChanged(LifeEvent event) {
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for(int x = 0; x < Hero.MAX_LIVES; x++) {
			if (x < hero.getLives()) {
				g.drawImage(HeartTextures.Heart_Full.getSkin(), x*heartWidth, 0, null);
			} else {
				g.drawImage(HeartTextures.Heart_Empty.getSkin(), x*heartWidth, 0, null);
			}
		}
	}
}
