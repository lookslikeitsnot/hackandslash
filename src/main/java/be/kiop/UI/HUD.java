package be.kiop.UI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.heroes.Hero;

public class HUD extends JPanel{
	private static final long serialVersionUID = 1L;

//	private Hero hero;
//	private Ennemy ennemy;

	public HUD(Hero hero, Ennemy ennemy) {
		setLayout(new BorderLayout());
		JPanel heroStatPanel = new StatPanel(hero);
		add(heroStatPanel, BorderLayout.WEST);
		if (ennemy != null) {
			JPanel ennemyStatPanel = new StatPanel(ennemy);
			add(ennemyStatPanel, BorderLayout.WEST);
		}
	}
}
