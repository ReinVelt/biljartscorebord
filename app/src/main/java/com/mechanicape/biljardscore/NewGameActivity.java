/**
 * 
 */
package com.mechanicape.biljardscore;
import java.util.List;

import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Game;
import com.mechanicape.biljardscore.model.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class NewGameActivity extends Activity 
{ 
	
	Spinner spinnerPlayer1;
	Spinner spinnerPlayer2; 
	Button buttonNewGame;
	Game game;
	// Database Helper
    DatabaseHelper db;
    Player player;
    //Group groups;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_newgame);
		// Get References of Views 
		
		spinnerPlayer1=(Spinner)findViewById(R.id.spinnerPlayer1); 
		spinnerPlayer2=(Spinner)findViewById(R.id.spinnerPlayer2); 
		db = new DatabaseHelper(getApplicationContext());
		
		List<String> playerlist = db.getPlayerList();
		//List<String> grouplist =new ArrayList<String>();
		ArrayAdapter<String> player1adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,playerlist);
		ArrayAdapter<String> player2adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,playerlist);
		
		
		spinnerPlayer1.setAdapter(player1adapter);
		player1adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerPlayer2.setAdapter(player2adapter);
		player2adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	    
		
		
		
		buttonNewGame=(Button)findViewById(R.id.buttonNewGame);
		buttonNewGame.setOnClickListener(new View.OnClickListener() 
		{
		
			public void onClick(View v) { 
				playGame();
			} 
		}); 
		
    } 
	

	
	public void playGame()
	{
	
		int player1Id=spinnerPlayer1.getSelectedItemPosition(); 
		int player2Id=spinnerPlayer2.getSelectedItemPosition();
		
		
		
		// check if REQUIRED fields are vacant 
		if( player1Id==0|| player2Id==0 )
		{ 
			
	
			Toast.makeText(getApplicationContext(), "Select 2 players", Toast.LENGTH_LONG).show(); 
			
		} 
		// check if both password matches
		
		else 
		{   //db = new DatabaseHelper(getApplicationContext());
			//int player1Id=Integer.parseInt(player1Fields[2]);
			//int player2Id=Integer.parseInt(player2Fields[2]);
			game=new Game();
			game.setGroupId(1);
			game.addPlayer(db.getPlayer(player1Id));
			game.addPlayer(db.getPlayer(player2Id));
			game.setStart(System.currentTimeMillis()/1000);
			long gameId=db.createGame(game);
			game.setId((int)gameId);
			// Save the Data in Database 
			Toast.makeText(getApplicationContext(), "Registered Successfully ", Toast.LENGTH_LONG).show(); 
			//db.close();
		
			Intent myIntent = new Intent(getApplicationContext(), TurnActivity.class); 
			myIntent.putExtra("gameId", gameId);
            startActivityForResult(myIntent,1);
            
		} 
	}
    
	
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
        db.close(); //IS THIS REQUIRED? OR DOUBLE?
		super.onDestroy();
		
		
	} 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    //if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
	            finish();
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        	finish();
	        }
	    //}
	}//onActivityRe
	
	
	protected void initPlayers(Integer groupId){
		db = new DatabaseHelper(getApplicationContext());
		//List<String> playerlist =new ArrayList<String>();
		List<String> playerlist = db.getPlayerListByGroupId(groupId);
        ArrayAdapter<String> playeradapter1 = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,playerlist);
        ArrayAdapter<String> playeradapter2 = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,playerlist);
        spinnerPlayer1.setAdapter(playeradapter1);
        spinnerPlayer2.setAdapter(playeradapter2);
        playeradapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playeradapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        db.close();  
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
