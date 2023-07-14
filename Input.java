import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public boolean[] key = new boolean[68836];
	
	public void keyPressed(KeyEvent e) {
		key[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e) {
		key[e.getKeyCode()] = false;
	}
	
	public void keyTyped (KeyEvent e) {
		
	}

}
