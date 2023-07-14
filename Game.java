import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable { 
	
	public static final int WIDTH = 400, HEIGHT = 300, SCALE = 2;
	
	private String title = "Pong", fps = "";
	private boolean running = false;
	private BufferedImage image;
	private BufferStrategy bufferStrat;
	private int[] pixels;
	private Thread thread;
	private Player player1, player2;
	private Ball ball;
	private Input input;

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		JFrame frame = new JFrame(title);
		frame.setSize(WIDTH*SCALE, HEIGHT*SCALE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		
		setUp();
		
		addKeyListener(input);
		requestFocus();
		
		start();
	}
	
	public void setUp() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		input = new Input();
		
		player1 = new Player(WIDTH/2 - 32, HEIGHT - 46, 64, 16);
		player2 = new Player(WIDTH/2 - 32, 10, 64, 16);
		
		ball = new Ball(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
	}
	
	public void update() {
		player1.update(input.key[KeyEvent.VK_A], input.key[KeyEvent.VK_D]);
		player2.update(input.key[KeyEvent.VK_LEFT], input.key[KeyEvent.VK_RIGHT]);
		ball.update(player1, player2);
		
	}
	
	public void render() {
		bufferStrat = this.getBufferStrategy();
		if (bufferStrat == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		for (int i = 0; i < pixels.length; i ++) {
			pixels[i] = 0;
		}
		
		player1.render(pixels);
		player2.render(pixels);
		
		ball.render(pixels);
		
		Graphics g = bufferStrat.getDrawGraphics();
		
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Verdana", 0, 20));
		g.drawString(fps,  10,  30);
		
		g.dispose();
		bufferStrat.show();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		int frames = 0, updates = 0;
		long previousTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		
		while (running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - previousTime)/(1000000000.0/60.0);
			previousTime = currentTime;
			
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			while (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames + " FPS | " + updates + " UPS";
				frames = 0;
				updates = 0;
			}
		}
		System.exit(0);
	}

}
