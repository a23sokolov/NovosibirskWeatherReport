package weatherapp.a23sokolov.com.manager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import weatherapp.a23sokolov.com.model.WeatherReport;
import weatherapp.a23sokolov.com.model.elements.Forecast;

/**
 * Created by alexey on 21/12/16.
 */
public class WeatherReportManager {
    private List<Forecast> forecastList;
    private Listener listener;

    private CallbackReport callbackReport;
    private Call<WeatherReport> call;


    public interface Listener {
        public void updateForecast(List<Forecast> forecasts);

        public void updateFailed();
    }

    public WeatherReportManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://informer.gismeteo.ru/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        forecastList = new ArrayList<>();
        callbackReport = new CallbackReport();
        GismeteoApi gismeteoApi = retrofit.create(GismeteoApi.class);
        call = gismeteoApi.loadWeatherReport();
    }

    public void doRequest() {
        if (call.isExecuted())
            call = call.clone();

        call.enqueue(callbackReport);
    }

    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    private void updateWeatherReport(final List<Forecast> forecasts) {
        if (this.forecastList == null)
            this.forecastList = new ArrayList<>();
        this.forecastList.clear();
        this.forecastList.addAll(forecasts);

        listener.updateForecast(this.forecastList);
    }

    private void updateFailed() {
        listener.updateFailed();
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    private class CallbackReport implements Callback<WeatherReport> {

        @Override
        public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
            updateWeatherReport(response.body().getReport().getTown().getForecasts());
        }

        @Override
        public void onFailure(Call<WeatherReport> call, Throwable t) {
            updateFailed();
        }
    }
}
