package be.kiop.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import be.kiop.UI.Map;
import be.kiop.characters.GameCharacter;
import be.kiop.characters.heroes.Hero;
import be.kiop.listeners.RepaintTimer;
import be.kiop.valueobjects.Directions;

//public class Keyboard implements KeyListener {
//	private Map map;
//	private Hero hero;
//
//	public Keyboard(Map map, Hero hero) {
//		this.map = map;
//		this.hero = hero;
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//
//	}
//
//	@Override
//	public void keyPressed(KeyEvent event) {
//		int key = event.getKeyCode();
//
//		switch (key) {
//		case KeyEvent.VK_EAST:
//			map.moveCharacter(Directions.EAST, hero);
//			break;
//		case KeyEvent.VK_WEST:
//			map.moveCharacter(Directions.WEST, hero);
//			break;
//		case KeyEvent.VK_SOUTH:
//			map.moveCharacter(Directions.SOUTH, hero);
//			break;
//		case KeyEvent.VK_NORTH:
//			map.moveCharacter(Directions.NORTH, hero);
//			break;
//		case KeyEvent.VK_SPACE:
//			break;
//		case KeyEvent.VK_P:
//			
//		}
//		System.out.println("repaint");
//		map.repaint();
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//
//	}
//
//}
public class Keyboard {

	private static final String MOVE_NORTH = "move up";
	private static final String MOVE_SOUTH = "move down";
	private static final String MOVE_WEST = "move left";
	private static final String MOVE_EAST = "move right";

	public Keyboard(Map map, Hero hero) {
		InputMap iMap = map.getInputMap(Map.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = map.getActionMap();

		iMap.put(KeyStroke.getKeyStroke("NORTH"), MOVE_NORTH);
		iMap.put(KeyStroke.getKeyStroke("SOUTH"), MOVE_SOUTH);
		iMap.put(KeyStroke.getKeyStroke("WEST"), MOVE_WEST);
		iMap.put(KeyStroke.getKeyStroke("EAST"), MOVE_EAST);
		aMap.put(MOVE_NORTH, new MoveAction(Directions.NORTH, hero, map));
		aMap.put(MOVE_SOUTH, new MoveAction(Directions.SOUTH, hero, map));
		aMap.put(MOVE_WEST, new MoveAction(Directions.WEST, hero, map));
		aMap.put(MOVE_EAST, new MoveAction(Directions.EAST, hero, map));
	}

	private class MoveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		Directions direction;
		GameCharacter hero;
		Map map;

		MoveAction(Directions direction, GameCharacter hero, Map map) {
			this.direction = direction;
			this.hero = hero;
			this.map = map;
			new RepaintTimer(map);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			hero.move(direction, map.getHitBoxes());
//			map.repaint();
		}
	}
}
