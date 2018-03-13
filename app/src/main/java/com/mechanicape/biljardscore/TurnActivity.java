package com.mechanicape.biljardscore;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mechanicape.biljardscore.R;
import com.mechanicape.biljardscore.R.layout;
import com.mechanicape.biljardscore.R.menu;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.*;


import android.app.Activity; 
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle; 
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
//import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.LinearLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
public class TurnActivity extends Activity {

	
	// Database Helper
    DatabaseHelper db;
    Game game;
    LinearLayout player1,player2;
    TextView player1Name,p1newPoints,player1Points,player1Handicap,player1Id,player1Turns,player1Score,player1Avg,player1Max;
    TextView player2Name,p2newPoints,player2Points,player2Handicap,player2Id,player2Turns,player2Score,player2Avg,player2Max;
    Button player1button,player2button;
    int p1Id,p2Id;
	
	@Override 
	

	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_turn);
		Bundle extras = getIntent().getExtras();
		int gameId=1;
		if (extras != null) {
		    gameId  = (int)extras.getLong("gameId");
		}
				// get Instance  of Database Adapter 
		
		db = new DatabaseHelper(getApplicationContext());
		//Toast.makeText(getApplicationContext(), "dummy", Toast.LENGTH_LONG).show();
		game=db.loadGame(gameId);
		//player1=(LinearLayout)findViewById(R.id.player1);
		//player2=(LinearLayout)findViewById(R.id.player2);
		
		player1Name=(TextView)findViewById(R.id.player1Name);
		player1Points=(TextView)findViewById(R.id.player1Points);
		player1Handicap=(TextView)findViewById(R.id.player1Handicap);
		player2Name=(TextView)findViewById(R.id.player2Name);
		player2Points=(TextView)findViewById(R.id.player2Points);
		player2Handicap=(TextView)findViewById(R.id.player2Handicap);
		player1Id=(TextView)findViewById(R.id.player1Id);
		player2Id=(TextView)findViewById(R.id.player2Id);
		player1Turns=(TextView)findViewById(R.id.player1Turns);
		player2Turns=(TextView)findViewById(R.id.player2Turns);
		player1Score=(TextView)findViewById(R.id.player1Score);
		player2Score=(TextView)findViewById(R.id.player2Score);
		player1Avg=(TextView)findViewById(R.id.player1Avg);
		player2Avg=(TextView)findViewById(R.id.player2Avg);
		player1Max=(TextView)findViewById(R.id.player1Max);
		player2Max=(TextView)findViewById(R.id.player2Max);
		
		player1button=(Button)findViewById(R.id.player1button);
		player2button=(Button)findViewById(R.id.player2button);
		p1Id=game.getPlayerId(0);
		p2Id=game.getPlayerId(1);

		player1Name.setText(game.getPlayer(0).getName().toString());
		player1Points.setText(Integer.toString(game.getPointsByPlayerId(p1Id)));
		player1Handicap.setText(Integer.toString(game.getPlayer(0).getHandicap()));
		player1Id.setText(Integer.toString(game.getPlayer(0).getId()));
		
		player2Name.setText(game.getPlayer(1).getName().toString());
		player2Points.setText(Integer.toString(game.getPointsByPlayerId(p2Id)));
		player2Handicap.setText(Integer.toString(game.getPlayer(1).getHandicap()));
		player2Id.setText(Integer.toString(game.getPlayer(1).getId()));
		
		
		//@todo - people complained textsize is too small at large screens}
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		player1Name.setWidth((width-00)/2);
		player2Name.setWidth((width-00)/2);
		//player1Points.setTextSize(86);
		//player2Points.setTextSize(86);
		
		this.updateFieldVisibility(gameId);
		
		//db.close();
		p1newPoints=(EditText)findViewById(R.id.player1point);
		p2newPoints=(EditText)findViewById(R.id.player2point);
		
		
        player1button.setOnClickListener(new View.OnClickListener( ){
        public void onClick(View v)
    	{
        	String inputStr=p1newPoints.getText().toString();
        	if (isInputOk(inputStr))
        	{
        		updatePlayer1Points();
        	}
    	}
        });
        
        player2button.setOnClickListener(new View.OnClickListener( ){
        public void onClick(View v)
        {
        	String inputStr=p2newPoints.getText().toString();
        	if (isInputOk(inputStr))
        	{
        		updatePlayer2Points();
        	}
        }
        });
        
        
        
		
    } 
	
	public boolean isInputOk(String inputString)
	{
		boolean validated=false;
		if (inputString.length()>0 && inputString.length()<4)
		{
			if (android.text.TextUtils.isDigitsOnly(inputString))
			{
			   validated=true;
			}
		}
		return validated;
	}
	
	public void endGame()
	{
		db.close();
		//Intent myIntent = new Intent(getApplicationContext(), GameStatsActivity.class); 
		//myIntent.putExtra("gameId",  game.getId());
        //startActivityForResult(myIntent,1);
		
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);
		finish();
		
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
			
			 
	           switch (keyCode) {
	              case KeyEvent.KEYCODE_NUMPAD_ENTER:
	              case KeyEvent.KEYCODE_ENTER:
	            	  updatePlayerPoints();
	                  return true;	 
	              case KeyEvent.KEYCODE_PLUS:
	              case KeyEvent.KEYCODE_NUMPAD_ADD:  
	            	   addPointsByKeyboard();
	            	   return true;
	              case KeyEvent.KEYCODE_MINUS:
	              case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
	            	  subtractPointsByKeyboard();
	            	  return true;
	              case KeyEvent.KEYCODE_NUMPAD_DIVIDE:
	              //case KeyEvent.KEYCODE_NUMPAD_DOT:
	              case KeyEvent.KEYCODE_NUMPAD_LEFT_PAREN:
	            	  this.undo();
	            	  return true;
	              case KeyEvent.KEYCODE_NUMPAD_MULTIPLY:
	            	  if (game.getGameStatus()==3)
	            	  {
		            	  game.setEnd(System.currentTimeMillis()/1000);
					      db.updateGame(game);
					      this.endGame();
	            	  }
	            	  return true;
	              default:
	            	  //int key=keyCode;
				  
	           }
	           return super.onKeyUp(keyCode, event);
      }
	
	
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
        db.close(); //IS THIS REQUIRED? OR DOUBLE?
		super.onDestroy();
		
		
	} 
	
	public void addPointsByKeyboard()
	{
		EditText p1newPoints=(EditText)findViewById(R.id.player1point);
		EditText p2newPoints=(EditText)findViewById(R.id.player2point);
		if (p1newPoints.getVisibility()==View.VISIBLE)
		{
			if (isInputOk(p1newPoints.getText().toString()))
			{
				p1newPoints.setText(Integer.toString(Integer.parseInt("0"+p1newPoints.getText().toString())+1));
			} 
			
			if (p1newPoints.getText().length()<1)
			{
				p1newPoints.setText("1");
			}
			
			p1newPoints.setSelection(p1newPoints.getText().length());
		}
		else
		{
			if (isInputOk(p2newPoints.getText().toString()))
			{
				p2newPoints.setText(Integer.toString(Integer.parseInt("0"+p2newPoints.getText().toString())+1));
			   
			}
			if (p2newPoints.getText().length()<1)
			{
				p2newPoints.setText("1");
				
			}
			p2newPoints.setSelection(p2newPoints.getText().length());
		}
	}
	
	public void subtractPointsByKeyboard()
	{
		EditText p1newPoints=(EditText)findViewById(R.id.player1point);
		EditText p2newPoints=(EditText)findViewById(R.id.player2point);
		if (p1newPoints.getVisibility()==View.VISIBLE )
		{
			if (p1newPoints.getText().toString()!="0")
			{
				int newpoint=Integer.parseInt("0"+p1newPoints.getText().toString())-1;
				if (newpoint<0) {newpoint=0;}
				p1newPoints.setText(Integer.toString(newpoint));
				p1newPoints.setSelection(p1newPoints.getText().length());
			} 
		}
		else
		{
			if (p2newPoints.getText().toString()!="0")
			{
				int newpoint=Integer.parseInt("0"+p2newPoints.getText().toString())-1;
				if (newpoint<0) {newpoint=0;}
				p2newPoints.setText(Integer.toString(newpoint));
				p2newPoints.setSelection(p2newPoints.getText().length());
			}
		     
		}
	}
	
	
	public void undo()
	{
		
		List <Turn>t =db.getTurnsByGameId(this.game.getId());
		if (t.size()>0)
		{
			Turn u=t.get(t.size()-1);
			db.undoTurn(u.getId());
			this.game=db.loadGame(this.game.getId());
		}
  	    
  	    
  	  
		player1Points.setText(Integer.toString(game.getPointsByPlayerId(game.getPlayerId(0))));
		player1Turns.setText(Integer.toString(db.getTurnsByGameId(game.getId(),game.getPlayerId(0)).size()));

		player2Points.setText(Integer.toString(game.getPointsByPlayerId(game.getPlayerId(1))));
		player2Turns.setText(Integer.toString(db.getTurnsByGameId(game.getId(),game.getPlayerId(1)).size()));
		
		this.updateFieldVisibility(game.getId());
	}
	
	public void updatePlayerPoints()
	{
		//EditText p1newPoints=(EditText)findViewById(R.id.player1point);
		//EditText p2newPoints=(EditText)findViewById(R.id.player2point);
		if (game.getGameStatus()!=3)
		{
			if (p1newPoints.getVisibility()==View.VISIBLE)
			{
				String inputStr=p1newPoints.getText().toString();
	        	if (isInputOk(inputStr))
	        	{
	        		updatePlayer1Points();
	        	}
			}
			else
			{
				
				String inputStr=p2newPoints.getText().toString();
	        	if (isInputOk(inputStr))
	        	{
	        		updatePlayer2Points();
	        	}
			}
		
		}
	}
	
	public void updatePlayer1Points()
	{
		TextView p1Id=(TextView)findViewById(R.id.player1Id);
        EditText p1newPoints=(EditText)findViewById(R.id.player1point);
        TextView p1Turns=(TextView)findViewById(R.id.player1Turns);
        //TextView p1TurnsLeft=(TextView)findViewById(R.id.player1TurnsLeft);
       
    	int player1Id=Integer.parseInt(p1Id.getText().toString());
    	int newpoints=Integer.parseInt("0"+p1newPoints.getText().toString());
    	Turn t=new Turn();	
    	t.setGameId(game.getId());
    	t.setPlayerId(player1Id);
    	t.setPoints(newpoints);
    	t.setCreated(1);
    	game.addTurn(t);
    	db.createTurn(t);
    	int points=game.getPointsByPlayerId(player1Id);
		//player1Points=(TextView)findViewById(R.id.player1Points);
		player1Points.setText(Integer.toString(points));
		p1newPoints.setText("");
		p1newPoints.setHint("0");
		p1newPoints.setSelection(p1newPoints.getText().length());
		int turncount=db.getTurnsByGameId(game.getId(),player1Id).size();
		p1Turns.setText(Integer.toString(turncount));
		//p1TurnsLeft.setText("("+Integer.toString(game.getPlayer(0).getHandicap()-turncount)+")");
		//Toast.makeText(getApplicationContext(), game.getPlayer(0).getName()+" got "+newpoints+" points", Toast.LENGTH_LONG).show();
		updateFieldVisibility(game.getId());
	}
	//@TODO screen updatecode en db tranactie loskoppelen zodat het apart gebruikt kan worden
	public void updatePlayer2Points()
	{
		TextView p2Id=(TextView)findViewById(R.id.player2Id);
        EditText p2newPoints=(EditText)findViewById(R.id.player2point);
        TextView p2Turns=(TextView)findViewById(R.id.player2Turns);
        //TextView p2TurnsLeft=(TextView)findViewById(R.id.player2TurnsLeft);
    	int player2Id=Integer.parseInt(p2Id.getText().toString());
    	int newpoints=Integer.parseInt("0"+p2newPoints.getText().toString());
    	Turn t=new Turn();	
    	t.setGameId(game.getId());
    	t.setPlayerId(player2Id);
    	t.setPoints(newpoints);
    	t.setCreated(1);
    	game.addTurn(t);
    	db.createTurn(t);
    	int points=game.getPointsByPlayerId(player2Id);
		//player2Points=(TextView)findViewById(R.id.player2Points);
		player2Points.setText(Integer.toString(points));
		p2newPoints.setText("");
		p2newPoints.setHint("0");
		p2newPoints.setSelection(p2newPoints.getText().length());
		int turncount=db.getTurnsByGameId(game.getId(),player2Id).size();
		p2Turns.setText(Integer.toString(turncount));
		//p2TurnsLeft.setText("("+Integer.toString(game.getPlayer(1).getHandicap()-turncount)+ ")");
		//Toast.makeText(getApplicationContext(), game.getPlayer(1).getName()+" got "+newpoints+" points", Toast.LENGTH_LONG).show();
		updateFieldVisibility(game.getId());
	}
	
	private void hideKeyboard() {   
	    // Check if no view has focus:
	    View view = this.getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	
	public void updateFieldVisibility(int gameId){
		//the last player who did the last turn is not active (hidden fields)
		//the other player is active
		Turn t=db.getLastPlayerByGameId(gameId);
		EditText p1newPoints=(EditText)findViewById(R.id.player1point);
		EditText p2newPoints=(EditText)findViewById(R.id.player2point);
		int lastPlayerId=t.getPlayerId();
		if (lastPlayerId==p1Id )
		{
			//hide player 1
			//Toast.makeText(getApplicationContext(), game.getPlayer(1).getName()+" is now playing", Toast.LENGTH_SHORT).show(); 
			//Toast.makeText(getApplicationContext(), game.getPointsRemaining(1)+" caramboles to go", Toast.LENGTH_SHORT).show();
			player1button.setVisibility(View.INVISIBLE);
			p1newPoints.setVisibility(View.INVISIBLE);
			player2button.setVisibility(View.VISIBLE);
			p2newPoints.setVisibility(View.VISIBLE);
			
			p2newPoints.setCursorVisible(true);
			p2newPoints.requestFocus();
			p2newPoints.setSelection(p2newPoints.getText().length());
			
			
		}
		else
		{
			//hide player 2
			//Toast.makeText(getApplicationContext(), game.getPlayer(0).getName()+" is now playing...", Toast.LENGTH_SHORT).show(); 
			//Toast.makeText(getApplicationContext(), game.getPointsRemaining(0)+" caramboles to go", Toast.LENGTH_SHORT).show();		
			player1button.setVisibility(View.VISIBLE);
			p1newPoints.setVisibility(View.VISIBLE);
			player2button.setVisibility(View.INVISIBLE);
			p2newPoints.setVisibility(View.INVISIBLE);
			
			
			p1newPoints.setCursorVisible(true);
			p1newPoints.requestFocus();
			p1newPoints.setSelection(p1newPoints.getText().length());
		}
		
		
		
		switch (game.getGameStatus())
		{
			case 0: break;
			case 1: Toast.makeText(getApplicationContext(),"CARAMBOLE GOAL REACHED", Toast.LENGTH_SHORT).show();	break;
			case 2: Toast.makeText(getApplicationContext(),"LAST TURN", Toast.LENGTH_SHORT).show();	break;
			case 3: 
				    //Toast.makeText(getApplicationContext(),"END OF GAME", Toast.LENGTH_LONG).show();	
			        game.setEnd(System.currentTimeMillis()/1000);
			        db.updateGame(game);
			        //Toast.makeText(getApplicationContext(),"GAME SAVED", Toast.LENGTH_SHORT).show();
			        Toast.makeText(getApplicationContext(),"END OF GAME.....PRESS * TO EXIT GAME", Toast.LENGTH_LONG).show();
			        player1Score.setText(Integer.toString(game.getGameScore(p1Id)));
					player1Avg.setText(String.format("%.3f",game.getAverageCarambolesByPlayerId(p1Id)));;
					player1Max.setText(Integer.toString(game.getMostCarambolesByPlayerId(p1Id)));
					player2Score.setText(Integer.toString(game.getGameScore(p2Id)));
					player2Avg.setText(String.format("%.3f", game.getAverageCarambolesByPlayerId(p2Id)));;
					player2Max.setText(Integer.toString(game.getMostCarambolesByPlayerId(p2Id)));
			        player1button.setVisibility(View.INVISIBLE);
					p1newPoints.setVisibility(View.INVISIBLE);
					player2button.setVisibility(View.INVISIBLE);
					p2newPoints.setVisibility(View.INVISIBLE);
					p1newPoints.clearFocus();
					p2newPoints.clearFocus();
					this.hideKeyboard();
			        //super.finish();
			        //this.finish();
			        
			        break;
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_turn, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case  R.id.turnUndo:
				this.undo();
				return true;
			case R.id.gameFinalize:
				this.endGame();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
