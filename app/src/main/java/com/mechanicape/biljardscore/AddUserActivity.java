/**
 * 
 */
package com.mechanicape.biljardscore;

//import com.example.biljardscore.R.layout;
//import com.example.biljardscore.R.menu;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Player;

import android.app.Activity; 
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
		import android.widget.Button;
import android.widget.EditText; 
import android.widget.Spinner;
import android.widget.Toast;

public class AddUserActivity extends Activity 
{ 
	EditText editName;
	EditText editHandicap;
	EditText editCode;
	Button buttonNewUser;
	Spinner spinnerGroup;
	
	// Database Helper
    DatabaseHelper db;
    Player player;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_adduser);
		// get Instance  of Database Adapter 
		
		
		
		// Get References of Views 
		editName=(EditText)findViewById(R.id.editName); 
		editHandicap=(EditText)findViewById(R.id.editHandicap); 
		editCode=(EditText)findViewById(R.id.editCode);
		editCode.setText(Integer.toString((int)Math.random()));
		
		buttonNewUser=(Button)findViewById(R.id.buttonNewUser);
		buttonNewUser.setOnClickListener(new View.OnClickListener() 
		{
		
			public void onClick(View v) { 

				String name=editName.getText().toString(); 

				
				// check if REQUIRED fields are vacant 
				if(name.equals("") )
				{ 
					Toast.makeText(getApplicationContext(), "Enter a name, caramboles and optional keycode", Toast.LENGTH_LONG).show();
					return; 
				} 
				// check if both password matches
				
				else 
				{
					int groupId=1; //this is for the basic version
					int handicap=Integer.parseInt(editHandicap.getText().toString());
					int code=Integer.parseInt(editCode.getText().toString());
					db = new DatabaseHelper(getApplicationContext());
					player=new Player();
					player.setName(name);
					player.setHandicap(handicap);
					player.setKey(code);
					long playerId=db.createPlayer(player);
					player.setId((int)playerId);
					db.addPlayerGroup((int)playerId, groupId);
					// Save the Data in Database 
					Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_LONG).show(); 
					db.close();	
					finish();
				} 
			} 
			}); 
		
		
		
		
		} 
	
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
		super.onDestroy();
		
		
	}

	protected void end()
	{
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_back, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case  R.id.btnback:
				this.end();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
