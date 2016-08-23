package model;

public class Bridge extends Node{
	
	public int coreColor;
	boolean connector,receiver;
	Bridge portal;
	
	public Bridge(int x, int y,int color,boolean connector,boolean receiver) {
		super(x,y,color);
		this.connector = connector;
		this.receiver = receiver;
		to = null;
		from = null;
	}
	
	@Override 
	public boolean validConnection(Direction d){
		if( connector ){
			if(portal.from != null)
				return true;
			else 
				return false;
		}
		else if( receiver)
			return to == null ;
		else{
			receiver = true;
			portal.connector = true;
			return true;
		}
	}
	
	@Override
	public void reset(){
		receiver = false;
		connector = false;
		portal.connector = false;
		portal.receiver = false;
		super.reset();
	}

	public void setPortal(Bridge b){
		this.portal = b;
	}
	public Bridge getPortal(){
		return this.portal;
	}
}
