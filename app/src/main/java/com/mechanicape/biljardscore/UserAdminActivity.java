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
//import android.widget.EditText; 
//import android.widget.Toast; 

//import com.mechanicape.biljardscore.helper.DatabaseHelper;
//import com.mechanicape.biljardscore.model.*;

public class UserAdminActivity extends Activity {
	
	
	// Database Helper
    //DatabaseHelper db;
    Button buttonAddUser;
    Button buttonLoadUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //db = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_useradmin);
        
        // Get The Reference Of Buttons 
         buttonAddUser=(Button)findViewById(R.id.buttonAddUser);
         buttonLoadUser=(Button)findViewById(R.id.buttonLoadUser);

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
        
     
       
   


	   // @Override
	   // public boolean onCreateOptionsMenu(Menu menu) {
	   //     // Inflate the menu; this adds items to the action bar if it is present.
	   //     //getMenuInflater().inflate(R.menu.activity_useradmin, menu);
	   //     return true;
	   // }
    
    }
}
