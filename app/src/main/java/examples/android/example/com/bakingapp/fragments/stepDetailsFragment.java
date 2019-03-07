package examples.android.example.com.bakingapp.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import examples.android.example.com.bakingapp.Constants;
import examples.android.example.com.bakingapp.R;
import examples.android.example.com.bakingapp.RecipeData.Steps;
import examples.android.example.com.bakingapp.stepDetailsActivity;

public class stepDetailsFragment extends Fragment {

    @BindView(R.id.testtest)
    TextView textView;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mediaPlayer;

    @BindView(R.id.previous)
    Button prev;

    @BindView(R.id.next)
    Button next;

    private SimpleExoPlayer mExoPlayer;
    long mResumePosition;
    boolean playBackState;
    ArrayList<Steps> stepsArrayList;
    int clicked;

    public stepDetailsFragment(){

    }


    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        boolean isPhone=getResources().getBoolean(R.bool.is_phone);

        View rootView=inflater.inflate(R.layout.step_details_contains_media_fragmemt,container,false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState !=null){
            mResumePosition=savedInstanceState.getLong("STATE_RESUME_POSITION");
           // playBackState=savedInstanceState.getInt("PLAYBACK_STATE");

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                prev.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                textView.setVisibility(View.INVISIBLE);
                RelativeLayout.LayoutParams newLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                mediaPlayer.setLayoutParams(newLayoutParam);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }



            }


            if(isPhone) {
                stepDetailsActivity activity = (stepDetailsActivity) getActivity();
                stepsArrayList = activity != null ? activity.getStep() : null;
                clicked = activity != null ? activity.getClickedStepIndex() : 0;
            }

            else{
                Bundle fromHostBundle= getArguments();
                if (fromHostBundle != null) {
                    stepsArrayList=fromHostBundle.getParcelableArrayList(Constants.listOfSteps);
                    clicked=fromHostBundle.getInt(Constants.clickedItem);
                    prev.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                }

            }


        if (stepsArrayList != null) {
            textView.setText(stepsArrayList.get(clicked).getDescription());
        }

        mediaPlayer.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.processing));

        final Uri currentVideo;
        if (stepsArrayList != null) {
            currentVideo = Uri.parse(stepsArrayList.get(clicked).getVideoURL());

            if(!stepsArrayList.get(clicked).getVideoURL().isEmpty()) {


                initializePlayer(currentVideo,mResumePosition);
            }
            else
            {

                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                mediaPlayer.setPlayer(mExoPlayer);
                mediaPlayer.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.no_video));

            }

            if(clicked==stepsArrayList.size()-1){ next.setVisibility(View.GONE);}
            if(clicked==0){ prev.setVisibility(View.GONE);}
        }



        prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent preIntent =new Intent(getActivity(),stepDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.data, stepsArrayList);
                preIntent.putExtras(bundle);
                preIntent.putExtra(Constants.clickedItem,clicked-1);
                startActivity(preIntent);

            }
        });


        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    Intent nextIntent =new Intent(getActivity(),stepDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constants.data, stepsArrayList);
                    nextIntent.putExtras(bundle);
                    nextIntent.putExtra(Constants.clickedItem,clicked+1);
                    startActivity(nextIntent);

            }
        });

        return rootView;

    }

    private void initializePlayer(Uri mediaUri,long position) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

            mExoPlayer.seekTo(position);
            mediaPlayer.setPlayer(mExoPlayer);


            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putLong("STATE_RESUME_POSITION", mResumePosition);
        //outState.putInt("PLAYBACK_STATE",mediaPlayer.getPlayer().getPlaybackState());
        if(mediaPlayer.getPlayer().getPlaybackState() == mediaPlayer.getPlayer().STATE_READY){
            outState.putBoolean("PLAYBACK_STATE",true);
        }
        else{
            outState.putBoolean("PLAYBACK_STATE",false);
        }

    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        releasePlayer();
//    }

    @Override
    public void onPause() {
        super.onPause();
        mResumePosition= mediaPlayer.getPlayer().getCurrentPosition();
        playBackState=mediaPlayer.getPlayer().getPlayWhenReady();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }



    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onResume() {

        super.onResume();

        if ((Util.SDK_INT <= 23 || mediaPlayer.getPlayer() == null))
        initializePlayer(Uri.parse(stepsArrayList.get(clicked).getVideoURL()),mResumePosition);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(stepsArrayList.get(clicked).getVideoURL()),mResumePosition);
        }
    }


}
