package examples.android.example.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;
import examples.android.example.com.bakingapp.RecipeData.Recipes;
import examples.android.example.com.bakingapp.RecipeData.Steps;
import examples.android.example.com.bakingapp.fragments.stepDetailsFragment;
import examples.android.example.com.bakingapp.fragments.stepShortDescriptionFragment;

public class recipeDetailsActivity extends AppCompatActivity implements stepShortDescriptionFragment.getDataFromHost
 {

    public ArrayList<Steps> RecipeSteps;
    public ArrayList<Ingredients> RecipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        Bundle bundle = getIntent().getExtras();
        Recipes clickedRecipe= null;
        if (bundle != null) {
            clickedRecipe = bundle.getParcelable(Constants.data);        }

        RecipeSteps= clickedRecipe != null ? clickedRecipe.getSteps() : null;
        RecipeIngredients= clickedRecipe != null ? clickedRecipe.getIngredients() : null;


       //working with the fragment
        stepShortDescriptionFragment stepsFragment=new stepShortDescriptionFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.steps_fragment_container,stepsFragment)
                .commit();



    }



    @Override
    public void getAllData(int position, ArrayList<Steps> steps){

        stepDetailsFragment mediaFragment=new stepDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList(Constants.listOfSteps,steps);
        bundle.putInt(Constants.clickedItem,position);
        mediaFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.steps_fragment_with_media_container,mediaFragment)
                .commit();
    }


    public ArrayList<Steps> getRecipeSteps(){
        return RecipeSteps;
    }
    public ArrayList<Ingredients> getRecipeIngredients(){
        return RecipeIngredients;
    }


    //tst



 }
