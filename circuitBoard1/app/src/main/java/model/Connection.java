package model;


public class Connection extends Node {
	public Direction[] mDirs = new Direction[2];
	public boolean mandatory = false;
	
	public Connection(int x, int y) {
		super(x, y, Game.noColor);
	}
	public Connection(int x, int y, Direction a, Direction b) {
		super(x, y, Game.noColor);
		mDirs[0] = a;
		mDirs[1] = b;
		mandatory = true;
	}
	@Override
	public void reset(){
		color=Game.noColor;
		super.reset();
	}
	@Override
	public boolean validConnection(Direction d) {
		boolean v = true;
		if(mandatory)
			v = (d==mDirs[0] || d==mDirs[1]);
		return !isInvalid() && v;
	}
	@Override
	public boolean isInvalid() {
		return to != null && from != null;
	}

}
