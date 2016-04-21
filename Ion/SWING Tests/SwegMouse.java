import java.util.*;
import java.awt.event.MouseEvent;

public interface SwegMouse extends EventListener{

	public void mouseClicked(MouseEvent e);

	public void mouseReleased(MouseEvent e);

	public void mouseEntered(MouseEvent e);

	public void mouseExited(MouseEvent e);

	public void mousePressed(MouseEvent e);

	public void mouseMoved(MouseEvent e);

	public void mouseDragged(MouseEvent e);

}