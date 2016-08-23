package model;



public class Location {
	
	public int x, y;
	public boolean pointer=false;
	public Location(int x2, int y2) {
		y = x2;
		x = y2;
	}
	public boolean validConnection(Direction d){
		return false;
	}
	public boolean isInvalid() {
		return true;
	}
	@Override
	public String toString() {
		return "" + x + "," + y + "," ;
	}
}
