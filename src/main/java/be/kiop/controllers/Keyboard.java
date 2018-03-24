package be.kiop.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import be.kiop.UI.Map;
import be.kiop.characters.GameCharacter;
import be.kiop.characters.heroes.Hero;
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
//		case KeyEvent.VK_RIGHT:
//			map.moveCharacter(Directions.RIGHT, hero);
//			break;
//		case KeyEvent.VK_LEFT:
//			map.moveCharacter(Directions.LEFT, hero);
//			break;
//		case KeyEvent.VK_DOWN:
//			map.moveCharacter(Directions.DOWN, hero);
//			break;
//		case KeyEvent.VK_UP:
//			map.moveCharacter(Directions.UP, hero);
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
	
	private static final String MOVE_UP = "move up";
	private static final String MOVE_DOWN = "move down";
	private static final String MOVE_LEFT = "move left";
	private static final String MOVE_RIGHT = "move right";
	
	public Keyboard(Map map, Hero hero) {
		InputMap iMap = map.getInputMap(Map.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = map.getActionMap();

		
		iMap.put(KeyStroke.getKeyStroke("UP"),MOVE_UP);
		iMap.put(KeyStroke.getKeyStroke("DOWN"),MOVE_DOWN);
		iMap.put(KeyStroke.getKeyStroke("LEFT"),MOVE_LEFT);
		iMap.put(KeyStroke.getKeyStroke("RIGHT"),MOVE_RIGHT);
		aMap.put(MOVE_UP,new MoveAction(Directions.UP, hero, map));
		aMap.put(MOVE_DOWN,new MoveAction(Directions.DOWN, hero, map));
		aMap.put(MOVE_LEFT,new MoveAction(Directions.LEFT, hero, map));
		aMap.put(MOVE_RIGHT,new MoveAction(Directions.RIGHT, hero, map));
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
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        	hero.move(direction);
        	map.repaint();
        }
    }
}
