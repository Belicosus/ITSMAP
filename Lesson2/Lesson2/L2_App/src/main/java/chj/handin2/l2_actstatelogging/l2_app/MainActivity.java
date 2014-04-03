package chj.handin2.l2_actstatelogging.l2_app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;


public class MainActivity extends Activity {

    private int toastDuration = Toast.LENGTH_SHORT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(Constants.LogTag, "onStart State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onStart", toastDuration);
        t.show();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.e(Constants.LogTag, "onRestart State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onRestart", toastDuration);
        t.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(Constants.LogTag, "onResume State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onResume", toastDuration);
        t.show();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e(Constants.LogTag, "onPause State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onPause", toastDuration);
        t.show();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e(Constants.LogTag, "onStop State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onStop", toastDuration);
        t.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(Constants.LogTag, "onDestroy State logged");
        Toast t = Toast.makeText(getApplicationContext(), "onDestroy", toastDuration);
        t.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
