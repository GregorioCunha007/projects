package com.example.View;

import model.Direction;
import model.Node;
import android.graphics.Canvas;

public class DMandatory extends Viewable{
	
	Direction orientation;
	Node n1;
	
	public DMandatory(Boardview bv,int y, int x,int color, Direction d){
		this.y=y;
		this.x=x;
		this.color=color; 
		this.orientation = d;
		n1 = bv.control.getNode(y,x);
	}
	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {

		color = n1.color;
		int z = -1; // Vertical
		if(  orientation == Direction.RIGHT || orientation == Direction.LEFT )z = 1;
		Boardview.paint.setColor(Boardview.colors[Boardview.colors.length-1]);
		Boardview.extrapolatePosition(y,x);
		if(z == 1)canvas.drawLine(Boardview.refCX-Boardview.tileLength/2,Boardview.refCY,Boardview.refCX+Boardview.tileLength/2,Boardview.refCY,Boardview.paint);
		else canvas.drawLine(Boardview.refCX,Boardview.refCY-Boardview.tileLength/2,Boardview.refCX,Boardview.refCY+Boardview.tileLength/2,Boardview.paint);
		if( n1.from != null )Boardview.drawPassage(canvas, y, x, color, n1.from.dx, n1.from.dy);
		if( n1.to != null )Boardview.drawPassage(canvas, y, x, color, n1.to.dx, n1.to.dy);
	}
	
}
