package examples.android.example.com.bakingapp.RecipeData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipes implements Parcelable {


    private String id;
    private String name;
    private String servings;
    private String image;

    private ArrayList<Ingredients> ingredients=new ArrayList<>();
    private ArrayList<Steps> steps=new ArrayList<>();


    private Recipes (Parcel in) {


        id=in.readString();
        name=in.readString();
        servings =in.readString();
        image =in.readString();

        in.readList(ingredients,Ingredients.class.getClassLoader());
        in.readList(steps,Steps.class.getClassLoader());

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);

        dest.writeList(ingredients);
        dest.writeList(steps);

    }


    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>()
    {
        public Recipes createFromParcel(Parcel in)
        {
            return new Recipes(in);
        }
        public Recipes[] newArray(int size)
        {
            return new Recipes[size];
        }
    };


    public Recipes (String id, String name, String servings, String image,ArrayList<Ingredients> ingredients,ArrayList<Steps> steps){

        this.id=id;
        this.name=name;
        this.servings = servings;
        this.image =image;
        this.ingredients=ingredients;
        this.steps=steps;

    }


    public String getName() { return name; }
    public String getServings()
    {
        return servings;
    }
    public String getImage() {
        return image;
    }
    public String getId() { return id;}
    public ArrayList<Ingredients> getIngredients(){return ingredients;}
    public ArrayList<Steps> getSteps(){return steps;}
}
