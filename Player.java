
public class Player {
	
	public int xPos, yPos, width, height, score;
	
	protected int speed = 4;
	
	public Player(int x, int y, int w, int h) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		score = 0;
	}
	
	public void update(boolean left, boolean right) {
		if (right && xPos + width < Game.WIDTH) {
			xPos += speed;
		} else if (left && xPos > 0) {
			xPos -= speed;
		}
	}
	
	public void render(int[] pixels) {
		for (int y = 0; y < height; y ++) {
			int yPix = y + yPos;
			if (yPix < 0 || yPix >= Game.HEIGHT) {
				continue;
			}
			
			for (int x = 0; x < width; x ++) {
				int xPix = x + xPos;
				if (xPix < 0 || xPix >= Game.WIDTH) {
					continue;
				}
				pixels[xPix + yPix * Game.WIDTH] = 0xFFFFFF;
			}
		}
	}
}
