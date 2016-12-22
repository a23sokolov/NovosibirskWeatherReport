package weatherapp.a23sokolov.com.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weatherapp.a23sokolov.com.misc.Utils;
import weatherapp.a23sokolov.com.model.elements.Forecast;
import weatherapp.a23sokolov.com.weatherapp.R;

/**
 * Created by alexey on 21/12/16.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Forecast> forecasts;
    private Context ctx;
    private OnViewHolderClickListener listener;

    public interface OnViewHolderClickListener {
        public void onItemClicked(int position);
    }

    public SimpleRecyclerAdapter(final List<Forecast> forecastList, final Context context) {
        forecasts = forecastList;
        ctx = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                final View viewInfo = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_report_section_info, parent, false);
                return new InfoHolder(viewInfo);
            case 1:
                final View viewFooter = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_report_section_footer, parent, false);
                return new FooterHolder(viewFooter);
            default:
                final View viewReport = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_report_section, parent, false);
                return new ReportHolder(viewReport);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 1 )
            return 0;
        else if (position == forecasts.size())
            return 1;
        else return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) != -1)
            return;
        final ReportHolder reportHolder = (ReportHolder) holder;
        final Forecast forecast = forecasts.get(position);
        String resultStr = forecast.getDate();
        final String[] condition = ctx.getResources().getStringArray(R.array.condition);
        reportHolder.date.setText(resultStr);
        reportHolder.temperature.setText(String.format(ctx.getString(R.string.weather_celsius), forecast.getTemperature().getMax()));
        reportHolder.condition.setText(condition[forecast.getCloudiness()]);
        reportHolder.wind.setText(String.format(ctx.getString(R.string.wind_list_report),
                forecast.getWind().getWindDirection().getLocalizedString(),
                forecast.getWind().getWindDirection().getArrow(),
                forecast.getWindSpeed()));
        reportHolder.pressure.setText(String.format(ctx.getString(R.string.pressure_list_report), forecast.getPressure().getMax()));
        reportHolder.humidity.setText(String.format(ctx.getString(R.string.humidity_list_report), forecast.getHumidity().getMax()));

        Typeface weatherFont = Typeface.createFromAsset(ctx.getAssets(), "fonts/weather.ttf");
        reportHolder.icon.setTypeface(weatherFont);
        reportHolder.icon.setText(Utils.getWeatherIcon(forecast));
        reportHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forecasts.size() + 1;
    }

    public void setListener(OnViewHolderClickListener listener){
        this.listener = listener;
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
        private TextView icon;
        private CardView cardView;

        public ReportHolder(View itemView) {
            super(itemView);
            temperature = (TextView) itemView.findViewById(R.id.report_temperature);
            date = (TextView) itemView.findViewById(R.id.report_date);
            condition = (TextView) itemView.findViewById(R.id.report_condition);
            wind = (TextView) itemView.findViewById(R.id.report_wind);
            pressure = (TextView) itemView.findViewById(R.id.report_pressure);
            humidity = (TextView) itemView.findViewById(R.id.report_humidity);
            icon = (TextView) itemView.findViewById(R.id.report_icon);
            cardView = (CardView) itemView.findViewById(R.id.report_card_root);
        }
    }

    class InfoHolder extends RecyclerView.ViewHolder {
        public InfoHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
