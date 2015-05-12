package msoma.abcnewsfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static android.net.ConnectivityManager.TYPE_WIFI;


public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if(isOnline()){
            Intent work = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(work);

        }
        else{
            Intent down = new Intent(getApplicationContext(),noInternet.class);
            startActivity(down);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
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

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(TYPE_WIFI);
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
