package examples.android.example.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import examples.android.example.com.bakingapp.RecipeData.Steps;
import examples.android.example.com.bakingapp.fragments.stepDetailsFragment;


public class stepDetailsActivity extends AppCompatActivity {


    ArrayList<Steps> ListOfSteps =new ArrayList<>();
    int clickedStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_fragment_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ListOfSteps = bundle.getParcelableArrayList(Constants.data);
        }
        clickedStepIndex=getIntent().getIntExtra(Constants.clickedItem,0);

        if(savedInstanceState ==null) {
            stepDetailsFragment mediaFragment=new stepDetailsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_fragment_with_media_container,mediaFragment)
                    .commit();
        }

    }

    public ArrayList<Steps> getStep(){
        return ListOfSteps;
    }
    public int getClickedStepIndex(){return clickedStepIndex;}



}
