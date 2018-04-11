/**
 * 
 */
package com.mechanicape.biljardscore;
import java.util.List;

//import com.example.biljardscore.R.layout;
//import com.example.biljardscore.R.menu;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Player;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button; 
import android.widget.CheckBox;
import android.widget.EditText; 
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditUserActivity extends Activity 
{ 
	EditText editId;
	EditText editName;
	EditText editHandicap;
	EditText editCode;
	CheckBox editStatus;
	Button buttonSaveUser;
	Spinner spinnerPlayerId;
	long playerId=0;
	
	// Database Helper
    DatabaseHelper db;
    Player player;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_edituser);
		// get Instance  of Database Adapter 
		
		spinnerPlayerId=(Spinner)findViewById(R.id.spinnerPlayerId);
		
		// Get References of Views 
		editId=(EditText)findViewById(R.id.editId); 
		editName=(EditText)findViewById(R.id.editName); 
		editHandicap=(EditText)findViewById(R.id.editHandicap); 
		editCode=(EditText)findViewById(R.id.editCode); 
		editStatus=(CheckBox)findViewById(R.id.editStatus); 
		db = new DatabaseHelper(getApplicationContext());
		List<String> userlist = db.getPlayerList();
		//gamelist.add("test");
		//List<String> grouplist =new ArrayList<String>();
		ArrayAdapter<String> playeradapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,userlist);
		spinnerPlayerId.setAdapter(playeradapter);
		playeradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerPlayerId.setOnItemSelectedListener(new OnItemSelectedListener()
		{
				
			 public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
			 {
			        String[] selectedItems= parent.getItemAtPosition(pos).toString().split(" ");
			        if (selectedItems.length>0 && pos>0)
			        {
			        	
				        playerId=Long.parseLong(selectedItems[0]);
				        Player player=db.getPlayer(playerId);
				        editId.setText(Integer.toString(player.getId()));
				        editName.setText(player.getName());
				        editHandicap.setText(Integer.toString(player.getHandicap()));
				        editCode.setText(Integer.toString(player.getKey()));
				        editStatus.setChecked(player.getStatus()!=1);
				        
				        Toast.makeText(parent.getContext(), 
				                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
				                Toast.LENGTH_LONG).show();
				        
				        
				        //Intent myIntent = new Intent(getApplicationContext(), TurnActivity.class); 
						//myIntent.putExtra("playerId", gameId);
		                //startActivity(myIntent);
		                
		                
		               
			        }
			 }

			
			  

			
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			 
			  
		});
	
		buttonSaveUser=(Button)findViewById(R.id.buttonSaveUser);
		buttonSaveUser.setOnClickListener(new View.OnClickListener() 
		{
		
			public void onClick(View v) { 
				//int groupId=1; //this is for the basic version
				String name=editName.getText().toString(); 
				int playerId=Integer.parseInt(editId.getText().toString());
				int handicap=Integer.parseInt(editHandicap.getText().toString());
				int code=Integer.parseInt(editCode.getText().toString());
				int status=editStatus.isChecked()?0:1;
				
				// check if REQUIRED fields are vacant 
				if(name.equals("")|| code<1 || handicap<1 || playerId<1)
				{
					if (playerId<1)
					{
						Toast.makeText(getApplicationContext(), "Select a player", Toast.LENGTH_LONG).show();
						return;
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Enter a name and handicap", Toast.LENGTH_LONG).show();
						return;
					}
				} 	
				else 
				{   //db = new DatabaseHelper(getApplicationContext());
					player=db.getPlayer(playerId);
					player.setName(name);
					player.setHandicap(handicap);
					player.setKey(code);
					player.setStatus(status);
					db.updatePlayer(player);
					//db.close();
					// Save the Data in Database 
					Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_LONG).show();
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
