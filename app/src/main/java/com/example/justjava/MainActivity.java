/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        EditText userText = (EditText) findViewById(R.id.name);
        String name = userText.getText().toString().trim();
        String orderSummary = createOrderSummary(name, quantity, calculatePrice());
        displayMessage(orderSummary);
        //String[] address = new String[1];
        //address = {"pydon@outlook.com"};

        //send email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pydon@outlook.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderFrom) + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        displayQuantity(++quantity);
    }

    public void decrement(View view) {
        if (quantity >= 2) {
            displayQuantity(--quantity);
        }
    }

    public String createOrderSummary(String name, int quantity, double price) {

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        return getString(R.string.summaryName) + name + "\n" +
                getString(R.string.summaryCream) + hasWhippedCream + "\n" +
                getString(R.string.summaryChocolate) + hasChocolate + "\n" +
                getString(R.string.summaryQuantity) + quantity + "\n" +
                getString(R.string.summaryTotal) + price + "\n" +
                getString(R.string.summaryGreetings);
    }

    private double calculatePrice() {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        double price = 5;

        if (whippedCreamCheckbox.isChecked()) {
            double whippedCreamToppingPrice = 0.5;
            price += whippedCreamToppingPrice;
        }
        if (chocolateCheckbox.isChecked()) {
            double chocolateToppingPrice = 0.75;
            price += chocolateToppingPrice;
        }

        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}

