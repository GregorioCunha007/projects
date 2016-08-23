package com.example.View;

import android.graphics.Canvas;

public abstract class Viewable {

	public int y;
	public int x;
	public int color;
			
	protected abstract void draw(Canvas canvas, int y, int x, int color);

}
