package me.gpalacios.cabifymobilechallenge.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.gpalacios.cabifymobilechallenge.R;
import me.gpalacios.cabifymobilechallenge.data.CartProductsContract;
import me.gpalacios.cabifymobilechallenge.data.ProductParcelable;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = DetailActivity.class.getSimpleName();
    private ProductParcelable productInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mProductName = (TextView) findViewById(R.id.tv_product_name);
        TextView mProductPrice = (TextView) findViewById(R.id.tv_product_price);

        Intent intentIncoming = getIntent();

        if(intentIncoming != null && intentIncoming.hasExtra(Intent.EXTRA_TEXT)){
            productInfo = intentIncoming.getParcelableExtra(Intent.EXTRA_TEXT);

            mProductName.setText(productInfo.getName());
            mProductPrice.setText(String.format("$%s", productInfo.getPrice()));

        }

    }

    @SuppressWarnings("UnusedParameters")
    public void addToCart(View view) {
        int quantity = 0;
        Cursor cursor = getContentResolver().query(CartProductsContract.CartProductsEntry.buildUri(productInfo.getCode()), null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            quantity = cursor.getInt(cursor.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_QUANTITY)) + 1;
            cursor.close();
        }

        ContentValues values = new ContentValues();

        values.put(CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE, productInfo.getCode());
        values.put(CartProductsContract.CartProductsEntry.COLUMN_NAME, productInfo.getName());
        values.put(CartProductsContract.CartProductsEntry.COLUMN_PRICE, productInfo.getPrice());
        values.put(CartProductsContract.CartProductsEntry.COLUMN_QUANTITY, quantity);

        try {
            getContentResolver().delete(CartProductsContract.CartProductsEntry.buildUri(productInfo.getCode()), null, null);
            getContentResolver().insert(CartProductsContract.CartProductsEntry.CONTENT_URI, values);
        } catch (Exception e) {
            Log.e(TAG, "Error inserting", e);
        }
    }
}
