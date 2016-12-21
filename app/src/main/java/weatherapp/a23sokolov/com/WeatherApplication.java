package weatherapp.a23sokolov.com;

import android.app.Application;

import weatherapp.a23sokolov.com.manager.WeatherReportManager;

/**
 * Created by alexey on 21/12/16.
 */
public class WeatherApplication extends Application {

    private static WeatherApplication instance;

    private WeatherReportManager weatherReportManager;

    public WeatherApplication() {
        instance = this;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        weatherReportManager = new WeatherReportManager();
    }


    public static WeatherApplication getInstance() {
        return instance;
    }

    public WeatherReportManager getWeatherReportManager() {
        return weatherReportManager;
    }
}
