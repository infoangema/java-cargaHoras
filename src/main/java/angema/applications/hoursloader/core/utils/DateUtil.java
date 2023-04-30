package angema.applications.hoursloader.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class DateUtil {

    @Value("${configs.auth.timezone}")
    private String TIMEZONE;

    private SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return sdf;
    }

    public String getDateString() {
        return simpleDateFormat().format(new Date());
    }

    public String getMonthYearNameString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return sdf.format(new Date());
    }

    public String getDayMonthString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return dateFormat.format(new Date());
    }

    public String getDayMonthString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
            Date date = dateFormat.parse(dateString);
            dateFormat.applyPattern("dd-MM");
            return dateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public long getDateMillis() {
        String strDate = simpleDateFormat().format(new Date());
        Date strNow = new Date();
        try {
            strNow = simpleDateFormat().parse(strDate);
        } catch (ParseException ignored) {}
        return strNow.getTime();
    }

    public String getLastMonthWithYearString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
            Date date = dateFormat.parse(dateString);

            // Convertir la fecha a LocalDate
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Obtener el mes anterior
            localDate = localDate.minusMonths(1);

            // Formatear la fecha en el formato deseado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            String formattedDate = localDate.format(formatter);

            return formattedDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public String getMonthWihtYearString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
            Date date = dateFormat.parse(dateString);
            dateFormat.applyPattern("MM/yyyy");
            return dateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
