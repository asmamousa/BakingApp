package examples.android.example.com.bakingapp.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Recipes;


public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.BakingViewHolder> {

    private Context context;
    private List<Recipes> result;
    private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {

        void onListItemClick(int clickedItemIndex);

    }

    public BakingAdapter(List<Recipes> details, ListItemClickListener listener){

        if(details!=null){

            result=details;
        }
        mListItemClickListener=listener;

    }


    @Override
    public BakingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutIdForListItem = R.layout.recycler_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new BakingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BakingViewHolder holder, int position) {


        holder.populateUI(result,holder.getAdapterPosition());

    }

    @Override
    public int getItemCount() {

        if(result!=null){
            return result.size();
        }
        else{return 0;}


    }


    class BakingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.recipeImage)
        ImageView imageView;

        @Override
        public void onClick(View view) {

            if(mListItemClickListener!=null){
                int position=getAdapterPosition();
                mListItemClickListener.onListItemClick(position);}
        }

        private BakingViewHolder (View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);


        }


        private void populateUI(List<Recipes> result, int index) {

            int imgRes=-1;
            switch (index){
                case 0: imgRes=R.drawable.img; break;
                case 1: imgRes=R.drawable.img2; break;
                case 2: imgRes=R.drawable.img3; break;
                case 3: imgRes=R.drawable.img4; break;

            }
            Picasso.with(context)
                    .load(imgRes)
                    .error(R.drawable.no_image)
                    .placeholder(R.drawable.processing)
                    .into(imageView);

            name.setText(result.get(index).getName());
        }

    }



}


