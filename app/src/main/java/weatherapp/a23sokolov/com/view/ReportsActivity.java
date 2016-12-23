package weatherapp.a23sokolov.com.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class ReportsActivity extends BaseActivity implements WeatherReportManager.Listener, View.OnClickListener, SimpleRecyclerAdapter.OnViewHolderClickListener {

    private SimpleRecyclerAdapter mAdapter;

    private WeatherApplication app = WeatherApplication.getInstance();
    int cityPosition;
    public static String EXTRA_CITY_POSITION = "cityPosition";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        findViewById(R.id.get_weather_btn).setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            cityPosition = intent.getIntExtra(EXTRA_CITY_POSITION, 0);
        }

        setTitle(getTitleInner());
        progressDialog = new ProgressDialog(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SimpleRecyclerAdapter(new ArrayList<Forecast>(), this);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        WeatherReportManager weatherReportManager = app.getWeatherReportManager();

        mAdapter.resetElements(weatherReportManager.getForecastList(cityPosition));
        weatherReportManager.setListener(this, cityPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_weather_btn:
                app.getWeatherReportManager().doRequest(cityPosition);
                progressDialog.show();
                break;
        }
    }

    @Override
    protected void onStop() {
        app.getWeatherReportManager().removeListener(cityPosition);
        super.onStop();
    }

    @Override
    public void updateForecast(List<Forecast> forecasts) {
        mAdapter.resetElements(forecasts);
        progressDialog.dismiss();
        Toast.makeText(this, this.getString(R.string.request_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFailed() {
        progressDialog.dismiss();
        Toast.makeText(this, this.getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, String.format(this.getString(R.string.item_clicked), position), Toast.LENGTH_SHORT).show();
    }

    private String getTitleInner() {
        String title = String.valueOf(getTitle());
        switch (cityPosition) {
            case 0:
                title = getString(R.string.city_1);
                break;
            case 1:
                title = getString(R.string.city_2);
                break;
            case 2:
                title = getString(R.string.city_3);
                break;
        }
        return title;
    }
}
