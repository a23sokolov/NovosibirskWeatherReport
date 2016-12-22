package weatherapp.a23sokolov.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import weatherapp.a23sokolov.com.WeatherApplication;
import weatherapp.a23sokolov.com.manager.WeatherReportManager;
import weatherapp.a23sokolov.com.model.elements.Forecast;
import weatherapp.a23sokolov.com.weatherapp.R;

public class MainActivity extends AppCompatActivity implements WeatherReportManager.Listener, View.OnClickListener, SimpleRecyclerAdapter.OnViewHolderClickListener {

    private SimpleRecyclerAdapter mAdapter;

    private WeatherApplication app = WeatherApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get_weather_btn).setOnClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SimpleRecyclerAdapter(new ArrayList<Forecast>(), this);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        WeatherReportManager weatherReportManager = app.getWeatherReportManager();

        updateForecast(weatherReportManager.getForecastList());
        weatherReportManager.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_weather_btn:
                app.getWeatherReportManager().doRequest();
                break;
        }
    }

    @Override
    public void updateForecast(List<Forecast> forecasts) {
        mAdapter.resetElements(forecasts);
//        Toast.makeText(this, this.getString(R.string.request_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFailed() {
        Toast.makeText(this, this.getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(ReportActivity.EXTRA_FORECAST_POSITION, position);
        startActivity(intent);
    }
}
