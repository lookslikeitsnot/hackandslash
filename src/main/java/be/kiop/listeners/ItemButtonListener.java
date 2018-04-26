package be.kiop.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import be.kiop.characters.heroes.Hero;
import be.kiop.items.Drop;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;

public class ItemButtonListener implements MouseListener {
	private Hero hero;

	public ItemButtonListener(Hero hero) {
		this.hero = hero;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		Drop drop = (Drop) button.getClientProperty("item");
		if (SwingUtilities.isRightMouseButton(e)) {
			if (drop != null) {
				hero.getBackpack().remove(drop);
				if (drop == hero.getWeapon()) {
					hero.dropWeapon();
				}
			}
		} else {
			if (drop instanceof Weapon) {
				Weapon heroWeapon = hero.getWeapon();
				hero.changeWeapon((Weapon) drop);
//				hero.getBackpack().remove(drop);
				if (!(heroWeapon instanceof Fist)) {
//					hero.getBackpack().add(heroWeapon);
				}
			}
		}
		button.getParent().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
