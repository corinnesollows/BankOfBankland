package com.example.cjhmdpack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class actGetBalance extends Activity
{
	
	final double ZERO = 0;
	final String EMPTYSTRING = "";
	double newBalanceAmount = ZERO;
	double transactionAmount = ZERO;
	double balance = ZERO;
	String transaction = EMPTYSTRING;
	String roundedTransactionAmount = EMPTYSTRING;
	String roundedNewBalanceAmount = EMPTYSTRING;
	String roundedBalance = EMPTYSTRING;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        SharedPreferences prefs = getSharedPreferences("Assignment2", MODE_PRIVATE);
    
        transaction = prefs.getString("transaction", EMPTYSTRING);
    	transactionAmount = Double.parseDouble(prefs.getString("transactionAmount", EMPTYSTRING));
    	balance = Double.parseDouble(prefs.getString("currentBalance", EMPTYSTRING));

       //if the transaction amount is negative it is withdrawn, if positive deposited
        if(transaction.equals("W"))
        	newBalanceAmount = balance - transactionAmount;
        else if(transaction.equals("D"))
        	newBalanceAmount = balance + transactionAmount;
        else
        	newBalanceAmount = balance;
        
        roundedTransactionAmount = String.format("%3.2f", transactionAmount);
        roundedNewBalanceAmount = String.format("%3.2f", newBalanceAmount);
        roundedBalance = String.format("%3.2f", balance);
        
        //sets newBalanceAmount to the new balance
        prefs.edit().putString("newBalanceAmount", Double.toString(newBalanceAmount)).commit();
        prefs.edit().putString("newBalanceAmount",(String.format("%3.2f", newBalanceAmount))).commit();
        transactionsReportStorage();
        
        finish(); 
     } 
    
	
	public void transactionsReportStorage()
	{
        SharedPreferences prefs = this.getSharedPreferences("Assignment2", Context.MODE_PRIVATE);
        
	    //Sets shared prefs from three most recent transactions
        prefs.edit().putString("transactionAmount3", prefs.getString("transactionAmount2", EMPTYSTRING)).commit();
        prefs.edit().putString("typeTransaction3", prefs.getString("typeTransaction2", EMPTYSTRING)).commit();
        prefs.edit().putString("currentBalance3", prefs.getString("currentBalance2", EMPTYSTRING)).commit();
        prefs.edit().putString("newBalance3", prefs.getString("newBalance2", EMPTYSTRING)).commit();
        
        prefs.edit().putString("transactionAmount2", prefs.getString("transactionAmount1", EMPTYSTRING)).commit();
        prefs.edit().putString("typeTransaction2", prefs.getString("typeTransaction1", EMPTYSTRING)).commit();
        prefs.edit().putString("currentBalance2", prefs.getString("currentBalance1",EMPTYSTRING)).commit();
        prefs.edit().putString("newBalance2", prefs.getString("newBalance1", EMPTYSTRING)).commit();
        
        prefs.edit().putString("transactionAmount1", roundedTransactionAmount).commit();
        prefs.edit().putString("typeTransaction1", transaction).commit();
        prefs.edit().putString("currentBalance1", roundedBalance).commit();
        prefs.edit().putString("newBalance1", roundedNewBalanceAmount).commit();
	}
}
