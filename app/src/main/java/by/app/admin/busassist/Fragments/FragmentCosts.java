package by.app.admin.busassist.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import by.app.admin.busassist.ActivityAddAndReport.ActivityReportMinus;
import by.app.admin.busassist.ActivityAddAndReport.ActivityReportPlus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentCosts extends Fragment {

    public static final String TABLE_NAME_PLUS = "balance_plus";
    public static final String COLUMN_DATE_PLUS = "date_plus";
    public static final String COLUMN_AMOUNT_PLUS = "amount_plus";
    public static final String TABLE_NAME_MINUS = "balance_minus";
    public static final String COLUMN_DATE_MINUS = "date_minus";
    public static final String COLUMN_AMOUNT_MINUS = "amount_minus";

    public static final String TABLE_NAME_START_CAPITAL = "start_capital";
    public static final String COLUMN_ID_START_CAPITAL = "_id_start_capital";
    public static final String COLUMN_SUM_START_CAPITAL = "sum_start_capital";


    DBHelper dbHelper;
    Float balance;

    TextView tvSumCosts;

    int count = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_costs, container, false);


        Button btnReportMinus = (Button) v.findViewById(R.id.btnReportMinus);
        btnReportMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityReportMinus.class);
                startActivity(intent);
            }
        });

        Button btnReportPlus = (Button) v.findViewById(R.id.btnReportPlus);
        btnReportPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityReportPlus.class);
                startActivity(intent);
            }
        });

        TextView tvDayMinus = (TextView) v.findViewById(R.id.tvDayMinus);
        TextView tvWeekMinus = (TextView) v.findViewById(R.id.tvWeekMinus);
        TextView tvMonthMinus = (TextView) v.findViewById(R.id.tvMonthMinus);

        tvDayMinus.setText(String.format("%.2f",DayMinus()) + " BYN");
        tvWeekMinus.setText(String.format("%.2f",WeekMinus()) + " BYN");
        tvMonthMinus.setText(String.format("%.2f",MonthMinus()) + " BYN");


        TextView tvDayPlus = (TextView) v.findViewById(R.id.tvDayPlus);
        TextView tvWeekPlus = (TextView) v.findViewById(R.id.tvWeekPlus);
        TextView tvMonthPlus = (TextView) v.findViewById(R.id.tvMonthPlus);

        tvDayPlus.setText(String.format("%.2f",DayPlus()) + " BYN");
        tvWeekPlus.setText(String.format("%.2f",WeekPlus()) + " BYN");
        tvMonthPlus.setText(String.format("%.2f",MonthPlus()) + " BYN");


        tvSumCosts = (TextView) v.findViewById(R.id.tvSumCosts);
        tvSumCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        balance = BalancePlus() - BalanceMinus() + StartCapitalSum();

        int SC = StartCapital();
        if(SC==0){
            tvSumCosts.setText("Добавьте стартовый капитал");
        } else {
            tvSumCosts.setText(String.format("%.2f", balance) + " BYN");
        }

        return v;
    }

    public int StartCapital() {

        int i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID_START_CAPITAL + " FROM " + TABLE_NAME_START_CAPITAL;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                i = i + 1;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float StartCapitalSum() {

        int i = 0;
        float d = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_SUM_START_CAPITAL + " FROM " + TABLE_NAME_START_CAPITAL;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                i = i + 1;
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_SUM_START_CAPITAL));
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return d;
    }

    public float BalanceMinus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_MINUS + " FROM " + TABLE_NAME_MINUS + " WHERE " +
                COLUMN_DATE_MINUS + " BETWEEN " + 0 + " AND " + (DateFinishBalance() + 43199);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float DayMinus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_MINUS + " FROM " + TABLE_NAME_MINUS + " WHERE " +
                COLUMN_DATE_MINUS + " BETWEEN " + DateDay() + " AND " + (DateDay() + 86399);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float WeekMinus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_MINUS + " FROM " + TABLE_NAME_MINUS + " WHERE " +
                COLUMN_DATE_MINUS + " BETWEEN " + DateWeek() + " AND " + (DateWeek() + 604799);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float MonthMinus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_MINUS + " FROM " + TABLE_NAME_MINUS + " WHERE " +
                COLUMN_DATE_MINUS + " BETWEEN " + DateMonthStart() + " AND " + DateMonthFinish();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float BalancePlus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_PLUS + " FROM " + TABLE_NAME_PLUS + " WHERE " +
                COLUMN_DATE_PLUS + " BETWEEN " + 0 + " AND " + (DateFinishBalance() + 86399);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float DayPlus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_PLUS + " FROM " + TABLE_NAME_PLUS + " WHERE " +
                COLUMN_DATE_PLUS + " BETWEEN " + DateDay() + " AND " + (DateDay() + 86399);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float WeekPlus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_PLUS + " FROM " + TABLE_NAME_PLUS + " WHERE " +
                COLUMN_DATE_PLUS + " BETWEEN " + DateWeek() + " AND " + (DateWeek() + 604799);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    public float MonthPlus() {

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT_PLUS + " FROM " + TABLE_NAME_PLUS + " WHERE " +
                COLUMN_DATE_PLUS + " BETWEEN " + DateMonthStart() + " AND " + DateMonthFinish();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }

    int DateFinishBalance() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }


    int DateDay() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int DateWeek() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day_week = c.getFirstDayOfWeek();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_WEEK, day_week);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);


        return (int) (c.getTimeInMillis() / 1000);

    }

    int DateMonthStart() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int DateMonthFinish() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        Log.e("LoooogMonth", String.valueOf(month));
        if (month == 11) {
            month = -1;
            year = year +1;
        }
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, (month + 1));
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);


        return (int) (c.getTimeInMillis() / 1000);

    }
}
