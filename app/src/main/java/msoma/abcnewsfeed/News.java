package msoma.abcnewsfeed;

import android.os.Parcel;
import android.os.Parcelable;

import msoma.abcnewsfeed.extras.SerialBitmap;

/**
 * Created by Mohanish on 24/04/15.
 */
    public class News implements Parcelable {

    private String tittle;
    private String description;
    private String newsLink;
    private String thumbNails;



    public News() {

    }

    public News(String tittle, String description, String newsLink, String thumbNails) {
        this.tittle = tittle;
        this.description = description;
        this.newsLink = newsLink;
        this.thumbNails = thumbNails;

    }

    // Static method to create Parcelable object (required)
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public News(Parcel in) {
        this.tittle = in.readString();
        this.description = in.readString();
        this.newsLink = in.readString();
        this.thumbNails = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tittle);
        dest.writeString(description);
        dest.writeString(newsLink);
        dest.writeString(thumbNails);
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getThumbNails() {
        return thumbNails;
    }

    public void setThumbNails(String thumbNails) {
        this.thumbNails = thumbNails;
    }
}

