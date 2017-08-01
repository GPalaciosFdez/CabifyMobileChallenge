package me.gpalacios.cabifymobilechallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.gpalacios.cabifymobilechallenge.R;
import me.gpalacios.cabifymobilechallenge.data.ProductParcelable;
import me.gpalacios.cabifymobilechallenge.network.FetchProductsTask;
import me.gpalacios.cabifymobilechallenge.ui.ProductsListAdapter;

public class MainActivity extends AppCompatActivity implements ProductsListAdapter.ProductsListAdapterOnClickHandler{

    private ProductsListAdapter mProductsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_products);
        mRecyclerView.setHasFixedSize(true);

        mProductsListAdapter = new ProductsListAdapter(this);
        mRecyclerView.setAdapter(mProductsListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getProducts();
    }

    private void getProducts(){
        new FetchProductsTask(this).execute();
    }

    public void passDataToAdapter(ProductParcelable[] products){
        mProductsListAdapter.setmProductsData(products);
    }

    @Override
    public void onClick(ProductParcelable productInfo) {
        Intent intentToDetailActivity = new Intent(this, DetailActivity.class);
        intentToDetailActivity.putExtra(Intent.EXTRA_TEXT, productInfo);
        startActivity(intentToDetailActivity);
    }

    @SuppressWarnings("UnusedParameters")
    public void toCart(View view) {
        Intent intentToCartActivity = new Intent(this, CartActivity.class);
        startActivity(intentToCartActivity);
    }
}
