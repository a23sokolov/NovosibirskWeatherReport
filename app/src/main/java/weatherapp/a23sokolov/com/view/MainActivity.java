package weatherapp.a23sokolov.com.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import weatherapp.a23sokolov.com.model.elements.Report;
import weatherapp.a23sokolov.com.weatherapp.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.city_1).setOnClickListener(this);
        findViewById(R.id.city_2).setOnClickListener(this);
        findViewById(R.id.city_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int cityPosition = 0;
        switch (view.getId()) {
            case R.id.city_1:
                cityPosition = 0;
                break;
            case R.id.city_2:
                cityPosition = 1;
                break;
            case R.id.city_3:
                cityPosition = 2;
                break;
        }

        Intent intent = new Intent(this, ReportsActivity.class);
        intent.putExtra(ReportsActivity.EXTRA_CITY_POSITION, cityPosition);
        startActivity(intent);
    }
}
