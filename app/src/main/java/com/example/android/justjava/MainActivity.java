package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public int quantity = 0;

    public void increment(View view)
    {
        if (quantity == 50) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 50 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity+=1;
        display(quantity);
    }


    public void decrement(View view)
    {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;


        }
        quantity-=1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void submitOrder(View view)
    {
       EditText namefield = (EditText) findViewById(R.id.plain_text_input);
        String name = namefield.getText().toString();

        CheckBox hasChecked = (CheckBox) findViewById(R.id.checkbox_cream);
        Boolean hasWhippedCream = hasChecked.isChecked();

        CheckBox hasChecked1 = (CheckBox) findViewById(R.id.checkbox_chocolate);
        Boolean hasChocolate = hasChecked1.isChecked();

       // Log.v("MAinActivity","Whipped Cream "+ hasWhippedCream);

        int price = calculatePrice(hasWhippedCream, hasChocolate );
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);


        //to set the intent on click to mail activity


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, "Your Order \n\n" + priceMessage );

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }



        // Intent for Location

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6,-122.3"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/

    }

    private int calculatePrice(boolean whippedCream, boolean chocolate)

    {
        int baseprice = 5;

        if(whippedCream)
        {
            baseprice += 1;
        }

        if(chocolate)
        {
            baseprice += 2;
        }

        return baseprice * quantity ;
    }

    private String createOrderSummary(String name, int price, boolean addCream, boolean addChoco)
    {
        String printMessage ="Name: " + name ;
        printMessage += "\nAdd WhippedCream ? " + addCream;
        printMessage += "\nAdd Chocolate ? " + addChoco;
        printMessage += "\nQuantity : " + quantity;
        printMessage += "\nTotal Cost : $ " + price;
        printMessage += "\nTHanK You!";

        return printMessage;
    }
}