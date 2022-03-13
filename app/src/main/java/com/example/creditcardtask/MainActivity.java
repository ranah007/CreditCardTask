package com.example.creditcardtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.creditcardtask.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText firstName,secondName,zipCode,cardNum,expdate,cvv;
    private Spinner spinner;
    Button trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName=findViewById(R.id.et_first_name);
        secondName=findViewById(R.id.et_second_name);
        zipCode=findViewById(R.id.et_zipcode);
        spinner=findViewById(R.id.spinner_country);
        cvv=findViewById(R.id.et_cvv);
        cardNum=findViewById(R.id.et_credit_card);
        expdate=findViewById(R.id.et_expirydate);
        trans=findViewById(R.id.trasaction);
        String creditCard=cardNum.getText().toString();
        boolean luhnValidation=isLuhnVelidate(creditCard);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            inputfieldValidity();
                if (luhnValidation)
                {
                    CheckValidityOfCreditCard();
                }
                else {
                    Toast.makeText(MainActivity.this,"Luhn Validation Failed, Card number info invalid",Toast.LENGTH_LONG).show();
                }
            }
        });

     //Coustom spinner for country Picker
        ArrayList<Countries>countryList=new ArrayList<>();
        countryList.add(new Countries("Pakistan",R.drawable.pakistanflag));
        countryList.add(new Countries("Brazil",R.drawable.brazilianflag));
        countryList.add(new Countries("Bangladesh",R.drawable.bangladeshflag));
        countryList.add(new Countries("Afghanistan",R.drawable.afghanflag));
        countryList.add(new Countries("China",R.drawable.chineseflag));
        countryList.add(new Countries("Belgiam",R.drawable.belgianflag));
        countryList.add(new Countries("Canada",R.drawable.canadianflag));
        countryList.add(new Countries("England",R.drawable.englishflag));
        countryList.add(new Countries("Australia",R.drawable.australianflag));
        countryList.add(new Countries("France",R.drawable.frenchflag));
        countryList.add(new Countries("Germany",R.drawable.germanflag));
        countryList.add(new Countries("Greek",R.drawable.greekflag));
        countryList.add(new Countries("India",R.drawable.indianflag));
        countryList.add(new Countries("Indonesia",R.drawable.indonesianflag));
        countryList.add(new Countries("Iran",R.drawable.iranianflag));
        countryList.add(new Countries("Iraq",R.drawable.iraqiflag));
        countryList.add(new Countries("Italy",R.drawable.italianflag));

        CountriesAdopter countriesAdopter=new CountriesAdopter(this,countryList);
        if (spinner!=null){
            spinner.setAdapter(countriesAdopter);
            spinner.setOnItemSelectedListener(this);
        }
        //check Expiry Date
   expdate.addTextChangedListener(new TextWatcher() {
       int IntL =0;
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           IntL = expdate.getText().toString().length();
       }
       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
       }
       @Override
       public void afterTextChanged(Editable s) {
           int ilength = s.length();
           Log.i("TAG", "================ilength=================== " + ilength);
           Log.i("TAG", "===================sssss================ " + s);
           Log.i("TAG", "====================intl=============== " + IntL);

           if ((IntL < ilength) && (ilength == 2)) {
               s.append("/");
           }
       }
   });
    }

    private void inputfieldValidity() {
        String fname=firstName.getText().toString();
        if (TextUtils.isEmpty(fname)){
            firstName.setError("Enter your  Name.");
        }
        String sname=secondName.getText().toString();
        if (TextUtils.isEmpty(sname)){
            secondName.setError("Enter your  Name.");
        }
        String zip=zipCode.getText().toString();
        if (TextUtils.isEmpty(zip)){
            zipCode.setError("Enter Zip Code.");
        }
        String cCard=firstName.getText().toString();
        if (TextUtils.isEmpty(cCard)){
            cardNum.setError("Enter your  Creditcard.");
        }
        String expiryDate=expdate.getText().toString();
        if (TextUtils.isEmpty(expiryDate)){
            expdate.setError("Enter your  Creditcard expiry date.");
        }
        String cvvCode=cvv.getText().toString();
        if (TextUtils.isEmpty(cvvCode)){
            cvv.setError("Enter your  cvv code.");
        }
    }

    public static boolean isLuhnVelidate(String creditCard){
        int[] ints = new int[creditCard.length()];
        for (int i = 0; i < creditCard.length(); i++) {
            ints[i] = Integer.parseInt(creditCard.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if(sum%10==0)
        {
            return true;
        }
        return false;
    }

    private void CheckValidityOfCreditCard() {
        //check Credit Card
        String creditCard = cardNum.getText().toString();
        int cardlenght = cardNum.getText().toString().length();
        Log.i("TAG", "================1=================== " + cardlenght);

        Long checkDigit=Long.parseLong(creditCard);
        Log.i("TAG", "================2=================== " + checkDigit);
        while (checkDigit>=10){
            checkDigit /= 10;
        }
        Log.i("TAG", "================3=================== " + checkDigit);
        int cvvnumber=cvv.getText().toString().length();
        if(cvvnumber==4 )
        {
        if(checkDigit==3 && cardlenght==15)
        {
            Toast.makeText(this,"you have american express card",Toast.LENGTH_LONG).show();

        }
            Toast.makeText(this,"either cvv or card number info invalid",Toast.LENGTH_LONG).show();

        }
        else if(cvvnumber==3)
        {
            if (cardlenght==16)
            {
                if (checkDigit==4){
                    Toast.makeText(this,"You have Visa Card",Toast.LENGTH_LONG).show();

                }
                else if (checkDigit==5){
                    Toast.makeText(this,"You have master Card",Toast.LENGTH_LONG).show();
                }
                else if (checkDigit==6){
                    Toast.makeText(this,"you have Discover card",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,"Card number info invalid",Toast.LENGTH_LONG).show();
                }
            }
            else if(cardlenght==13)
            {
                if(checkDigit==4){

                    Toast.makeText(this,"You have Visa Card",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this,"Card number info invalid",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Countries item=(Countries) parent.getSelectedItem();
        Toast.makeText(this,item.getCountryName(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}