package examples.android.example.com.bakingapp.RecipeData;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;

    private Ingredients(Parcel in){

        quantity=in.readString();
        measure=in.readString();
        ingredient =in.readString();

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);

    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>()
    {
        public Ingredients createFromParcel(Parcel in)
        {
            return new Ingredients(in);
        }
        public Ingredients[] newArray(int size)
        {
            return new Ingredients[size];
        }
    };


    public Ingredients (String quantity, String measure, String ingredient){

        this.quantity=quantity;
        this.measure=measure;
        this.ingredient = ingredient;

    }

    public String getQuantity() { return quantity; }
    public String getMeasure()
    {
        return measure;
    }
    public String getIngredient() {
        return ingredient;
    }
}
