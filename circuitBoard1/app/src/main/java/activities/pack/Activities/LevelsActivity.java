package activities.pack.Activities;

import java.io.InputStream;
import java.util.Scanner;

import controls.LevelController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class LevelsActivity extends Activity {
	
	public int numberOflevels;
	static Button [] buttons ;
	
	@Override
	public void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		
		++LevelController.currentPack;
		numberOflevels = loadPackageLevel();
		buttons = new Button[numberOflevels];
		LevelController.maxLevel = numberOflevels;
		
		LinearLayout root = new LinearLayout(this);
		
		root.setOrientation(LinearLayout.VERTICAL);
		
		for(int i = 1; i <= numberOflevels ; ++i){
			buttons[i-1] = createButton(this,i);
			root.addView(buttons[i-1]);
		}
		buttons[0].setEnabled(true);
		setContentView(root);
		
	}

	
	private Button createButton(Context ctx, int index){
			
		Button exe = new Button(ctx);
		exe.setId(index);
		exe.setText(Integer.toString(index));
		exe.setEnabled(false);
		exe.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				LevelController.level = v.getId();
				Intent i = new Intent(LevelsActivity.this,BoardActivity.class);
				startActivity(i);
			}
		});
		
		return exe;
		
	}
	
	private int loadPackageLevel(){
		int l=0;
		try{
			InputStream in = getResources().openRawResource(R.raw.packages);
			Scanner i = new Scanner(in);
			String s;
			s = i.nextLine();
			for(int c=0;i.hasNext();++c){
				if( s.charAt(c) - '0' != LevelController.currentPack )continue;
				if( s.charAt(++c) == '#'){
					l = s.charAt(++c) - '0';
					while(s.charAt(++c) != ' ')l*=10+(s.charAt(c)-'0');
				}
			}
			
			i.close();	
		}catch(Exception e){
		e.printStackTrace();
		
		}
		return l;
	}	
	
	public static void refreshEnable(){
		buttons[LevelController.level-1].setEnabled(true);
    }
	
}
