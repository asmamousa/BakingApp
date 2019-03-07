package examples.android.example.com.bakingapp.RecipeData;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Steps implements Parcelable {

    private String stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private Steps(Parcel in){

        stepId=in.readString();
        shortDescription=in.readString();
        description =in.readString();
        videoURL=in.readString();
        thumbnailURL =in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);

    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>()
    {
        public Steps createFromParcel(Parcel in)
        {
            return new Steps(in);
        }
        public Steps[] newArray(int size)
        {
            return new Steps[size];
        }
    };

    public Steps (String stepId, String shortDescription, String description,String videoURL,String thumbnailURL){

        this.stepId=stepId;
        this.shortDescription=shortDescription;
        this.description = description;
        this.videoURL=videoURL;
        this.thumbnailURL=thumbnailURL;

    }


    public String getStepId() { return stepId; }
    public String getShortDescription()
    {
        return shortDescription;
    }
    public String getDescription() {
        return description;
    }
    public String  getVideoURL(){return videoURL;}
    public String getThumbnailURL(){return thumbnailURL;}
}
