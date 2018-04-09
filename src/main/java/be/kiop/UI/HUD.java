package be.kiop.UI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.SkinNotFoundException;

class HUD extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;

	HUD(Hero hero, Enemy ennemy) {
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/images/backgrounds/dark-background.jpg"));
		} catch (IOException e) {
			backgroundImage = null;
			throw new SkinNotFoundException();
		}
		setLayout(new BorderLayout());
		JPanel heroStatPanel = new StatPanel(hero);
		add(heroStatPanel, BorderLayout.WEST);
		if (ennemy != null) {
			JPanel ennemyStatPanel = new StatPanel(ennemy);
			add(ennemyStatPanel, BorderLayout.EAST);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0,0, null); //, this.getWidth(), this.getHeight()
	}
}
