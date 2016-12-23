package weatherapp.a23sokolov.com.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, List<Forecast>> forecastMap;
    private Map<Integer, Listener> listenersMap;

    private List<CallbackReport> callbackReports;
    private List<Call<WeatherReport>> calls;


    public interface Listener {
        public void updateForecast(List<Forecast> forecasts);

        public void updateFailed();
    }

    public WeatherReportManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://informer.gismeteo.ru/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        forecastMap = new HashMap<>();
        calls = new ArrayList<>();
        listenersMap = new HashMap<>();
        callbackReports = new ArrayList<>();

        GismeteoApi gismeteoApi = retrofit.create(GismeteoApi.class);
        calls.add(gismeteoApi.loadMoscowReport());
        calls.add(gismeteoApi.loadSaintPetersburgReport());
        calls.add(gismeteoApi.loadNovosibirskReport());

        for (int i = 0; i < calls.size(); i++) {
            callbackReports.add(new CallbackReport(i));
        }
    }

    public void doRequest(int requestPosition) {
        if (requestPosition < 0 || requestPosition > calls.size() - 1)
            return;

        Call<WeatherReport> call = calls.get(requestPosition);
        if (call.isExecuted())
            call = call.clone();

        call.enqueue(callbackReports.get(requestPosition));
    }

    public void setListener(final Listener listener, Integer position) {
        this.listenersMap.put(position, listener);
    }

    public void removeListener(Integer position) {
        this.listenersMap.remove(position);
    }

    private void updateWeatherReport(final List<Forecast> forecasts, int requestPosition) {
        if (requestPosition == -1)
            return;

        forecastMap.remove(requestPosition);
        forecastMap.put(requestPosition, forecasts);

        Listener appropriateListener = listenersMap.get(requestPosition);
        if (appropriateListener != null)
            appropriateListener.updateForecast(forecasts);
    }

    private void updateFailed(Integer requestPosition) {
        Listener appropriateListener = listenersMap.get(requestPosition);
        if (appropriateListener != null)
            appropriateListener.updateFailed();
    }

    public List<Forecast> getForecastList(Integer requestPosition) {
        return forecastMap.get(requestPosition);
    }

    private class CallbackReport implements Callback<WeatherReport> {
        Integer requestPosition;

        public CallbackReport(Integer requestPosition) {
            this.requestPosition = requestPosition;
        }

        @Override
        public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
            updateWeatherReport(response.body().getReport().getTown().getForecasts(), requestPosition);
        }

        @Override
        public void onFailure(Call<WeatherReport> call, Throwable t) {
            updateFailed(requestPosition);
        }
    }

    public void clear() {
        forecastMap.clear();
        for (Map.Entry<Integer, Listener> entry : listenersMap.entrySet()) {
            updateWeatherReport(new ArrayList<Forecast>(), entry.getKey());
        }
    }
}
