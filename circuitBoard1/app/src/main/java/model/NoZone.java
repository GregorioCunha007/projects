package model;

public class NoZone extends Node {
	public NoZone(int x, int y) {
		super(x, y);
	}
	@Override
	public String toString() {
		return "noZone";
	}
	@Override
	public boolean isInvalid(){
		return true;
	}

}
