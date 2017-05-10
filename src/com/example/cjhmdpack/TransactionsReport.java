package com.example.cjhmdpack;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

//is displayed for 10 seconds before closing project
public class TransactionsReport extends Activity {

	//Declares screen components
	TextView user;
	TextView currentBalance1, currentBalance2, currentBalance3;
	TextView typeTransaction1, typeTransaction2, typeTransaction3;
	TextView transactionAmount1, transactionAmount2, transactionAmount3;
	TextView newBalance1, newBalance2, newBalance3;
	final String EMPTYSTRING = "";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_report);
        
        SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
       
        //Initializes TextView
        user = (TextView)this.findViewById(R.id.user);

		currentBalance1 = (TextView)this.findViewById(R.id.currentBalance1);
		currentBalance2 = (TextView)this.findViewById(R.id.currentBalance2);
		currentBalance3 = (TextView)this.findViewById(R.id.currentBalance3);

		typeTransaction1 = (TextView)this.findViewById(R.id.typeTransaction1);
		typeTransaction2 = (TextView)this.findViewById(R.id.typeTransaction2);
		typeTransaction3 = (TextView)this.findViewById(R.id.typeTransaction3);

		transactionAmount1 = (TextView)this.findViewById(R.id.transactionAmount1);
		transactionAmount2 = (TextView)this.findViewById(R.id.transactionAmount2);
		transactionAmount3 = (TextView)this.findViewById(R.id.transactionAmount3);

		newBalance1  = (TextView)this.findViewById(R.id.newBalance1);
		newBalance2  = (TextView)this.findViewById(R.id.newBalance2);
		newBalance3  = (TextView)this.findViewById(R.id.newBalance3);
		
		currentBalance1.setText(prefs.getString("currentBalance1", EMPTYSTRING));
		currentBalance2.setText(prefs.getString("currentBalance2", EMPTYSTRING));
		currentBalance3.setText(prefs.getString("currentBalance3", EMPTYSTRING));

		typeTransaction1.setText(prefs.getString("typeTransaction1", EMPTYSTRING));
		typeTransaction2.setText(prefs.getString("typeTransaction2", EMPTYSTRING));
		typeTransaction3.setText(prefs.getString("typeTransaction3", EMPTYSTRING));

		transactionAmount1.setText(prefs.getString("transactionAmount1", EMPTYSTRING));
		transactionAmount2.setText(prefs.getString("transactionAmount2", EMPTYSTRING));
		transactionAmount3.setText(prefs.getString("transactionAmount3", EMPTYSTRING));

		newBalance1.setText(prefs.getString("newBalance1", EMPTYSTRING));
		newBalance2.setText(prefs.getString("newBalance2", EMPTYSTRING));
		newBalance3.setText(prefs.getString("newBalance3", EMPTYSTRING));
		
        user.setText(prefs.getString("user", "UNAs1H15"));
            
        timer();
	}
		
	//timer set to 10 seconds before closing program
	public void timer()
	{
        new java.util.Timer().schedule(
        new java.util.TimerTask()
        {
            @Override
            public void run() 
            {  
            	Intent startMain = new Intent(Intent.ACTION_MAIN); 
            	startMain.addCategory(Intent.CATEGORY_HOME); 
            	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
            	startActivity(startMain); 
            	finish();
            }
        }, 10000); 
    
	}
}
