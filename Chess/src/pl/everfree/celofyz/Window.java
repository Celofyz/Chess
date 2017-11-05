package pl.everfree.celofyz;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/*This class create a window based on arguments from the Main class*/
public class Window extends Canvas{

	private static final long serialVersionUID = 3204736792811150584L;
	
	/* This constructor is used by Main class constructor*/
	public Window(int width, int height, String title, Main main){
		JFrame frame = new JFrame(title);
		
		/*System window's dimensions*/
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This disable process when game window is closed
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(main);
		frame.setUndecorated(true); //Windows borders have been disabled because they caused design problems
		frame.setVisible(true);
		
		/*It starts game's thread*/
		main.start();
		
	}
}
