package msoma.abcnewsfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import msoma.abcnewsfeed.extras.SerialBitmap;


public class MainActivity extends Activity {

    public static final String JSON_DOWNLOAD_LOCATION = "http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://www.abc.net.au/news/feed/51120/rss.xml&num=-1";
    //public static final String JSON_DOWNLOAD_LOCATION = "http://date.jsontest.com";

    private ListView newsListView;
    private ArrayList<News>  newsArray ;
    private ArrayList<ImageResult> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            new DownloadParseJSONTask().execute();

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Grab the selected Reminder
                News tappedReminder = (News) newsListView.getAdapter().getItem(i);
                String newsUrl = tappedReminder.getNewsLink();
                Intent intent = new Intent(getApplicationContext(), NewsDetail.class);
                intent.putExtra("tappedNews", newsUrl);
                startActivity(intent);

            }
        });


    }

    private void updateNewsCount() {
        int totalNews = newsArray.size();
        TextView reminderCountText = (TextView)findViewById(R.id.newsCount);

        if(totalNews == 0){
            reminderCountText.setText("No news at ABC currently!");
        }
        else{
            reminderCountText.setText("Total News: " + totalNews);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadParseJSONTask extends AsyncTask<Void, Void, Void> {

        JSONObject json;
        ProgressBar progressBar;


        @Override
        protected Void doInBackground(Void... params) {
            // Request our JSON data
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(JSON_DOWNLOAD_LOCATION);

            try {
                // Execute the GET request and get input
                HttpResponse response = client.execute(request);
                System.out.println("*********1******"+response.getStatusLine());
                InputStream input = response.getEntity().getContent();
                System.out.println("*********2******"+input);

                // Parse input into a String, line by line
                String content = "";
                StringWriter writer = new StringWriter();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                while ((content = reader.readLine()) != null) {
                    sb.append(content);
                }
                // Set our JSON object by passing the StringBuilder result
                System.out.println(sb.toString());
                json = new JSONObject(sb.toString());

                try {

                    newsArray = new ArrayList<News>();
                    images = new ArrayList<ImageResult>();

                    JSONObject responseData = (JSONObject) json.get("responseData");
                    JSONObject feed = (JSONObject) responseData.get("feed");
                    JSONArray entries = (JSONArray) feed.get("entries");

                    String tempTitle = null;
                    String tempDesc = null;
                    String tempLink = null;
                    String tempThumbNail = null;


                   // for(int i =0;i<entries.length();i++){
                    for(int i =0;i<9;i++){
                        JSONObject temp = (JSONObject) entries.get(i);

                        //Retrieve Title
                        try{
                            tempTitle = (String) temp.get("title");
                            System.out.println("$$$title$$$$"+i+tempTitle);
                        }
                        catch (Exception e){System.out.println("***Error in Title***"+i);}

                        //Retrieve Description
                        try{
                            tempDesc = (String) temp.get("contentSnippet");
                            System.out.println("$$$Description$$$$"+i+tempDesc);
                        }
                        catch (Exception e){System.out.println("***Error in description***"+i);}

                        //Retrieve Url of News
                        try{
                            tempLink = (String) temp.get("link");
                            System.out.println("$$$URL$$$$"+i+tempLink);
                        }
                        catch (Exception e){System.out.println("***Error in Link***"+i);}

                        //Retrieve Thumbnail Link
                        try{
                            JSONArray mediaGroups = (JSONArray) temp.get("mediaGroups");
                            JSONObject tempJSON = (JSONObject) mediaGroups.get(0);
                            JSONArray contents = (JSONArray) tempJSON.get("contents");
                            JSONObject contentZero = (JSONObject) contents.get(0);
                            JSONArray thumbnails = (JSONArray) contentZero.get("thumbnails");
                            JSONObject thumbnailJSON = (JSONObject) thumbnails.get(0);
                            tempThumbNail =(String)thumbnailJSON.get("url");
                            System.out.println("$$$thumnailURL$$$$"+i+tempThumbNail);

                            // Setup network request for image and decode into Bitmap
                            request = new HttpGet(tempThumbNail);
                            response = client.execute(request);
                            InputStream is = response.getEntity().getContent();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            System.out.println(bitmap);
                            ImageResult tempImage = new ImageResult(new SerialBitmap(bitmap));
                            images.add(tempImage);
                        }
                        catch (Exception e)
                            {
                            System.out.println("***Error in Thumbnail***"+i);
                            images.add(null);
                        }


                        News tempNews = new News(tempTitle,tempDesc,tempLink,tempThumbNail);
                        //update news array
                        newsArray.add(tempNews);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }



            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("*********33Exception******");
            }






            return null;
        }

        // Before we retrieve the JSON, let's activate a progress bar
        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setIndeterminate(true);

            // Get text views and hide for the moment
            newsListView = (ListView) findViewById(R.id.newslistView);

            newsListView.setVisibility(View.GONE);

        }

        // Execute once our background job is complete
        @Override
        protected void onPostExecute(Void aVoid) {
            NewsAdapter adapter = new NewsAdapter(getApplicationContext(), newsArray,images);

            // Associate adapter with ListView
            newsListView.setAdapter(adapter);

            // Update monster count
            updateNewsCount();

            // Hide the progress bar
            progressBar.setVisibility(View.GONE);

            //make list view visible
            newsListView.setVisibility(View.VISIBLE);


        }


    //async task closing brace
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetwork != null && wifiNetwork.isConnected() )
        { System.out.println(wifiNetwork.toString());
             System.out.println(wifiNetwork.isConnected()+"wifi is connected");
            return true;}

        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(mobileNetwork !=null && mobileNetwork.isConnected())
        { return true;}

        return false;
    }
}
