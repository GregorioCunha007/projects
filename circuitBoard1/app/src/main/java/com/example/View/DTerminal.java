package com.example.View;

import model.Node;
import android.graphics.Canvas;
import android.graphics.Color;

public class DTerminal extends Viewable {
	
	Node n1;	
	
	public DTerminal(Boardview bv, int y, int x, int color){
		this.y=y;
		this.x=x;
		this.color=color;
		n1 = bv.control.getNode(y, x);
	}
	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {
		
	if( n1.to != null )
		Boardview.drawPassage(canvas,y,x,color,n1.to.dx,n1.to.dy);
	if( n1.from != null )
		Boardview.drawPassage(canvas,y,x,color,n1.from.dx,n1.from.dy);	
	
		Boardview.extrapolatePosition(y,x);
		Boardview.paint.setColor(Boardview.colors[color]);
		canvas.drawCircle(Boardview.refCX, Boardview.refCY, Boardview.tileLength/3 , Boardview.paint);
		Boardview.paint.setColor(Color.BLACK);
		canvas.drawCircle(Boardview.refCX, Boardview.refCY, Boardview.tileLength/6, Boardview.paint);	
		
	}
	
}
