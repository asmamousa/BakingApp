package examples.android.example.com.bakingapp.NetworkUtilities;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import examples.android.example.com.bakingapp.Constants;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;
import examples.android.example.com.bakingapp.RecipeData.Recipes;
import examples.android.example.com.bakingapp.RecipeData.Steps;

final public class JsonUtils {


    private JsonUtils() {
    }


    public static List<Recipes> parseRecipeJson(String recipeJson) {

        String quantity=null;
        String measure=null;
        String ingredient=null;

        String stepId=null;
        String shortDescription=null;
        String description=null;
        String thumbnailURL=null;
        String videoURL=null;

        Recipes recipesObject = null;
        Ingredients ingredientsObject=null;
        Steps stepsObject=null;
        List<Recipes> recipes = new ArrayList<>();




        if (TextUtils.isEmpty(recipeJson)) {
            return null;
        }

        try {

            JSONArray baseJsonArray = new JSONArray(recipeJson);

            for(int i=0;i<baseJsonArray.length();i++){

                ArrayList<Steps> steps=new ArrayList<>();
                ArrayList<Ingredients> ingredients=new ArrayList<>();

                JSONObject resulti = baseJsonArray.getJSONObject(i);

                String id=resulti.optString("id");
                String name=resulti.optString("name");
                String servings=resulti.optString("servings");
                String image=resulti.optString("image");

                JSONArray ingredientsArray = resulti.getJSONArray("ingredients");
                JSONArray stepsArray = resulti.getJSONArray("steps");


                for (int j=0;j<ingredientsArray.length();j++){

                    JSONObject result2i = ingredientsArray.getJSONObject(j);

                    quantity=result2i.optString("quantity");
                    measure=result2i.optString("measure");
                    ingredient=result2i.optString("ingredient");

                    ingredientsObject =new Ingredients(quantity,measure,ingredient);
                    ingredients.add(ingredientsObject);

                }


                for (int k=0;k<stepsArray.length();k++){

                    JSONObject result3i = stepsArray.getJSONObject(k);

                    stepId=result3i.optString("id");
                    shortDescription=result3i.optString("shortDescription");
                    description=result3i.optString("description");
                    videoURL=result3i.optString("videoURL");
                    thumbnailURL=result3i.optString("thumbnailURL");

                    stepsObject=new Steps(stepId,shortDescription,description,videoURL,thumbnailURL);
                    steps.add(stepsObject);


                }



                recipesObject = new Recipes(id,name,servings,image,ingredients,steps);
                recipes.add(recipesObject);



            }
        } catch (JSONException e) {

            Log.e(Constants.JsonUtils, Constants.JsonErrorMsg, e);
        }

        return recipes;
    }
}
