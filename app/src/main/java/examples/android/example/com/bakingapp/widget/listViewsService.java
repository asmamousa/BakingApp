package examples.android.example.com.bakingapp.widget;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.Serializable;
import java.util.ArrayList;

import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;

public class listViewsService extends RemoteViewsService implements Serializable
{

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewsFactory(this.getApplicationContext());
    }
}


class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory,Serializable {
    Context mContext;
    private ArrayList<Ingredients> mIngradiants;

    public ListViewsFactory(Context applicationCntext) {
        mContext = applicationCntext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mIngradiants = NewAppWidgetPrivider.mIngrediant;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngradiants == null)
            return 0;
        return mIngradiants.size();

    }
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_text_view_widget_layout);
        views.setTextViewText(R.id.text_view_recipe_widget,mIngradiants.get(position).getIngredient());
        System.out.println("test");
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
