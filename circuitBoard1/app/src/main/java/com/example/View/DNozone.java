package com.example.View;

import android.graphics.Canvas;

public class DNozone extends Viewable{
	
	
	
	public DNozone( int y, int x){
		this.y=y;
		this.x=x;
	}
	@Override
	protected void draw(Canvas canvas, int y, int x, int color) {
		Boardview.extrapolatePosition(y,x);
		int halfway = Boardview.tileLength/2;
		Boardview.paint.setColor(Boardview.colors[5]);
		canvas.drawRect(Boardview.refCX - halfway, Boardview.refCY - halfway, Boardview.refCX + halfway, Boardview.refCY + halfway, Boardview.paint);
	}

}
