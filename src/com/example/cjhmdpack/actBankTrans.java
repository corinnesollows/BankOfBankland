package com.example.cjhmdpack;

import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class actBankTrans extends Activity {
	final int ZERO = 0;
	final String EMPTYSTRING = "";
	RadioGroup radioGroup1;
	RadioButton deposit, withdraw, balance;
	Button submit, returnButton, more;
	TextView currentBalance, transactionAmount, newBalanceAmount;
	LinearLayout transactionAmountLayout,newBalanceAmountLayout,currentBalanceLayout;  
	double currentBalanceValue = ZERO;
	double transactionAmountValue = ZERO;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_transaction);
        
        //setting variables activity components to their corresponding IDs
        currentBalanceLayout = (LinearLayout)this.findViewById(R.id.currentBalanceLayout);
        transactionAmountLayout = (LinearLayout)this.findViewById(R.id.transactionAmountLayout);
        newBalanceAmountLayout = (LinearLayout)this.findViewById(R.id.newBalanceAmountLayout);
        
        radioGroup1 = (RadioGroup)this.findViewById(R.id.radioGroup1);
        withdraw = (RadioButton)this.findViewById(R.id.withdraw);
        deposit = (RadioButton)this.findViewById(R.id.deposit);
        balance = (RadioButton)this.findViewById(R.id.balance); 
        
        currentBalance = (TextView)this.findViewById(R.id.currentBalance);
        transactionAmount = (TextView)this.findViewById(R.id.transactionAmount);
        newBalanceAmount = (TextView)this.findViewById(R.id.newBalanceAmount);
        
        submit = (Button)this.findViewById(R.id.submit);
        returnButton = (Button)this.findViewById(R.id.returnButton);
        more = (Button)this.findViewById(R.id.more);
        
        disable();
    	
        //shares currentBalanceValue, transactionAmountValue, and 3 most recent transactions
        final SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);

        //generates a random current balance
        Random random = new Random();
        String randomBalance = Double.toString(random.nextInt(401 - 100) + 100);
        currentBalance.setText(prefs.getString("currentBalance", randomBalance));
        
        //filled in doubles with the values from text views
        if(!currentBalance.getText().toString().equals(EMPTYSTRING))
        	currentBalanceValue = Double.parseDouble(currentBalance.getText().toString());
        
        //Stores currentBalance in Shared Preferences
        prefs.edit().putString("currentBalance", Double.toString(currentBalanceValue)).commit();
        prefs.edit().putString("newBalanceAmount", Double.toString(currentBalanceValue)).commit();
        
        //return button returns to login 2
        returnButton.setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			finish();
    		}
        });   
       
        //more button returns to transaction initial look
        more.setOnClickListener(new View.OnClickListener() 
        {
        	public void onClick(View v) 
        	{   
        		disable();
        	}
        });      
       
        //enabled when transaction amount is entered, validates it and if its good sends to actGetBalance and displays new balance
        submit.setOnClickListener(new View.OnClickListener() 
        {
        	public void onClick(View v) 
        	{           
        		String transaction = prefs.getString("transaction", EMPTYSTRING);
        		//if radio button deposit is clicked
        		if (transaction.equals("D")||transaction.equals("W"))
        			transactionValueCheck();
        		//if balance is clicked
        		else if(transaction.equals("B"))
        			balance();
        	}
        });       
          
        //radio button options
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() 
        {
        	@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//if radio button deposit is clicked
                if (deposit.isChecked())
                { 
        			prefs.edit().putString("transaction", "D").commit();
                	enable();
                    currentBalance.setText(prefs.getString("currentBalance", EMPTYSTRING));
                }
                //if radio button bank transaction is clicked
                else if(withdraw.isChecked())
                { 
        			prefs.edit().putString("transaction", "W").commit();
                	enable();
                    currentBalance.setText(prefs.getString("currentBalance", EMPTYSTRING));
                }
                // if balance is clicked
                else if (balance.isChecked())
                {
            		prefs.edit().putString("transaction", "B").commit();
                	halfEnable();
                    currentBalance.setText(prefs.getString("currentBalance", EMPTYSTRING));
                }       
			}
        });    	    	
	} 
    
    //called to handle when deposit radio button is clicked
	public void transactionValueCheck()
	{        
		SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);

		//checks that user inputs data
		if(!transactionAmount.getText().toString().equals(EMPTYSTRING))
        {	
        	transactionAmountValue = Double.parseDouble(transactionAmount.getText().toString());
        	String transaction = prefs.getString("transaction", EMPTYSTRING);

        	//checks that the amount entered is less that 200% of current balance for deposit
	        if(transactionAmountValue >= (2 * currentBalanceValue) && transaction.equals("D"))
			{	
	        	//prompt user to retype
	        	Toast.makeText(getApplicationContext(), "Please enter a valid deposit amount (cannot be more than 200% of current balance) or click the Return button", Toast.LENGTH_LONG).show();
	        	transactionAmount.setText(EMPTYSTRING);
	        	transactionAmount.requestFocus();
			}
			//checks that the amount entered is less that 50% of current balance for withdraw
		    else if(transactionAmountValue >= (0.5 * currentBalanceValue) && transaction.equals("W"))
		    { 
		    	//prompt user to retype
		    	Toast.makeText(getApplicationContext(), "Please enter a valid withdraw amount (cannot be more than 50% of current balance) or click the Return button", Toast.LENGTH_LONG).show();
		    	transactionAmount.setText(EMPTYSTRING);
		    	transactionAmount.requestFocus();
		    }
		    else
		    {
	        	//saves transaction amount and goes to actGetBalance 
				prefs.edit().putString("transactionAmount", Double.toString(transactionAmountValue)).commit();
				Intent data = new Intent("net.learn2develop.actGetBalance");
				startActivity(data);
			}
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Please enter a transaction amount!", Toast.LENGTH_LONG).show();  
        	transactionAmount.requestFocus();
        }
	}
    
    //called to handle when balance radio button is clicked
    public void balance()
    {   
    	SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
    	transactionAmountValue = ZERO;
       	prefs.edit().putString("transactionAmount", Double.toString(transactionAmountValue)).commit();
       	Intent data = new Intent("net.learn2develop.actGetBalance");
       	startActivity(data);
    }
    
    public void disable()
    {
    	//setting visibilities
    	currentBalanceLayout.setVisibility(View.GONE);
    	transactionAmountLayout.setVisibility(View.GONE);
    	newBalanceAmountLayout.setVisibility(View.GONE);
    	submit.setVisibility(View.GONE);
    	more.setVisibility(View.GONE);
    }
    
    public void halfEnable()
    {
    	//setting visibilities
    	currentBalanceLayout.setVisibility(View.VISIBLE);
        currentBalance.setVisibility(View.GONE);
    	transactionAmountLayout.setVisibility(View.GONE);
    	newBalanceAmountLayout.setVisibility(View.GONE);
    	submit.setVisibility(View.VISIBLE);
    	more.setVisibility(View.VISIBLE);
    	
        //disables current balance text view also gives the disabled button black font
        currentBalance.setEnabled(false);
        currentBalance.setTextColor(Color.BLACK);
    }
    
    public void enable()
    {
    	//setting visibilities
    	currentBalanceLayout.setVisibility(View.VISIBLE);
    	transactionAmountLayout.setVisibility(View.VISIBLE);
    	newBalanceAmountLayout.setVisibility(View.VISIBLE);
        currentBalance.setVisibility(View.VISIBLE);
    	newBalanceAmount.setVisibility(View.GONE);
    	submit.setVisibility(View.VISIBLE);
    	more.setVisibility(View.VISIBLE);
    	transactionAmount.requestFocus();
    	
        //disables current balance, new balance amount text views also gives the disabled buttons black font
        currentBalance.setEnabled(false);
        currentBalance.setTextColor(Color.BLACK);
        newBalanceAmount.setEnabled(false);
        newBalanceAmount.setTextColor(Color.BLACK);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        //shares currentBalanceValue, transactionAmountValue, and 3 most recent transactions
        SharedPreferences prefs = this.getSharedPreferences("Assignment2", Context.MODE_PRIVATE);
        currentBalance.setVisibility(View.VISIBLE);
    	//get from sharedPreferences
        transactionAmount.setText(EMPTYSTRING);
    	newBalanceAmount.setVisibility(View.VISIBLE);
    	newBalanceAmount.setText(prefs.getString("newBalanceAmount", EMPTYSTRING));
    	prefs.edit().putString("currentBalance", prefs.getString("newBalanceAmount", EMPTYSTRING)).commit();
    	radioGroup1.clearCheck();
    }
}
