package me.gpalacios.cabifymobilechallenge.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.gpalacios.cabifymobilechallenge.R;
import me.gpalacios.cabifymobilechallenge.data.ProductParcelable;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder> {

    private final ProductsListAdapterOnClickHandler mClickHandler;
    private ProductParcelable[] mProductsData;

    ProductsListAdapter(ProductsListAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_list_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        String productName = mProductsData[position].getName();
        holder.mProductName.setText(productName);
        String productPrice = mProductsData[position].getPrice();
        holder.mProductPrice.setText(String.format("$%s", productPrice));
    }

    @Override
    public int getItemCount() {
        if(mProductsData == null){
            return 0;
        }
        return mProductsData.length;
    }

    void setmProductsData(ProductParcelable[] productsData) {
        mProductsData = productsData;
        notifyDataSetChanged();
    }

    interface ProductsListAdapterOnClickHandler{
        void onClick(ProductParcelable productInfo);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView mProductName;
        final TextView mProductPrice;

        ProductViewHolder(View productView){
            super(productView);
            mProductName = (TextView) productView.findViewById(R.id.tv_product_name);
            mProductPrice = (TextView) productView.findViewById(R.id.tv_product_price);
            productView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ProductParcelable productInfo = mProductsData[adapterPosition];
            mClickHandler.onClick(productInfo);
        }
    }

}
