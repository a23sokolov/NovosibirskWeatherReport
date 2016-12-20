package weatherapp.a23sokolov.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import weatherapp.a23sokolov.com.model.WeatherReport;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import weatherapp.a23sokolov.com.model.elements.Forecast;
import weatherapp.a23sokolov.com.model.elements.Report;
import weatherapp.a23sokolov.com.weatherapp.R;

public class MainActivity extends AppCompatActivity implements Callback<WeatherReport>, View.OnClickListener {

    Call<WeatherReport> call;

    private RecyclerView mRecyclerView;
    private SimpleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get_weather_btn).setOnClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SimpleRecyclerAdapter(new ArrayList<Forecast>());
        mRecyclerView.setAdapter(mAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://informer.gismeteo.ru/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        GismeteoApi gismeteoApi = retrofit.create(GismeteoApi.class);

        call = gismeteoApi.loadWeatherReport();
    }

    @Override
    protected void onStop() {
        call.cancel();
//        call = call.clone();
//        call can be used only one time may use clone;
        super.onStop();
    }

    public void updateRecyclerView(List<Forecast> forecasts) {
        mAdapter.resetElements(forecasts);
    }

    @Override
    public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
        Log.v("$$$$","response = " + response.body());
        updateRecyclerView(response.body().getReport().getTown().getForecasts());
    }

    @Override
    public void onFailure(Call<WeatherReport> call, Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.v("$$$$","MainActivity.onFailure message = " + t.getLocalizedMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_weather_btn:
                if (call.isExecuted())
                    call = call.clone();

                call.enqueue(this);
                break;
        }
    }

    public class SimpleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Forecast> forecasts;

        public SimpleRecyclerAdapter(final List<Forecast> forecastList) {
            forecasts = forecastList;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_report_section, parent, false);
            return new ReportHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ReportHolder reportHolder = (ReportHolder) holder;
            final Forecast forecast = forecasts.get(position);
            reportHolder.date.setText(forecast.getDate());
        }

        @Override
        public int getItemCount() {
            return forecasts.size();
        }

        public void resetElements(final List<Forecast> forecasts) {
            if (forecasts != null) {
                this.forecasts.clear();
                this.forecasts.addAll(forecasts);
            }
            notifyDataSetChanged();
        }

        class ReportHolder extends RecyclerView.ViewHolder {
            private TextView date;
            private TextView condition;
            private TextView wind;
            private TextView pressure;
            private TextView humidity;
//            private ImageView icon;

            public ReportHolder(View itemView) {
                super(itemView);
                date = (TextView) itemView.findViewById(R.id.report_date);
                condition = (TextView) itemView.findViewById(R.id.report_condition);
                wind = (TextView) itemView.findViewById(R.id.report_wind);
                pressure = (TextView) itemView.findViewById(R.id.report_pressure);
                humidity = (TextView) itemView.findViewById(R.id.report_humidity);
            }
        }
    }
}
