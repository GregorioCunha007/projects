package com.example.View;

import java.util.ArrayList;
import java.util.Collection;

import model.Node;
import controls.Controller;
import controls.OnTileTouchListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Boardview extends View {
	
	public Controller control;
	Node selectNode,newN;
	int refRow;
	int refLin;
	static int refCX;
	static int refCY;
	static int tileLength;
	OnTileTouchListener touch;
	boolean Vertical;
	boolean MotionDetected;
	
	public static int [] colors = {Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.MAGENTA,0x4000FF00,Color.GRAY};
	
	public Collection<Viewable> Drawables;

	public Boardview(Context context ) {
		super(context);
		Drawables = new ArrayList<Viewable>();
		paint = null;
	}
	
	@Override
	protected void onMeasure(int wMS, int hMS) {
		int w = MeasureSpec.getSize(wMS);
		int h = MeasureSpec.getSize(hMS);
		if (MeasureSpec.getMode(hMS)==MeasureSpec.UNSPECIFIED) 
			h = getSuggestedMinimumHeight();
		if (MeasureSpec.getMode(wMS)==MeasureSpec.UNSPECIFIED) 
			w = getSuggestedMinimumWidth();
		calcSideTile(w, h);
		int wm = tileLength*control.getYSize(); 
		int hm = tileLength*control.getYSize();
		setMeasuredDimension(wm,hm);
	}
	
	private void calcSideTile(int w, int h) {
		int aux = ( w < h ) ? w : h;
		if( aux == w )Vertical=true;
		else Vertical = false;
		tileLength = aux / control.getXSize();
	}
	
	public void setGridSize(int row, int lin) {
	
		refRow = row;
		refLin = lin;
		
	}
	
	static Paint paint;
	private int xLastSel;
	private int yLastSel;
	
	protected void onDraw(Canvas canvas){

		if (paint == null) 
		{
			paint = new Paint();
			paint.setStrokeWidth(tileLength/3);
			for( Viewable v : Drawables )
				v.draw(canvas, v.y, v.x, v.color);
		}
		else
			for( Viewable v : Drawables )
				v.draw(canvas, v.y, v.x, v.color);
	}				
							
	public static void extrapolatePosition(int y, int x) {
		
		refCX=0;
		refCY=0;
		
		for(;x >= 0;--x){refCX+=tileLength;}
		
		for(;y >= 0;--y){refCY+=tileLength;}
		
		refCX = refCX - (tileLength / 2);
		refCY = refCY - (tileLength / 2);
	}
	
	public static void drawPassage(Canvas canvas, int y, int x, int color, int dx,int dy){
		
		extrapolatePosition(y,x);
		paint.setColor(colors[color]);
		canvas.drawLine(refCX,refCY,refCX+((tileLength/2)*dx),refCY+((tileLength/2)*dy), paint);
		canvas.drawCircle(refCX,refCY,tileLength/6,paint);
		
	}

	public void setListener(OnTileTouchListener touched){
		this.touch=touched;
	}
	
	public void setController(Controller control){
		this.control=control;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		
		int auxX=0,auxY=0;
		
		switch(event.getAction()){
			
			case MotionEvent.ACTION_DOWN :  xLastSel = (int)event.getX();
											yLastSel = (int)event.getY();
											if( Vertical && viewExceeded(yLastSel)) return false;
											else if ( !Vertical && viewExceeded(xLastSel))return false;
											if(revertLocation(yLastSel,xLastSel) instanceof Node){
												Node n2 = revertLocation(yLastSel,xLastSel);
												if( n2.color != colors[colors.length-1] ){
												    	selectNode = revertLocation(yLastSel,xLastSel);
												    	if( control.isLocked(selectNode))touch.resetPassage(selectNode);
												}
											}
											MotionDetected=false;
											break;
			
			case MotionEvent.ACTION_MOVE : 	auxX = (int)event.getX();
											auxY = (int)event.getY();
											if( Vertical && viewExceeded(auxY)) return false;
											else if ( !Vertical && viewExceeded(auxX))return false;
											Node n1 = selectNode;
											if( (newN = revertLocation(auxY,auxX)) != selectNode && n1.color != colors[colors.length-1]/* && !(newN instanceof NoZone)*/){
												if( newN.color != colors[colors.length-1] )
													touch.onMove(selectNode.x,selectNode.y,newN.x,newN.y,n1,false);
												if(availableForRemove(newN))
													touch.onMove(selectNode.x,selectNode.y,newN.x,newN.y,n1,true);
												invalidate();
												selectNode=newN;
											}
											MotionDetected = true;
											break;
											
			
			case MotionEvent.ACTION_UP : 	if( (selectNode.color != colors[colors.length-1] && !MotionDetected) ){
												touch.resetPassage(selectNode);
												invalidate();
											}
											break;
			
		}
		return true;
	}
	
	
	private boolean availableForRemove(Node n1){
		return (n1.color != colors[colors.length-1])  ? true : false;
	}
	private boolean viewExceeded(int h){
		if( Vertical)return ( h > getWidth()) ? true : false;
		return ( h > getHeight()) ? true : false;
	}
	private Node revertLocation(int y,int x) {
	
		int newX = x / tileLength;
		int newY = y / tileLength;
		
		return control.getNode(newY,newX);
	}
	
	public void updateBoard(){
		Drawables = new ArrayList<Viewable>();
	}
	public void updateView(){
		invalidate();
	}
}
