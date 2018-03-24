package be.kiop.UI;

import javax.swing.JFrame;

import be.kiop.valueobjects.Size;

public class Board extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static Size size = new Size(640,640);
	
	private Map map;
	
	public Board() {
		map = new Map(20,20);
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		setContentPane(map);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

	}

	public static Size getSize(boolean i) {
		return Board.size;
	}

	public static void setSize(Size size) {
		if(size == null) {
			throw new IllegalArgumentException();
		}
		Board.size = size;
	}
}
