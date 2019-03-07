package examples.android.example.com.bakingapp.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;



import java.io.Serializable;
import java.util.ArrayList;

import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Ingredients;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidgetPrivider extends AppWidgetProvider {
    public static ArrayList<Ingredients>mIngrediant;

    public NewAppWidgetPrivider() {
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId[],ArrayList<Ingredients> ingredients) {
        mIngrediant = ingredients;
        for (int WidgetId : appWidgetId) {
            Intent intent = new Intent(context, listViewsService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setRemoteAdapter(R.id.list_view_widget, intent);
            ComponentName componentName = new ComponentName(context, NewAppWidgetPrivider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(WidgetId, R.id.list_view_widget);
            appWidgetManager.updateAppWidget(componentName, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

