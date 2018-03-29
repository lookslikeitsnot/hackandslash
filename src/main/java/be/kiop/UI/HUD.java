package be.kiop.UI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.SkinNotFoundException;

public class HUD extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;

//	private Hero hero;
//	private Ennemy ennemy;

	public HUD(Hero hero, Ennemy ennemy) {
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/images/backgrounds/background.png"));
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
		g.drawImage(backgroundImage, 0,0, this.getWidth(), this.getHeight(), null);
	}
}
