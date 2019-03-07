package examples.android.example.com.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import examples.android.example.com.bakingapp.Adapters.stepFragmentAdapter;
import examples.android.example.com.bakingapp.Constants;
import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;
import examples.android.example.com.bakingapp.RecipeData.Steps;
import examples.android.example.com.bakingapp.recipeDetailsActivity;
import examples.android.example.com.bakingapp.stepDetailsActivity;


public class stepShortDescriptionFragment extends Fragment implements stepFragmentAdapter.stepsFragmentListItemClickListener {


    @BindView(R.id.recipeIngredients)
    TextView recipeIngredients;

    @BindView(R.id.recipe_steps_shortDescription_fragment_recycler)
    RecyclerView fragmentRecycler;

    ArrayList<Steps> steps=new ArrayList<>();
    ArrayList<Ingredients> ingredients=new ArrayList<>();

    getDataFromHost fromHost;

    public interface getDataFromHost{
         void getAllData(int position, ArrayList<Steps> steps);
    }

    public stepShortDescriptionFragment(){

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            fromHost=(getDataFromHost) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.recip_details_fragment,container,false);
        ButterKnife.bind(this, rootView);


        recipeDetailsActivity activity = (recipeDetailsActivity) getActivity();
        steps = activity != null ? activity.getRecipeSteps() : null;

        ingredients= activity != null ? activity.getRecipeIngredients() : null;


        //for recycler
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentRecycler.setLayoutManager(linearLayoutManager);

        stepFragmentAdapter adapter= new stepFragmentAdapter(steps,this);
        fragmentRecycler.setAdapter(adapter);

        if (ingredients != null) {
            for(int i=0;i<ingredients.size();i++) {

                recipeIngredients.append(ingredients.get(i).getIngredient()+"\n");
            }
        }

        //test


        return rootView;
    }


    @Override
    public void onstepsFragmentListItemClick(int clickedItemIndex){

        boolean isPhone=getResources().getBoolean(R.bool.is_phone);

        //send the position and list of steps to next fragment host activity
        if(isPhone) {
            Intent current = new Intent(getActivity(), stepDetailsActivity.class);
            current.putExtra(Constants.clickedItem, clickedItemIndex);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.data, steps);
            current.putExtras(bundle);
            startActivity(current);
        }
        else {

            fromHost.getAllData(clickedItemIndex,steps);
        }
    }



}
