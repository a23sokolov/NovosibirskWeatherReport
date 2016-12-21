package weatherapp.a23sokolov.com.manager;

import retrofit2.Call;
import retrofit2.http.GET;
import weatherapp.a23sokolov.com.model.WeatherReport;

/**
 * Created by alexey on 19/12/16.
 */
public interface GismeteoApi {
    @GET("/xml/29634.xml")
    Call<WeatherReport> loadWeatherReport();
}
