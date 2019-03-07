package examples.android.example.com.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Steps;

public class stepFragmentAdapter extends RecyclerView.Adapter<stepFragmentAdapter.stepsFragmentViewHolder> {


    private List<Steps> result;
    private Context context;
    private stepsFragmentListItemClickListener mListItemClickListener;

    public interface stepsFragmentListItemClickListener {

        void onstepsFragmentListItemClick(int clickedItemIndex);

    }

    public stepFragmentAdapter(List<Steps> steps, stepsFragmentListItemClickListener listener){

        if(steps!=null){

            result=steps;
        }
        mListItemClickListener=listener;

    }


    @Override
    public stepsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_fragment_recycler_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new stepsFragmentViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull stepsFragmentViewHolder holder, int position) {


        holder.populateUI(result,holder.getAdapterPosition());

    }

    @Override
    public int getItemCount() {

        if(result!=null){
            return result.size();
        }
        else{return 0;}


    }


    class stepsFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_shortDescription)
        TextView stepDescription;

        @Override
        public void onClick(View view) {

            if(mListItemClickListener!=null){
                int position=getAdapterPosition();
                mListItemClickListener.onstepsFragmentListItemClick(position);}
        }


        private stepsFragmentViewHolder (View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);


        }


        private void populateUI(List<Steps> result, int index) {
//
//            int imgRes=-1;
//            switch (index){
//                case 0: imgRes=R.drawable.img; break;
//                case 1: imgRes=R.drawable.img2; break;
//                case 2: imgRes=R.drawable.img3; break;
//                case 3: imgRes=R.drawable.img4; break;
//
//            }
//            Picasso.with(context)
//                    .load(imgRes)
//                    .error(R.drawable.no_image)
//                    .placeholder(R.drawable.processing)
//                    .into(imageView);
//
//            name.setText(result.get(index).getName());
           // for(int i=0;i<result.size();i++){
//
                stepDescription.setText(result.get(index).getShortDescription()+"\n");
//
//                stepDescription.append(result.get(i));
          // }
//
//


        }

    }
}
