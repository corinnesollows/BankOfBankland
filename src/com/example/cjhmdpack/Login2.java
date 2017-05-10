package com.example.cjhmdpack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Login2 extends Activity{
    Button exit, x;
    RadioGroup radioGroup1;
    RadioButton changePassword, bankTransaction;
         
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);    

       //exit displays transactions
        exit = (Button)this.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {   
                Intent data = new Intent("net.learn2develop.TransactionsReport");
                startActivity(data);
                finish();
            }
        });
        
       //setting variables
        radioGroup1 = (RadioGroup)this.findViewById(R.id.radioGroup1);
        changePassword = (RadioButton)this.findViewById(R.id.withdraw);
        bankTransaction = (RadioButton)this.findViewById(R.id.deposit);
        
        //exits to login 1
        x = (Button)this.findViewById(R.id.X);
        x.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	finish();
            }
        });
        
        //radio button options
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() 
        {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
								
                if (changePassword.isChecked()) { 
                    Intent data = new Intent("net.learn2develop.actChangePassword");
                    startActivity(data);
                }
                else if(bankTransaction.isChecked()){ 
                    Intent data = new Intent("net.learn2develop.actBankTrans");
                    startActivity(data);
                }
			}
        });      
    }
    @Override
    public void onResume()
    {
        super.onResume();
        radioGroup1.clearCheck();
    }
}
