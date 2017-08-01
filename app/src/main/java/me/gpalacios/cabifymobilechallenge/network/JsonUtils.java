package me.gpalacios.cabifymobilechallenge.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.gpalacios.cabifymobilechallenge.data.ProductParcelable;

class JsonUtils {

    static ProductParcelable[] getProductsArrayFromJson(String data) throws JSONException {

        JSONObject dataJson = new JSONObject(data);

        JSONArray resultsArray = dataJson.getJSONArray("products");

        ProductParcelable[] products = new ProductParcelable[resultsArray.length()];

        for(int i = 0; i<resultsArray.length(); i++){
            String code, name, price;
            code = resultsArray.getJSONObject(i).getString("code");
            name = resultsArray.getJSONObject(i).getString("name");
            price = Double.toString(resultsArray.getJSONObject(i).getDouble("price"));

            products[i] = new ProductParcelable(code, name, price);
        }

        return products;

    }
}
