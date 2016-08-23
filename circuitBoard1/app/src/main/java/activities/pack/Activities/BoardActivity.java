package activities.pack.Activities;
import java.io.InputStream;
import java.util.Scanner;

import com.example.View.Boardview;
import com.example.View.DBridge;
import com.example.View.DDot;
import com.example.View.DMandatory;
import com.example.View.DMandatoryCurve;
import com.example.View.DNozone;
import com.example.View.DTerminal;

import model.Bridge;
import model.Connection;
import model.Game;
import model.NoZone;
import model.Node;
import model.Direction;
import controls.Controller;
import controls.LevelController;
import controls.OnTileTouchListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BoardActivity extends Activity implements OnTileTouchListener,Controller{
	
	public Game model;
	Boardview board;
	TextView nextBoard;
	
	TextView score;
	int [] STATE;
	public final String SAVED = "SAVE_ARRAY";
	final int RED = 0x1;
	final int YELLOW = 0x2;
	final int GREEN = 0x3;
	final int BLUE = 0x4;
	final int NO_COLOR = 0x5;
	final int NULL_STATE = 0xF;
	LinearLayout ll;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLevel();
    }

	private void loadLevel() {
		
		ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        
        board = new Boardview(this);
        board.setListener(this);
        board.setController(this);
        
        load();
        
        nextBoard = new Button(this);
	    nextBoard.setText("Next Board");
	    nextBoard.setOnClickListener(new OnClickListener(){
	    	@Override
			public void onClick(View v) {
	    		++LevelController.level;
	    		if( LevelController.maxLevel == 13){
	    			LevelController.level=1;
	    			++LevelController.currentPack;
	    			Intent intent = new Intent(BoardActivity.this,Menu.class);
	    			startActivity(intent);
	    		}
	    		LevelsActivity.refreshEnable();
	    		loadLevel();
	    	}
	     });
	    
	    nextBoard.setEnabled(false);
	    nextBoard.setVisibility(Button.INVISIBLE);
	     
        board.setGridSize(model.max_x_size,model.max_y_size);
        board.setBackgroundColor(Color.DKGRAY);

        
        ll.addView(board);
	    ll.addView(nextBoard);
       
	    setContentView(ll);
	}

	private void load() {
		
		try{
			InputStream in = getResources().openRawResource(R.raw.tutoriallevels);
			Scanner i = new Scanner(in);
			int z=0,w=0;
			char c;
			String s;
			Boolean levelNotFound = false;
			for(;!levelNotFound;){
				s = i.nextLine();
				if(s.charAt(0)=='#'){
					if(s.charAt(1) - '0' == LevelController.level){
					z  = s.charAt(3) - '0';
				    w = s.charAt(5) - '0';
					levelNotFound=true;
					}
				}
			}
			model = new Game(z,w);
			board.updateBoard();
			int pair = 2,counter=0;
			Bridge b1 = null,b2 = null;
			for(int x=0;x < model.max_y_size ; ++x){
				s = i.nextLine();
				for(int y=0;y < model.max_x_size;++y){
					c = s.charAt(y);
					if(c>='A' && c<'A'+ model.MAXCOLOR){
						model.board[x][y] = new Node(x,y,c-'A');
						board.Drawables.add(new DTerminal(board,x,y,model.board[x][y].color));
					}
					else if( c>='Y' && c<'Y' + model.MAXCOLOR){
						++counter;
						if( counter == pair )model.board[x][y] = (b2 = new Bridge(x,y,c-'Y',false,false));
						else model.board[x][y] = (b1 = new Bridge(x,y,c-'Y',false,false));
						board.Drawables.add(new DBridge(board,x,y,model.board[x][y].color));
					}
					else if(c==')'){
						model.board[x][y] = new Connection(x,y,Direction.LEFT,Direction.UP);
						board.Drawables.add(new DMandatoryCurve(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0],((Connection)model.board[x][y]).mDirs[1]));
					}
					else if( c=='('){
						model.board[x][y] = new Connection(x,y,Direction.RIGHT,Direction.UP);
						board.Drawables.add(new DMandatoryCurve(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0],((Connection)model.board[x][y]).mDirs[1]));
					}
					else if( c=='u'){
						model.board[x][y] = new Connection(x,y,Direction.LEFT,Direction.DOWN);
						board.Drawables.add(new DMandatoryCurve(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0],((Connection)model.board[x][y]).mDirs[1]));
					}
					else if( c=='v'){
						model.board[x][y] = new Connection(x,y,Direction.RIGHT,Direction.DOWN);
						board.Drawables.add(new DMandatoryCurve(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0],((Connection)model.board[x][y]).mDirs[1]));
					}
					else if (c=='.') {
						model.board[x][y] = new Connection(x,y);
						board.Drawables.add(new DDot(board,x,y,model.board[x][y].color));
					}
					else if (c=='*'){
						model.board[x][y] = new NoZone(x,y);
						board.Drawables.add(new DNozone(x,y));
					}
					else if (c=='|'){
						model.board[x][y] = new Connection(x,y,Direction.UP, Direction.DOWN);
						board.Drawables.add(new DMandatory(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0]));
					}
					else if (c=='-') {
						model.board[x][y] = new Connection(x,y,Direction.LEFT, Direction.RIGHT);
						board.Drawables.add(new DMandatory(board,x,y,model.board[x][y].color,((Connection)model.board[x][y]).mDirs[0]));
					}
				}
			}
			b2.setPortal(b1);
			b1.setPortal(b2);
			i.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public void onRestart(){
		super.onRestart();
	}
	
	@Override
	public boolean onMove(int xFrom , int yFrom, int xTo, int yTo, Node n1, boolean onRemoval) {
		
		Direction dir = Direction.find(xTo - xFrom, yTo - yFrom);
		if( dir == null)return false;
		
		if(!onRemoval)
			model.addConnection(n1, dir);
		else 
			model.removeConnection(n1, dir);
		
		if(model.checkEnd())nextBoard();
		
		return true;
	}


	@Override
	public boolean resetPassage(Node n1) {
		model.removeConnection(n1);
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		STATE = new int	[model.max_x_size * model.max_y_size];
		code();
		outState.putIntArray(SAVED,STATE);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle state) {
		STATE = state.getIntArray(SAVED);
	    decode();
		super.onRestoreInstanceState(state);
	}
	
	private void code(){
		int i = 0;
		for(int y=0;y < model.max_y_size ; ++y)
			for(int x=0;x < model.max_x_size; ++x, ++i){
				while(!(model.board[y][x] instanceof NoZone)){
					
					 STATE[i] |= choseColor(model.board[y][x].color);  
					 STATE[i]<<=4;
					
					
					 STATE[i] |= model.board[y][x].from == null ? NULL_STATE : model.board[y][x].from.ordinal();
					 STATE[i]<<=4;
	
					
					 STATE[i] |= model.board[y][x].to == null ? NULL_STATE : model.board[y][x].to.ordinal();
					 STATE[i]<<=4;
					 
					 break;
				}
			}
		
	}
	
	private void decode(){
		int mask = 0xF000;

		for( int i = 0 ; i < STATE.length ; ++i) {
			mask = 0xF000;
			for( ; mask > 0 ;mask>>=4 ){
				if( STATE[i] == 0 ){
					continue;
				}
				int result = STATE[i] & mask; 
				if( mask == 0xF000 ){
					result>>=12;
					giveColor(result,i);
				}
				if( mask == 0x0F00 ){
					result>>=8;
					giveFrom(result,i);
				}
				if( mask == 0x00F0 ){
					result>>=4;
					giveTo(result,i);
				}

			}
		}
		
	}


	private int choseColor(int c){
		
		if( c == -1)c=NO_COLOR;
		else if( c == 0)c=RED;
		else if( c ==1 )c=BLUE;
		else if(c == 2)c=GREEN;
		else if( c == 3)c=YELLOW;
		
		return c;
		
	}
	
	private void giveColor( int color , int i){
		int y = i / model.max_y_size ;
		int x = i % model.max_x_size ;
		if( color == RED )color = 0;
		else if( color == BLUE )color = 1;
		else if( color == GREEN )color = 2;
		else if( color == YELLOW )color = 3;
		else if( color == NO_COLOR)color = -1;
		model.board[y][x].color = color;
	}
	
	private void giveTo( int result , int i){
		int y = i / model.max_y_size;
		int x = i % model.max_x_size;
		Direction d = null;
		if( result == 0) d = Direction.LEFT;
		if( result == 1) d = Direction.UP;
		if( result == 2) d = Direction.RIGHT;
		if( result == 3) d = Direction.DOWN;
		model.board[y][x].to = d;
		
	}
	private void giveFrom( int result , int i){
		int y = i / model.max_y_size;
		int x = i % model.max_x_size;
		Direction d = null;
		if( result == 0) d = Direction.LEFT;
		if( result == 1) d = Direction.UP;
		if( result == 2) d = Direction.RIGHT;
		if( result == 3) d = Direction.DOWN;
		model.board[y][x].from = d;
		
	}
	
	private void nextBoard(){
		nextBoard.setVisibility(Button.VISIBLE);
		nextBoard.setEnabled(true);
	}

	@Override
	public int getXSize() {
		return model.max_x_size;
	}

	@Override
	public int getYSize() {
		return model.max_y_size;
	}

	@Override
	public boolean isLocked(Node n1) {
		return model.isLocked(n1);
	}

	@Override
	public Node getNode(int y,int x) {
		return model.board[y][x];
	}
	
}
