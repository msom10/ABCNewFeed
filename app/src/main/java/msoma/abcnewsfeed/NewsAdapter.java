package msoma.abcnewsfeed;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import msoma.abcnewsfeed.extras.SquareImageView;

/**
 * Created by Mohanish on 24/04/15.
 */
public class NewsAdapter  extends BaseAdapter{

    private Context context;
    private ArrayList<News> news;
    private ArrayList<ImageResult> newsImage;

    public NewsAdapter(Context context, ArrayList<News> news,ArrayList<ImageResult> newsImage) {
        this.context = context;
        this.news = news;
        this.newsImage = newsImage;
        System.out.println("Adapter set correctly");
    }


    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the view has been created for the row. If not, lets inflate it
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_news, null); // List layout here
        }

        // Grab the TextViews in our custom layout
        TextView newsTitle = (TextView)convertView.findViewById(R.id.newsTitle);
        TextView newsDescription = (TextView)convertView.findViewById(R.id.newsDesc);

       ImageResult tempImage = newsImage.get(position);
        // Find the image view and assign the image
        SquareImageView imageView = (SquareImageView)convertView.findViewById(R.id.imageView);

        if(tempImage != null) {
            imageView.setImageBitmap(tempImage.getMedia().getBitmap());
        }

        newsTitle.setTextColor(Color.BLACK);
        newsDescription.setTextColor(Color.BLACK);

        //Assign values to the TextViews using the news object
        newsTitle.setText(news.get(position).getTittle());
        newsDescription.setText(news.get(position).getDescription());
        //reminderDueDate.setText("Due Date: " + reminders.get(position).getDueDate());

        return convertView;

    }
}
