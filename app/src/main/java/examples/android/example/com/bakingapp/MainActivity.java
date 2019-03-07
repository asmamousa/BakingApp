package examples.android.example.com.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import examples.android.example.com.bakingapp.Adapters.BakingAdapter;
import examples.android.example.com.bakingapp.NetworkUtilities.JsonUtils;
import examples.android.example.com.bakingapp.NetworkUtilities.NetworkUtils;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;
import examples.android.example.com.bakingapp.RecipeData.Recipes;
import examples.android.example.com.bakingapp.widget.WidgetUpdateService;

public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener {


    private static final String LOG_TAG=Constants.error;
    List<Recipes> JSONResult=new ArrayList<>();
    Context context=this;
    BakingAdapter adapter;

    public static final String BUNDLE = "bundle";


    //test
    int test;


    @BindView(R.id.recycler)
    RecyclerView Recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        URL u = NetworkUtils.buildUrl();
        try {
            JSONResult = new RecipesTask().execute(u).get();

        } catch (ExecutionException e) {

            Log.e(LOG_TAG, e.getMessage(), e);

        } catch (InterruptedException e) {

            Log.e(LOG_TAG, e.getMessage(), e);

        }

        adapter = new BakingAdapter(JSONResult,this);

        boolean isPhone=getResources().getBoolean(R.bool.is_phone);

        if(isPhone) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            Recycler.setLayoutManager(linearLayoutManager);
            Recycler.setAdapter(adapter);
        }
        else{
            GridLayoutManager gridlayout = new GridLayoutManager(this, 2);
            Recycler.setLayoutManager(gridlayout);
            Recycler.setHasFixedSize(true);
            Recycler.setAdapter(adapter);
        }


        //test
//        startWidgetService();

    }

    @SuppressLint("StaticFieldLeak")
    private class RecipesTask extends AsyncTask<URL, Void, List<Recipes>> {

        protected List<Recipes> doInBackground(URL... urls) {

            URL u = urls[0];

            String RecipeResults = null;

            try {
                RecipeResults = NetworkUtils.getResponseFromHttpUrl(u);
                JSONResult= JsonUtils.parseRecipeJson(RecipeResults);



            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return JSONResult;

        }


        protected void onPostExecute(List<Recipes> result) {

            if(result==null || result.size()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
                builder.setMessage(R.string.NoRecipes);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
            }
            super.onPostExecute(result);
        }
    }






    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent=new Intent(MainActivity.this,recipeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.data, JSONResult.get(clickedItemIndex));
        intent.putExtras(bundle);
        startActivity(intent);


        test=clickedItemIndex;

        startWidgetService();

        }


void startWidgetService()
{
    if(JSONResult!=null ){
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        // bundle.putSerializable("Ingradiant", JSONResult.get(0).getIngredients());
        bundle.putParcelableArrayList("Ingradiant", JSONResult.get(test).getIngredients());
        i.putExtra(MainActivity.BUNDLE, bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);
    }

}

}

