package be.kiop.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import be.kiop.characters.heroes.Hero;
import be.kiop.events.BackpackEvent;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.items.Drop;
import be.kiop.listeners.BackpackListener;

public class BackpackPanel extends JPanel implements BackpackListener {
	private static final long serialVersionUID = 1L;
	private static final String backpackString = "Hero backpack: ";
	private BufferedImage backgroundImage;

	private Hero hero;

	public BackpackPanel(Hero hero) {
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/images/backgrounds/dark-background.jpg"));
		} catch (IOException e) {
			backgroundImage = null;
			throw new SkinNotFoundException();
		}
		this.add(new JLabel(backpackString));
		this.hero = hero;
		hero.getBackpack().addBackpackListener(this);
		
		setPreferredSize(new Dimension(500, 100));
		populate();
	}
	
	private void populate() {
		for(Drop drop: hero.getBackpack().getItems()) {
			ImageIcon dropImage = new ImageIcon(drop.getTexture().getSkin());
			JButton button = new JButton("image here",dropImage);
			System.out.println("adding drop button");
			
			button.setPreferredSize(new Dimension(36, 36));
			this.add(button);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("painting backpack");
		g.drawImage(backgroundImage, 0,0, null); //, this.getWidth(), this.getHeight()
	}

	@Override
	public void backpackChanged(BackpackEvent event) {
		populate();
		repaint();
	}
}
