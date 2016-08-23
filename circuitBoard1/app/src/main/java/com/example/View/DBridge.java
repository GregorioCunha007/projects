package com.example.View;

import android.graphics.Canvas;
import android.graphics.Color;
import model.Node;

public class DBridge extends Viewable{
	
	public int center = Boardview.colors[Boardview.colors.length-1];
	Node n1;
	
	public DBridge(Boardview bv, int y, int x, int color){
		this.y=y;
		this.x=x;
		this.color=color;
		n1 = bv.control.getNode(y,x);
	}

	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {
		
		center = n1.color;

		if( n1.to != null ){
			canvas.drawCircle(y,x,center,Boardview.paint);
		    Boardview.drawPassage(canvas,y,x,color,n1.to.dx,n1.to.dy);	
		}
		if( n1.from != null ){
			canvas.drawCircle(y,x,center,Boardview.paint);
			Boardview.drawPassage(canvas,y,x,color,n1.from.dx,n1.from.dy);
		}
		Boardview.extrapolatePosition(y,x);
		Boardview.paint.setColor(Boardview.colors[color]);
		canvas.drawCircle(Boardview.refCX, Boardview.refCY, Boardview.tileLength/3 , Boardview.paint);
		Boardview.paint.setColor(Color.GRAY);
		canvas.drawCircle(Boardview.refCX, Boardview.refCY, Boardview.tileLength/6, Boardview.paint);	
		
	}

	
}
