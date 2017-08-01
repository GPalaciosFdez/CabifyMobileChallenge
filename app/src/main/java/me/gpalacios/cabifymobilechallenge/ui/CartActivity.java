package me.gpalacios.cabifymobilechallenge.ui;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import me.gpalacios.cabifymobilechallenge.R;
import me.gpalacios.cabifymobilechallenge.data.CartProductsContract;

import static java.lang.String.*;

public class CartActivity extends AppCompatActivity {

    private CartListAdapter mCartListAdapter;
    private Cursor mCartProducts;
    private TextView mTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mTotalPrice = (TextView) findViewById(R.id.tv_total_price);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_cart);
        mRecyclerView.setHasFixedSize(true);

        mCartListAdapter = new CartListAdapter();
        mRecyclerView.setAdapter(mCartListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getProductsInCart();
        calculateTotal();
    }

    private void getProductsInCart(){
        mCartProducts = getContentResolver().query(CartProductsContract.CartProductsEntry.CONTENT_URI, null, null, null, null);
        mCartListAdapter.setmCartProducts(mCartProducts);
    }

    private void calculateTotal(){
        double totalPrice = 0.0;
        while(mCartProducts.moveToNext()){
            String code = mCartProducts.getString(mCartProducts.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE));
            int quantity = mCartProducts.getInt(mCartProducts.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_QUANTITY));
            double itemPrice = Double.parseDouble(mCartProducts.getString(mCartProducts.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_PRICE)));
            switch (code){
                case "VOUCHER":
                    if(quantity > 1){
                        quantity = quantity%2 + quantity/2;
                    }
                    break;
                case "TSHIRT":
                    if(quantity > 2){
                        itemPrice = 19.0;
                    }
            }
            totalPrice += itemPrice*quantity;
        }
        mTotalPrice.setText(String.format("Total: $%s", valueOf(totalPrice)));

    }

}
