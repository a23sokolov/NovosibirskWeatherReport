package weatherapp.a23sokolov.com.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import weatherapp.a23sokolov.com.weatherapp.R;

public class ReportActivity extends AppCompatActivity {

    public static String EXTRA_FORECAST_POSITION = "forecastPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, String.valueOf(getIntent().getIntExtra(EXTRA_FORECAST_POSITION, 0)), Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_report);
    }
}
