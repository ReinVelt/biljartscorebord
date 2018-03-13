/**
 * 
 */
package com.mechanicape.biljardscore;
import java.util.List;
import com.mechanicape.biljardscore.R;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Game;
import com.mechanicape.biljardscore.model.Player;
import com.mechanicape.biljardscore.TurnActivity;
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button; 
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class LoadGameActivity extends Activity 
{ 
	Spinner spinnerGame;
	// Database Helper
    DatabaseHelper db;
    Long gameId;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_loadgame);
		// Get References of Views 
		spinnerGame=(Spinner)findViewById(R.id.spinnerGame); 
		db = new DatabaseHelper(getApplicationContext());
		List<String> gamelist = db.getOpenGamesList();
		db.close();
		//gamelist.add("test");
		//List<String> grouplist =new ArrayList<String>();
		ArrayAdapter<String> gameadapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,gamelist);
		spinnerGame.setAdapter(gameadapter);
		gameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
			spinnerGame.setOnItemSelectedListener(new OnItemSelectedListener()
		{
				
			 public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
			 {
			        String[] selectedItems= parent.getItemAtPosition(pos).toString().split(" ");
			        if (selectedItems.length>0)
			        {
				        gameId=Long.parseLong(selectedItems[0]);
				      
				        
				        
				        Toast.makeText(parent.getContext(), 
				                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
				                Toast.LENGTH_LONG).show();
				        
				        
				        Intent myIntent = new Intent(getApplicationContext(), TurnActivity.class); 
						myIntent.putExtra("gameId", gameId);
		                startActivity(myIntent);
		                
		                
		               
			        }
			 }

			
			  

			
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			 
			  
		});
	
		
		
    } 
    
	
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
        db.close(); //IS THIS REQUIRED? OR DOUBLE?
		super.onDestroy();
		
		
	} 
	
	
	
}
