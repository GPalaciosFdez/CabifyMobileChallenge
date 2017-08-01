package me.gpalacios.cabifymobilechallenge.data;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class CartProductsProvider extends android.content.ContentProvider {

    private static final int CODE_PRODUCTS = 100;
    private static final int CODE_PRODUCTS_WITH_CODE = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CartProductsDBHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CartProductsContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, CartProductsContract.PATH_PRODUCTS, CODE_PRODUCTS);

        matcher.addURI(authority, CartProductsContract.PATH_PRODUCTS + "/*", CODE_PRODUCTS_WITH_CODE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CartProductsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_PRODUCTS_WITH_CODE: {

                String id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{id};

                cursor = mOpenHelper.getReadableDatabase().query(
                        CartProductsContract.CartProductsEntry.TABLE_NAME,
                        projection,
                        CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_PRODUCTS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        CartProductsContract.CartProductsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long insert = mOpenHelper.getWritableDatabase().insert(
                CartProductsContract.CartProductsEntry.TABLE_NAME,
                null,
                values);

        if (insert > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_PRODUCTS_WITH_CODE:
                String id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{id};
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        CartProductsContract.CartProductsEntry.TABLE_NAME,
                        CartProductsContract.CartProductsEntry.COLUMN_PRODUCT_CODE + " = ? ",
                        selectionArguments);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
