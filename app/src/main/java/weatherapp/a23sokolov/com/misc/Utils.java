package weatherapp.a23sokolov.com.misc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import weatherapp.a23sokolov.com.WeatherApplication;
import weatherapp.a23sokolov.com.model.elements.Forecast;
import weatherapp.a23sokolov.com.weatherapp.R;

/**
 * Created by alexey on 21/12/16.
 */
public class Utils {

    public static String formatDateTimeByPattern(@Nullable final String pattern, @Nullable final Date date) {
        String formattedDate = "";
        if (!TextUtils.isEmpty(pattern) && date != null) {
            final SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            formattedDate = format.format(date);
        }
        return formattedDate;
    }

    public enum WindDirection {
        // don't change order
        NORTH, NORTH_EAST,
        EAST, SOUTH_EAST,
        SOUTH, SOUTH_WEST,
        WEST, NORTH_WEST;

        public String getLocalizedString() {
            final Context ctx = WeatherApplication.getInstance().getApplicationContext();
            return ctx.getResources().getStringArray(R.array.windDirections)[ordinal()];
        }

        public String getArrow() {
            final Context ctx = WeatherApplication.getInstance().getApplicationContext();
            return ctx.getResources().getStringArray(R.array.windDirectionArrows)[ordinal()];
        }
    }

    //тип осадков: 4 - дождь, 5 - ливень, 6,7 – снег, 8 - гроза, 9 - нет данных, 10 - без осадков
    //облачность по градациям:  0 - ясно, 1- малооблачно, 2 - облачно, 3 - пасмурно
    public static String getWeatherIcon(final Forecast forecast) {
        final Context ctx = WeatherApplication.getInstance().getApplicationContext();
        String icon = "";
        switch (forecast.getPhenomena().getPrecipitation()) {
            case 4:
                icon = ctx.getString(R.string.weather_rainy);
                break;
            case 5:
                icon = ctx.getString(R.string.weather_thunder);
                break;
            case 6:
            case 7:
                icon = ctx.getString(R.string.weather_snowy);
                break;
            case 8:
                icon = ctx.getString(R.string.weather_thunder);
                break;
            default:
                if (forecast.getCloudiness() <= 1) {
                    if (forecast.getHour() >= 7 && forecast.getHour() < 20)
                        icon = ctx.getString(R.string.weather_sunny);
                    else
                        icon = ctx.getString(R.string.weather_clear_night);
                } else {
                    icon = ctx.getString(R.string.weather_cloudy);
                }
        }
        return icon;
    }


}
