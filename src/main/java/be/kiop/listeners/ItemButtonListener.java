package be.kiop.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import be.kiop.characters.heroes.Hero;
import be.kiop.items.Drop;
import be.kiop.weapons.Weapon;

public class ItemButtonListener implements ActionListener {
	private Hero hero;
	
	public ItemButtonListener(Hero hero) {
		this.hero = hero;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		Drop drop = (Drop) button.getClientProperty("item");
		if(drop instanceof Weapon) {
			Weapon heroWeapon = hero.getWeapon();
			hero.changeWeapon((Weapon) drop);
			hero.getBackpack().remove(drop);
			hero.getBackpack().add(heroWeapon);
		}
	}
}
