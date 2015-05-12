package msoma.abcnewsfeed;

import java.io.Serializable;

import msoma.abcnewsfeed.extras.SerialBitmap;

/**
 * Created by Mohanish on 25/04/15.
 */
public class ImageResult implements Serializable {

    private SerialBitmap media;

    public ImageResult(SerialBitmap media) {
        this.media = media;
    }

    public ImageResult() {
    }

    public SerialBitmap getMedia() {
        return media;
    }

    public void setMedia(SerialBitmap media) {
        this.media = media;
    }
}
