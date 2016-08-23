package model;

public class Node extends Location{
	public int color;
	public Direction to,from;
	
	public Node( int x , int y){
			super(x,y);
	}
	
	public Node(int x, int y, int c) {
		super(x, y);
		color = c;
		to = null;
		from = null;
	}
	
	public boolean validConnection (Direction d) {
		return !isInvalid();
	}
	public void reset(){
		to=null;
		from=null;
	}
	@Override
	public boolean isInvalid() {
		return to!=null || from!=null;
	}
	@Override
	public String toString() {
		return "" + color + to + from;
	}
	
}
