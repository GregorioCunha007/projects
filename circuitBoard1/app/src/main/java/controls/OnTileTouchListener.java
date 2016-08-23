package controls;
import model.Node;

public interface OnTileTouchListener {
	
	public boolean onMove(int xFrom , int yFrom , int xTo , int yTo , Node n1, boolean onRemoval);
	
	public boolean resetPassage(Node n1);
}
