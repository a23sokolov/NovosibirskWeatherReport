package weatherapp.a23sokolov.com.misc;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

}
