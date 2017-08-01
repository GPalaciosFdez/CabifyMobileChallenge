package me.gpalacios.cabifymobilechallenge.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class CartProductsContract {

    static final String CONTENT_AUTHORITY = "me.gpalacios.cabifymobilechallenge";
    static final String PATH_PRODUCTS = "products";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class CartProductsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCTS)
                .build();
        public static final String COLUMN_PRODUCT_CODE = "code";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        static final String TABLE_NAME = "products";

        public static Uri buildUri(String... paths) {
            Uri.Builder builder = CONTENT_URI.buildUpon();
            for (String path : paths) {
                builder.appendPath(path);
            }
            return builder.build();
        }

    }
}
