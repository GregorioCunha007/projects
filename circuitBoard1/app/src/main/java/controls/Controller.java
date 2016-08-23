package controls;

import model.Node;


public interface Controller {
	
	int getXSize();
	int getYSize();
	boolean isLocked(Node n1);
	Node getNode(int y,int x);
	
}
