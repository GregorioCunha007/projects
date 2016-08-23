package com.example.View;


import model.Node;
import android.graphics.Canvas;

public class DDot extends Viewable{
	
	Node n1;
	
	
	public DDot(Boardview bv, int y, int x, int color){
		this.y=y;
		this.x=x;
		this.color=color;
		this.n1 = bv.control.getNode(y, x);
	}
	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {
		color = n1.color;
		if( color == -1)color=Boardview.colors.length-1;
		if( n1.from != null )Boardview.drawPassage(canvas, y, x, color, n1.from.dx, n1.from.dy);
		if( n1.to != null )Boardview.drawPassage(canvas,y,x,color,n1.to.dx,n1.to.dy);
		Boardview.extrapolatePosition(y,x);
		Boardview.paint.setColor(Boardview.colors[color]);
		canvas.drawCircle(Boardview.refCX, Boardview.refCY, Boardview.tileLength/6, Boardview.paint);
	
	}

}
