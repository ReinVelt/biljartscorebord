/**
 * 
 */
package com.mechanicape.biljardscore;
import java.util.List;

import com.mechanicape.biljardscore.R;
//import com.example.biljardscore.R.layout;
//import com.example.biljardscore.R.menu;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Player;

import android.app.Activity; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

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
		
		buttonNewUser=(Button)findViewById(R.id.buttonNewUser);
		buttonNewUser.setOnClickListener(new View.OnClickListener() 
		{
		
			public void onClick(View v) { 
				int groupId=1; //this is for the basic version
				String name=editName.getText().toString(); 
				int handicap=Integer.parseInt(editHandicap.getText().toString());
				int code=Integer.parseInt(editCode.getText().toString());
				
				// check if REQUIRED fields are vacant 
				if(name.equals("") ) 
				{ 
					Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_LONG).show(); 
					return; 
				} 
				// check if both password matches
				
				else 
				{   db = new DatabaseHelper(getApplicationContext());
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
}
