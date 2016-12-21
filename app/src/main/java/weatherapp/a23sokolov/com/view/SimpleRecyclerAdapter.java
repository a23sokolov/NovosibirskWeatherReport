package weatherapp.a23sokolov.com.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weatherapp.a23sokolov.com.model.elements.Forecast;
import weatherapp.a23sokolov.com.weatherapp.R;

/**
 * Created by alexey on 21/12/16.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Forecast> forecasts;
    private Context ctx;

    public SimpleRecyclerAdapter(final List<Forecast> forecastList, final Context context) {
        forecasts = forecastList;
        ctx = context;
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
        String resultStr = forecast.getDate();
        final String[] condition = ctx.getResources().getStringArray(R.array.condition);
        reportHolder.date.setText(resultStr);
        reportHolder.temperature.setText(String.format(ctx.getString(R.string.weather_celsius), forecast.getTemperature().getMax()));
        reportHolder.condition.setText(condition[forecast.getCloudiness()]);
        reportHolder.wind.setText(String.format(ctx.getString(R.string.wind_list_report), forecast.getWindSpeed()));
        reportHolder.pressure.setText(String.format(ctx.getString(R.string.pressure_list_report), forecast.getPressure().getMax()));
        reportHolder.humidity.setText(String.format(ctx.getString(R.string.humidity_list_report), forecast.getHumidity().getMax()));
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
        private TextView temperature;
//            private ImageView icon;

        public ReportHolder(View itemView) {
            super(itemView);
            temperature = (TextView) itemView.findViewById(R.id.report_temperature);
            date = (TextView) itemView.findViewById(R.id.report_date);
            condition = (TextView) itemView.findViewById(R.id.report_condition);
            wind = (TextView) itemView.findViewById(R.id.report_wind);
            pressure = (TextView) itemView.findViewById(R.id.report_pressure);
            humidity = (TextView) itemView.findViewById(R.id.report_humidity);
        }
    }
}
