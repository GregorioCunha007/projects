package activities.pack.Activities;

import controls.LevelController;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menu extends Activity {
	
	TextView menu;
	Button tutorial,begginner,crazy;
	
	@Override
	public void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		
		LinearLayout root = new LinearLayout(this);
		
		root.setOrientation(LinearLayout.VERTICAL);
		root.setBackgroundColor(Color.GRAY);
		
		menu = new TextView(this);
		menu.setText("Select package to play");
		menu.setTextSize(15);
		menu.setGravity(Gravity.CENTER);
				
		tutorial = new Button(this);
		tutorial.setText("Tutorial");
		tutorial.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu.this,LevelsActivity.class);
				startActivity(intent);
			}
			
		});
		tutorial.setEnabled(true);

		begginner = new Button(this);
		begginner.setText("Begginer");
		begginner.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu.this,BoardActivity.class);
				startActivity(intent);				
			}
			
		});
		begginner.setEnabled(LevelController.getBegginnerEnable());
		
		crazy = new Button(this);
		crazy.setText("crazy");
		crazy.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu.this,BoardActivity.class);
				startActivity(intent);				
			}
			
		});
		
		crazy.setEnabled(LevelController.getCrazyEnabled());
		
		root.addView(menu);
		root.addView(tutorial);
		root.addView(begginner);
		root.addView(crazy);
		
		setContentView(root);
		
	}
	
}