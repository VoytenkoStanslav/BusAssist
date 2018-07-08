package by.app.admin.busassist.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.R;

/**
 * Created by Admin on 02.04.2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        TextView tv = (TextView) getActivity().findViewById(R.id.edDateOtSched);
        TextView tvGone = (TextView) getActivity().findViewById(R.id.tvGoneSchedOt);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String date = sdf.format(new Date(componentTimeToTimestamp(year, month, day)*1000L));
        String g = String.valueOf((componentTimeToTimestamp(year, month, day)));
        tvGone.setText(g);
        tv.setText(date);

    }

    int componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);

    }
}

    //@Override
   /* public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //int hour = c.get(Calendar.HOUR_OF_DAY);
        //int minute = c.get(Calendar.MINUTE);



       //return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day);
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, year, month, day);
    } */

    /*public void onDateSet(DatePicker view, int year, int month, int day) {
        FragmentSchedule frSched = new FragmentSchedule();
        //Do something with the date chosen by the user
        TextView tv = (TextView) getActivity().findViewById(R.id.edDateOtSched);
        String stringOfDate = year + "." + (month+1) + "." + day;
        int g = componentTimeToTimestamp(year, month, day);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(componentTimeToTimestamp(year, month, day)*1000L));
        //tv.setText(date);
        frSched.dateStartSearch = g;
        tv.setText(String.valueOf(g));
    }

    int componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);

    }*/
