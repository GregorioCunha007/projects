package com.example.View;

import model.Direction;
import model.Node;
import android.graphics.Canvas;

public class DMandatoryCurve extends Viewable{

	Direction first,second;
	Node n1;
	
	
	public DMandatoryCurve(Boardview bv, int y, int x, int color, Direction d, Direction d1){
		
		this.y=y;
		this.x=x;
		this.color=color; 
		this.first = d;
		this.second = d1;
		n1 = bv.control.getNode(y,x);
		
	}
	
	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {
		color = n1.color;
		Boardview.paint.setColor(Boardview.colors[Boardview.colors.length-1]);
		Boardview.extrapolatePosition(y, x);
		int dir1 = findDirection(first);
		int dir2 = findDirection(second);
		canvas.drawLine(Boardview.refCX, Boardview.refCY , Boardview.refCX + (Boardview.tileLength/2 * dir1) ,Boardview.refCY , Boardview.paint);
		canvas.drawLine(Boardview.refCX, Boardview.refCY, Boardview.refCX , Boardview.refCY + (Boardview.tileLength/2 * dir2) , Boardview.paint);
		canvas.drawCircle(Boardview.refCX,Boardview.refCY,Boardview.tileLength/6,Boardview.paint);
		if( n1.from != null )Boardview.drawPassage(canvas, y, x, color, n1.from.dx, n1.from.dy);
		if( n1.to != null )Boardview.drawPassage(canvas, y, x, color, n1.to.dx, n1.to.dy);
		
	}
	
	private int findDirection( Direction dir){
		if( dir == Direction.RIGHT || dir == Direction.LEFT )return ( dir == Direction.LEFT ) ? -1 : 1;
		else return ( dir == Direction.DOWN ) ? 1 : -1;
	}
}
