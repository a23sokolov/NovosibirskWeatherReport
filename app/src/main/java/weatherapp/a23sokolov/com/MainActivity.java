package weatherapp.a23sokolov.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import weatherapp.a23sokolov.com.model.WeatherReport;
import weatherapp.a23sokolov.com.weatherapp.R;

public class MainActivity extends AppCompatActivity implements Callback<WeatherReport>, View.OnClickListener {

    final String xml = "<MMWEATHER>\n" +
            "<REPORT type=\"frc3\">\n" +
            "<TOWN index=\"29634\" sname=\"%CD%EE%E2%EE%F1%E8%E1%E8%F0%F1%EA\" latitude=\"55\" longitude=\"82\">\n" +
            "<FORECAST day=\"19\" month=\"12\" year=\"2016\" hour=\"16\" tod=\"2\" predict=\"0\" weekday=\"2\">\n" +
            "<PHENOMENA cloudiness=\"3\" precipitation=\"10\" rpower=\"0\" spower=\"0\"/>\n" +
            "<PRESSURE max=\"751\" min=\"749\"/>\n" +
            "<TEMPERATURE max=\"-16\" min=\"-14\"/>\n" +
            "<WIND min=\"5\" max=\"7\" direction=\"4\"/>\n" +
            "<RELWET max=\"76\" min=\"74\"/>\n" +
            "<HEAT min=\"-22\" max=\"-20\"/>\n" +
            "</FORECAST>\n" +
            "<FORECAST day=\"19\" month=\"12\" year=\"2016\" hour=\"22\" tod=\"3\" predict=\"0\" weekday=\"2\">\n" +
            "<PHENOMENA cloudiness=\"3\" precipitation=\"10\" rpower=\"0\" spower=\"0\"/>\n" +
            "<PRESSURE max=\"750\" min=\"748\"/>\n" +
            "<TEMPERATURE max=\"-14\" min=\"-12\"/>\n" +
            "<WIND min=\"6\" max=\"8\" direction=\"4\"/>\n" +
            "<RELWET max=\"78\" min=\"76\"/>\n" +
            "<HEAT min=\"-20\" max=\"-18\"/>\n" +
            "</FORECAST>\n" +
            "<FORECAST day=\"20\" month=\"12\" year=\"2016\" hour=\"04\" tod=\"0\" predict=\"0\" weekday=\"3\">\n" +
            "<PHENOMENA cloudiness=\"3\" precipitation=\"10\" rpower=\"0\" spower=\"0\"/>\n" +
            "<PRESSURE max=\"751\" min=\"749\"/>\n" +
            "<TEMPERATURE max=\"-13\" min=\"-11\"/>\n" +
            "<WIND min=\"5\" max=\"7\" direction=\"4\"/>\n" +
            "<RELWET max=\"77\" min=\"75\"/>\n" +
            "<HEAT min=\"-19\" max=\"-17\"/>\n" +
            "</FORECAST>\n" +
            "<FORECAST day=\"20\" month=\"12\" year=\"2016\" hour=\"10\" tod=\"1\" predict=\"0\" weekday=\"3\">\n" +
            "<PHENOMENA cloudiness=\"3\" precipitation=\"10\" rpower=\"0\" spower=\"0\"/>\n" +
            "<PRESSURE max=\"753\" min=\"751\"/>\n" +
            "<TEMPERATURE max=\"-13\" min=\"-11\"/>\n" +
            "<WIND min=\"5\" max=\"7\" direction=\"4\"/>\n" +
            "<RELWET max=\"78\" min=\"76\"/>\n" +
            "<HEAT min=\"-19\" max=\"-17\"/>\n" +
            "</FORECAST>\n" +
            "</TOWN>\n" +
            "</REPORT>\n" +
            "</MMWEATHER>";

    Call<WeatherReport> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get_weather_btn).setOnClickListener(this);

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

    @Override
    public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
        Log.v("$$$$","response = " + response.body());
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
}
