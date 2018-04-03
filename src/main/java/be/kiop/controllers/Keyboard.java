package be.kiop.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import be.kiop.UI.Board;
import be.kiop.UI.BoardDrawing;
import be.kiop.characters.GameCharacter;
import be.kiop.characters.heroes.Hero;
import be.kiop.valueobjects.Directions;

public class Keyboard {

	private static final String MOVE_NORTH = "move up";
	private static final String MOVE_SOUTH = "move down";
	private static final String MOVE_WEST = "move left";
	private static final String MOVE_EAST = "move right";

	public Keyboard(BoardDrawing map, Hero hero, Board board) {
		InputMap iMap = map.getInputMap(BoardDrawing.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = map.getActionMap();

		iMap.put(KeyStroke.getKeyStroke("UP"), MOVE_NORTH);
		iMap.put(KeyStroke.getKeyStroke("DOWN"), MOVE_SOUTH);
		iMap.put(KeyStroke.getKeyStroke("LEFT"), MOVE_WEST);
		iMap.put(KeyStroke.getKeyStroke("RIGHT"), MOVE_EAST);
		aMap.put(MOVE_NORTH, new MoveAction(Directions.NORTH, hero, board));
		aMap.put(MOVE_SOUTH, new MoveAction(Directions.SOUTH, hero, board));
		aMap.put(MOVE_WEST, new MoveAction(Directions.WEST, hero, board));
		aMap.put(MOVE_EAST, new MoveAction(Directions.EAST, hero, board));
	}

	private class MoveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		Directions direction;
		GameCharacter hero;
		Board board;

		MoveAction(Directions direction, GameCharacter hero, Board board) {
			this.direction = direction;
			this.hero = hero;
			this.board = board;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			hero.move(direction, board.getAllHitBoxes(0));
		}
	}
}
