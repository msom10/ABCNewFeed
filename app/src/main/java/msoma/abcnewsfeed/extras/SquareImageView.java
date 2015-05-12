package msoma.abcnewsfeed.extras;

/**
 * Created by Mohanish on 25/04/15.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created a custom ImageView to help retain a square ratio.
 * @link http://stackoverflow.com/questions/15261088/gridview-with-two-columns-and-auto-resized-images
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}