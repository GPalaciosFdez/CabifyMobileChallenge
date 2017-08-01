package me.gpalacios.cabifymobilechallenge.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.gpalacios.cabifymobilechallenge.R;
import me.gpalacios.cabifymobilechallenge.data.CartProductsContract;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartProductViewHolder> {

    private Cursor mCartProducts;

    void setmCartProducts(Cursor cartProducts) {
        mCartProducts = cartProducts;
        notifyDataSetChanged();
    }

    @Override
    public CartProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_cart_list_item, parent, false);

        return new CartProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartProductViewHolder holder, int position) {
        mCartProducts.moveToPosition(position);
        String productName = mCartProducts.getString(mCartProducts.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_NAME));
        holder.mProductName.setText(productName);
        String productQuantity = mCartProducts.getString(mCartProducts.getColumnIndex(CartProductsContract.CartProductsEntry.COLUMN_QUANTITY));
        holder.mProductQuantity.setText(productQuantity);
    }

    @Override
    public int getItemCount() {
        return mCartProducts.getCount();
    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {

        final TextView mProductName;
        final TextView mProductQuantity;

        public CartProductViewHolder(View itemView) {
            super(itemView);
            mProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            mProductQuantity = (TextView) itemView.findViewById(R.id.tv_product_quantity);
        }

    }


}
