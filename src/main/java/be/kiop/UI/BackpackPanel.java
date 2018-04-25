package be.kiop.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import be.kiop.characters.heroes.Hero;
import be.kiop.events.BackpackEvent;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.items.Drop;
import be.kiop.listeners.BackpackListener;
import be.kiop.listeners.ItemButtonListener;

public class BackpackPanel extends JPanel implements BackpackListener {
	private static final long serialVersionUID = 1L;
//	private static final String backpackString = "Hero backpack: ";
	private BufferedImage backgroundImage;
	private List<JButton> buttons;

	private Hero hero;

	public BackpackPanel(Hero hero) {
		setLayout(new GridLayout(2, 6));
		this.hero = hero;
		buttons = new ArrayList<>();
		
		initializeButtons();               
		//setPreferredSize(new Dimension(500, 100));
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/images/backgrounds/dark-background.jpg"));
		} catch (IOException e) {
			backgroundImage = null;
			throw new SkinNotFoundException();
		}
		hero.getBackpack().addBackpackListener(this);
		buttons.stream().forEach(button -> this.add(button));
	}
	
	private void initializeButtons() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j <2 ; j++) {
				JButton button = new JButton("");
				button.setPreferredSize(new Dimension(36, 36));
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.addActionListener(new ItemButtonListener(hero));
				button.setName(Integer.toString(i*(1+j)));
				buttons.add(button);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0,0, null); //, this.getWidth(), this.getHeight()
	}

	@Override
	public void backpackChanged(BackpackEvent event) {
		List<Drop> backpackItems = event.newContent;
		int i = 0;
		for(Drop drop : backpackItems) {
			ImageIcon itemImage = new ImageIcon(drop.getTexture().getSkin());
			buttons.get(i).setIcon(itemImage);
			buttons.get(i).putClientProperty("item", drop);
			i++;
		}

		repaint();
	}
}
