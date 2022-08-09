package com.example.clone_olx.Helper;

import static java.text.DateFormat.getDateInstance;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SetMask {

    public static String getDate(long publicationDate, int type){
        final int DAY_MONTH_YEAR_HOUR_MINUTE = 1;
        final int DAY_MONTH_YEAR = 2;
        final int DAY_MONTH = 3;
        final int HOUR_MINUTE = 4;

        Locale locale = new Locale("PT", "br");
        String timezone = "America/Sao_Paulo";

        SimpleDateFormat daySdf = new SimpleDateFormat("dd", locale);
        daySdf.setTimeZone(TimeZone.getTimeZone(timezone));

        SimpleDateFormat monthSdf = new SimpleDateFormat("MM", locale);
        monthSdf.setTimeZone(TimeZone.getTimeZone(timezone));

        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy", locale);
        yearSdf.setTimeZone(TimeZone.getTimeZone(timezone));

        SimpleDateFormat minuteSdf = new SimpleDateFormat("mm", locale);
        minuteSdf.setTimeZone(TimeZone.getTimeZone(timezone));

        SimpleDateFormat hourSdf = new SimpleDateFormat("HH", locale);
        hourSdf.setTimeZone(TimeZone.getTimeZone(timezone));

        DateFormat dateFormat = getDateInstance();
        Date netDate = new Date(publicationDate);
        dateFormat.format(netDate);

        String day = daySdf.format(netDate);
        String month = monthSdf.format(netDate);
        String year = yearSdf.format(netDate);
        String hour = hourSdf.format(netDate);
        String minute = monthSdf.format(netDate);

        if(type == 3){
            switch(month){
                case "01":
                    month = "Janeiro";
                    break;
                case "02":
                    month = "Fevereiro";
                    break;
                case "03":
                    month = "Março";
                    break;
                case "04":
                    month = "Abril";
                    break;
                case "05":
                    month = "Maio";
                    break;
                case "06":
                    month = "Junho";
                    break;
                case "07":
                    month = "Julho";
                    break;
                case "08":
                    month = "Agosto";
                    break;
                case "09":
                    month = "Setembro";
                    break;
                case "10":
                    month = "Outubro";
                    break;
                case "11":
                    month = "Novemrbo";
                    break;
                case "12":
                    month = "Dezembro";
                    break;
                default :
                    month = "";
                    break;

            }
        }

        String time;
        switch(type){
            case DAY_MONTH_YEAR_HOUR_MINUTE:
                time = day+"/"+month+"/"+year+ " " +hour+":"+minute;
                break;
            case DAY_MONTH_YEAR:
                time = day+"/"+month+"/"+year;
                break;
            case DAY_MONTH:
                time = day+" de "+month;
                break;
            case HOUR_MINUTE:
                time = hour+":"+minute;
                break;
            default:
                time = "";
                break;
        }

        return time;
    }

    public static String getValue(double value){
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(
            new Locale("PT","br")
        ));
        return nf.format(value);
    }

}
