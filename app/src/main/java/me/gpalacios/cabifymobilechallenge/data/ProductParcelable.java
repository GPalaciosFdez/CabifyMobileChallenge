package me.gpalacios.cabifymobilechallenge.data;

import android.os.Parcel;
import android.os.Parcelable;


public class ProductParcelable implements Parcelable{

    private final String code;
    private final String name;
    private final String price;

    public static final Creator<ProductParcelable> CREATOR = new Creator<ProductParcelable>() {
        @Override
        public ProductParcelable createFromParcel(Parcel in) {
            return new ProductParcelable(in);
        }

        @Override
        public ProductParcelable[] newArray(int size) {
            return new ProductParcelable[size];
        }
    };

    public ProductParcelable(String code, String name, String price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    private ProductParcelable(Parcel in) {
        code = in.readString();
        name = in.readString();
        price = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(price);
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
