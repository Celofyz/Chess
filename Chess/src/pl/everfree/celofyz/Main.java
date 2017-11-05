package pl.everfree.celofyz;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{

	/* Constant values that hold game sizes in pixels
	 * First two are window width and height
	 * 
	 * Last one determinates size of the a field
	 * in a  the chess board. It means that width and height
	 * of the chess board are 8 * FIELD_SIZE in pixels*/
	public static final int WIDTH = 256;
	public static final int HEIGHT = 256;
	public static final int FIELD_SIZE = 32;
	
	/* Declaration of thread and boolean that controlling
	 * the application state*/
	private Thread thread;
	private boolean running = false;
	
	/* Board is a class that controls chess fields
	 * Basically it'a table of Fields*/
	Board b = new Board();
	
	/* This is the main constructor. Its called by the main() method.
	 * It creates a window using Window class which take as arguments
	 * dimensions and name of a system's window and object of Main class */
	public Main(){
		new Window(WIDTH, HEIGHT, "Chess", this);
	}
	
	/* method start() controlls the thread. When invoked it checks if
	 * game is already running by using boolean variable declared before,
	 * and if not it starts the thread and change running to true*/
	public void start(){
		if(running == false){
			thread = new Thread(this);
			thread.start();
			
			running = true;
		}
		return;
	}
	
	/* This is second method that controlls game's thread.
	 * When invoked it stops the thread and change running to false*/
	public void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* This is method with game's main loop
	 * It controlls game's time and invokes tick() and render() methods*/
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			
		}
	}
	
	/* This method is a container for all tick() methods from other classes
	 * Put here code which have to be executed in every loop sequence*/
	public void tick(){
		b.tick();
	}
	
	/* This methods controls graphics and it is container for 
	 * other render() methods in program. When invoked in main loop 
	 * in run() method it creates new frame and display it in window.
	 * Frames are created by combining all render() methods
	 * 
	 * Also it initiate Graphics object which can be pulled
	 * to minor render() methods as argument*/
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0; i < 8; i++){
			if(g.getColor() == Color.WHITE){
				g.setColor(Color.BLACK);
			}else{
				g.setColor(Color.WHITE);
			}
			for(int j = 0; j < 8; j++){
				
				g.fillRect(j * FIELD_SIZE, i * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
				
				if(g.getColor() == Color.WHITE){
					g.setColor(Color.BLACK);
				}else{
					g.setColor(Color.WHITE);
				}
			}
		}
		
		//Here goes every render() method from any other class
		b.render(g);
		
		g.dispose();
		bs.show();
	}
	
	/* The main() method initiate Main() constructor*/
	public static void main(String args[]){
		new Main();
	}
}
