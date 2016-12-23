package weatherapp.a23sokolov.com.view;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import weatherapp.a23sokolov.com.WeatherApplication;
import weatherapp.a23sokolov.com.weatherapp.R;

/**
 * Created by alexey on 24/12/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_cache:
                WeatherApplication.getInstance().getWeatherReportManager().clear();
                Toast.makeText(this, getString(R.string.toast_cache_cleared), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
