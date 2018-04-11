package com.mechanicape.biljardscore;


//import com.example.biljardscore.R.layout;
//import com.example.biljardscore.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import android.app.Dialog;  
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
//import android.widget.EditText; 
//import android.widget.Toast; 

import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.*;

public class UserAdminActivity extends Activity {
	
	
	// Database Helper
    //DatabaseHelper db;
    Button buttonAddUser;
    Button buttonLoadUser;
    ListView listviewUser;
	DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //db = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_useradmin);
        
        // Get The Reference Of Buttons 
         buttonAddUser=(Button)findViewById(R.id.buttonAddUser);
         buttonLoadUser=(Button)findViewById(R.id.buttonLoadUser);
         listviewUser=(ListView) findViewById(R.id.listViewUser);
		db = new DatabaseHelper(getApplicationContext());
		List<String> userlist = db.getPlayerList();
		//gamelist.add("test");
		//List<String> grouplist =new ArrayList<String>();
		ArrayAdapter<String> playeradapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1,userlist);
		listviewUser.setAdapter(playeradapter);
		playeradapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // Set OnClick Listener on adduser button 
        buttonAddUser.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),AddUserActivity.class); 
		        startActivity(registerintent); 
		    } 
	    }); 
       
        // Set OnClick Listener on adduser button 
        buttonLoadUser.setOnClickListener(new View.OnClickListener() 
        { 
        	@Override
	        public void onClick(View v) 
	        { 
		        /// Create Intent for SignUpActivity  and Start The Activity 
		        Intent registerintent=new Intent(getApplicationContext(),EditUserActivity.class); 
		        startActivity(registerintent); 
		    } 
	    });










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
