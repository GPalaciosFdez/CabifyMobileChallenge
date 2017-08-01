package me.gpalacios.cabifymobilechallenge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class CartProductsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";

    private static final int DATABASE_VERSION = 1;

    CartProductsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_PRODUCTS_TABLE =

                "CREATE TABLE " + CartProductsContract.CartProductsEntry.TABLE_NAME + " (" +

                        CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE + " TEXT PRIMARY KEY , " +
                        CartProductsContract.CartProductsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        CartProductsContract.CartProductsEntry.COLUMN_PRICE + " TEXT NOT NULL, " +
                        CartProductsContract.CartProductsEntry.COLUMN_QUANTITY + " INTEGER, " +

                        " UNIQUE (" + CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CartProductsContract.CartProductsEntry.TABLE_NAME);
        onCreate(db);
    }
}
