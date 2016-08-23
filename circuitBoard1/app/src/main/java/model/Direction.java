package model;

public enum Direction {
	
	LEFT(-1,0), UP(0,-1), RIGHT(1,0), DOWN(0,1);

	public Direction next(int n) {
		return values()[ (this.ordinal()+n) % values().length ];
	}
	
	public Direction opposite() { return next(values().length/2); }

	public Direction prev() { return next(values().length-1) ; }
	
	private Direction(int x, int y) { dx=x; dy=y; }

	public static Direction find(int dx, int dy) {
	  for(Direction d : values()) 
		  if (d.dx==dx && d.dy==dy) return d;
	  return null;
	}
	
	public final int dx;
	public final int dy;
}
