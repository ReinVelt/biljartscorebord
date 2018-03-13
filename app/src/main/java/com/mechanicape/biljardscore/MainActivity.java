package com.mechanicape.biljardscore;


import com.mechanicape.biljardscore.R;
//import com.example.biljardscore.R.layout;
//import com.example.biljardscore.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import android.app.Dialog;  
import android.content.Intent; 
import android.view.View; 
import android.widget.Button; 
import android.widget.TextView;
//import android.widget.EditText; 
//import android.widget.Toast; 

//import com.mechanicape.biljardscore.helper.DatabaseHelper;
//import com.mechanicape.biljardscore.model.*;

public class MainActivity extends Activity {
	
	
	// Database Helper
    //DatabaseHelper db;
    Button buttonUserAdmin;
    Button buttonGameStats;
    Button buttonNewGame;
    Button buttonLoadGame;
    TextView textCopyright;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //db = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_main);
        
        // Get The Reference Of Buttons 
         buttonUserAdmin=(Button)findViewById(R.id.buttonUserAdmin);
         buttonGameStats=(Button)findViewById(R.id.buttonGameStats);
         buttonNewGame=(Button)findViewById(R.id.buttonNewGame);
         buttonLoadGame=(Button)findViewById(R.id.buttonLoadGame);
         textCopyright=(TextView)findViewById(R.id.textCopyright);
         textCopyright.setText("Biljardscorebord v1.3 - stichting Theo's Mechanische Aap");

        // Set OnClick Listener on adduser button 
        buttonUserAdmin.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),UserAdminActivity.class); 
		        startActivity(registerintent); 
		    } 
	    }); 
        
        // Set OnClick Listener on adduser button 
        buttonGameStats.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),GameStatsActivity.class); 
		        startActivity(registerintent); 
		    } 
	    }); 
        
     // Set OnClick Listener on newgame button 
        buttonNewGame.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),NewGameActivity.class); 
		        startActivity(registerintent); 
		    } 
	    }); 
        
     // Set OnClick Listener on loadgame button 
        buttonLoadGame.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),LoadGameActivity.class); 
		        startActivity(registerintent); 
		    } 
	    }); 
    }

  
       
   


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
