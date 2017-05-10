package com.example.cjhmdpack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;

public class actChangePassword extends Activity 
{
	    TextView currentPassword, newPassword, confirmPassword;
	    Button confirm, x;
	    final int ZERO = 0;
	    String password;
	    
		public void onCreate(Bundle savedInstanceState)
		{
			final SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
			password = prefs.getString("password", "PWAs1H15");
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.change_password);  
	        
	        currentPassword = (TextView)this.findViewById(R.id.currentPassword);
	        newPassword = (TextView)this.findViewById(R.id.newPassword);
	        confirmPassword = (TextView)this.findViewById(R.id.confirmPassword);
	        
	       //validates user name and password
	        confirm = (Button)this.findViewById(R.id.confirm);
	        confirm.setOnClickListener(new View.OnClickListener() 
	        {
	            public void onClick(View v)
	            {
	            	//current password isn't correct
	            	if(!checkPassword(currentPassword.getText().toString()))
		        		Toast.makeText(getApplicationContext(), "That is not the correct Current Password", Toast.LENGTH_LONG).show();
	                //new password doesn't match confirm password
	            	else if(!newPassword.getText().toString().equals(confirmPassword.getText().toString()))
		        		Toast.makeText(getApplicationContext(), "New password does not match Confirmation password", Toast.LENGTH_LONG).show();
	            	//password doesn't meet standards (invalid)
	            	else if(!loginValidation(newPassword.getText().toString()))
		        		Toast.makeText(getApplicationContext(), "Invalid Password Change", Toast.LENGTH_LONG).show();
					else
					{
						 SharedPreferences.Editor editor = prefs.edit();
		                 editor.putString("password", newPassword.getText().toString());
		                 editor.commit();
			             finish();
					}
	            } 
	        });  
	        
	        //exits to login 1
	        x = (Button)this.findViewById(R.id.X);
	        x.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) 
	            {
	                finish();
	            }
	        });
		}
		
		//to check that the password entered matches the current password
		public boolean checkPassword(String currentPassword)
		{
	        return currentPassword.equals(password);
		}
		
	    //used to validate the user name and password
	    public boolean loginValidation(String newPassword)
	    {
	    	boolean digitExists = false;
			boolean uppercaseExists = false;
			boolean lowercaseExists = false;
			int length = newPassword.length();
			
			//Checks input's length
			if(length == 8 || length == 9 || length == 10)
			{
				//Checks if upper case and lower case characters and digits exists
				for (int i = ZERO; i < length; i++)
				{
					  char currentLetter = newPassword.charAt(i);
					  // check each rule in turn, with code like this:
					  if (Character.isLowerCase(currentLetter))
						  lowercaseExists = true;
					  else if(Character.isUpperCase(currentLetter)) 
						  uppercaseExists = true;
					  else if(Character.isDigit(currentLetter)) digitExists = true;
				}	
				//Displays an error message if no upper case and lower case characters and digits
				if(!lowercaseExists || !uppercaseExists || !digitExists) {
					Toast.makeText(this, "Password must contain at least one upper case letter, one lower case letter, and one number.", Toast.LENGTH_LONG).show();
					return false;
				}
				//if valid code
				else
					return true;
			} 
			else //if invalid length
			{
				Toast.makeText(this, "Password must be between 8-10 characters long", Toast.LENGTH_LONG).show();
				return false;
			}
		}
}
