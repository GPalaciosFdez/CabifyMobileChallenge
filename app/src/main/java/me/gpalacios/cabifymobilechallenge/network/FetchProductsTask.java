package me.gpalacios.cabifymobilechallenge.network;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;

import me.gpalacios.cabifymobilechallenge.data.ProductParcelable;
import me.gpalacios.cabifymobilechallenge.ui.MainActivity;

public class FetchProductsTask extends AsyncTask<Void, Void, ProductParcelable[]> {

    private final MainActivity mainActivity;

    public FetchProductsTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected ProductParcelable[] doInBackground(Void... params) {

        URL requestUrl = null;
        try {
            requestUrl = new URL("https://api.myjson.com/bins/4bwec");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            String productsJsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(requestUrl);
            return JsonUtils.getProductsArrayFromJson(productsJsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(ProductParcelable[] products) {
        if (products != null) {
            mainActivity.passDataToAdapter(products);
        }
    }


}
