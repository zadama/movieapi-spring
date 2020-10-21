package se.experis.com.movieapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    public static java.util.Date getFormattedDate(String dateToBeFormatted){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = null;
        try {

            formattedDate =  format.parse(dateToBeFormatted);

        } catch (ParseException e) {
            System.out.println("Could not format the date...");
            e.printStackTrace();
        }

        return formattedDate;

    }
}
