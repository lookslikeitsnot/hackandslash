package be.kiop.controllers;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import be.kiop.UI.Board;
import be.kiop.UI.BoardDrawing;
import be.kiop.characters.GameCharacter;
import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.items.Drop;
import be.kiop.valueobjects.Directions;

public class Keyboard {

	private static final String MOVE_NORTH = "move up";
	private static final String MOVE_SOUTH = "move down";
	private static final String MOVE_WEST = "move left";
	private static final String MOVE_EAST = "move right";

	private static final String ATTACK = "attack";
	private static final String PICK_UP = "pick up";

	public Keyboard(BoardDrawing map, Hero hero, Board board) {
		InputMap iMap = map.getInputMap(BoardDrawing.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = map.getActionMap();

		iMap.put(KeyStroke.getKeyStroke("UP"), MOVE_NORTH);
		iMap.put(KeyStroke.getKeyStroke("DOWN"), MOVE_SOUTH);
		iMap.put(KeyStroke.getKeyStroke("LEFT"), MOVE_WEST);
		iMap.put(KeyStroke.getKeyStroke("RIGHT"), MOVE_EAST);
		iMap.put(KeyStroke.getKeyStroke("SPACE"), ATTACK);
		iMap.put(KeyStroke.getKeyStroke("E"), PICK_UP);
		aMap.put(MOVE_NORTH, new MoveAction(Directions.NORTH, hero, board));
		aMap.put(MOVE_SOUTH, new MoveAction(Directions.SOUTH, hero, board));
		aMap.put(MOVE_WEST, new MoveAction(Directions.WEST, hero, board));
		aMap.put(MOVE_EAST, new MoveAction(Directions.EAST, hero, board));
		aMap.put(ATTACK, new AttackAction(hero, board));
		aMap.put(PICK_UP, new PickUpAction(hero, board));
	}

	private class MoveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		Directions direction;
		Hero hero;
		Board board;

		MoveAction(Directions direction, Hero hero, Board board) {
			this.direction = direction;
			this.hero = hero;
			this.board = board;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			hero.move(direction, board.getAllHitBoxes(0, hero.getTile()));
		}
	}

	private class AttackAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		GameCharacter hero;
		Board board;

		public AttackAction(GameCharacter hero, Board board) {
			this.hero = hero;
			this.board = board;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Optional<Set<Enemy>> optEnemies = board.enemiesInRange();
			if (optEnemies.isPresent()) {
				Set<Enemy> enemies = optEnemies.get();
				for (Enemy enemy : enemies) {
					System.out.println("attacking");
					hero.attack(enemy);
				}
				System.out.println("amount of attacked enemies: " + enemies.size());
			}
		}
	}

	private class PickUpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		Hero hero;
		Board board;
		
		public PickUpAction(Hero hero, Board board) {
			this.hero = hero;
			this.board = board;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Drop> availableDrops = board.getDrops().stream().filter(drop -> drop.getTile().equals(hero.getTile())).collect(Collectors.toList());
			for(Drop drop: availableDrops) {
				hero.getBackpack().add(drop);
				board.removeDrop(drop);
			}
		}

	}
}
