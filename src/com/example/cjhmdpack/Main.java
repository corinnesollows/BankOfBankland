package com.example.cjhmdpack;

//CHECK ALL INENTS SENDING PASSWORDS

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class Main extends Activity
{
    TextView username, password;
    Button clear, ok, X;
    final int ZERO = 0;
    int invalidCounter = ZERO;
    String defaultName;
    String defaultPassword;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        
        defaultPassword = prefs.getString("password", "PWAs1H15");
        defaultName =  prefs.getString("user", "UNAs1H15");
        
        username = (TextView)this.findViewById(R.id.username);
        password = (TextView)this.findViewById(R.id.password);
        	
        //validates user name and password
        ok = (Button)this.findViewById(R.id.okay);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                            
                if(!loginValidation(username.getText().toString(), defaultName) || !loginValidation(password.getText().toString(),defaultPassword))
                    invalidCounter++;
                else{
                    Intent data = new Intent("net.learn2develop.Login2");                     
                    startActivity(data);
                }
                
                if(invalidCounter == 3)
                {
                    Toast.makeText(getApplicationContext(), "Reached maximum number of login tries.", Toast.LENGTH_LONG).show();
                    new java.util.Timer().schedule(
                        new java.util.TimerTask()
                        {
                            @Override
                            public void run() 
                            {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        }, 5000);
                }
            }
        });
        
        //clears user name and password boxes
        clear = (Button)this.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });
        
        //exits program
        X = (Button)this.findViewById(R.id.X);
        X.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    //used to validate the user name and password
    public boolean loginValidation(String input, String defaultInput)
    {
        boolean digitExists = false;
        boolean uppercaseExists = false;
        boolean lowercaseExists = false;
        int length = input.length();
        
        //Checks input's length
        if(length == 8 || length == 9 || length == 10)
        {
            //Checks if upper case and lower case characters and digits exists
            for (int i = ZERO; i < length; i++) {
                  char currentLetter = input.charAt(i);
                  // check each rule in turn, with code like this:
                  if (Character.isLowerCase(currentLetter))
                      lowercaseExists = true;
                  else if(Character.isUpperCase(currentLetter))
                      uppercaseExists = true;
                  else if(Character.isDigit(currentLetter))
                      digitExists = true;
            }   
            //Displays an error message if no upper case and lower case characters and digits
            if(!lowercaseExists || !uppercaseExists || !digitExists) {
                Toast.makeText(getApplicationContext(), "ERROR: Invalid input", Toast.LENGTH_SHORT).show();
                return false;
            }
            //if valid code
            else {
                if(input.equals(defaultInput))
                    return true;
                else {        
                    Toast.makeText(getApplicationContext(), "Incorrect Login.", Toast.LENGTH_SHORT).show();
                    return false;    
                }
            }
        } 
        else //if invalid length
        {
            Toast.makeText(getApplicationContext(), "ERROR: Invalid length. " + length + " characters", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    
    
    public void onResume()
    {
    	super.onResume();
    	SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
        defaultPassword = prefs.getString("password", "PWAs1H15");
        defaultName =  prefs.getString("user", "UNAs1H15");
    }
}
    
