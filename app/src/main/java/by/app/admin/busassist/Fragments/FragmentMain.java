package by.app.admin.busassist.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.Adapters.ScheduleAdapter;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentMain extends Fragment{

    private RecyclerView rvScheduleMain;
    private RecyclerView.LayoutManager LMScheduleMain;
    private DBHelper dbHelper;
    private ScheduleAdapter adapter;

    public static final Integer PERMISSION_REQUEST_CODE = 1;


    public static final String TABLE_NAME_MINUS = "balance_minus";
    public static final String COLUMN_DATE_MINUS = "date_minus";
    public static final String COLUMN_AMOUNT_MINUS = "amount_minus";

    TextView tvMinusSeg;

    public int d = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        rvScheduleMain = (RecyclerView) v.findViewById(R.id.rvScheduleMain);
        rvScheduleMain.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        rvScheduleMain.setHasFixedSize(true);
        // use a linear layout manager
        LMScheduleMain = new LinearLayoutManager(getActivity());
        rvScheduleMain.setLayoutManager(LMScheduleMain);

        //populate recyclerview
        populaterecyclerView();

        String stringDate = DateFormat.getDateInstance().format(new Date(System.currentTimeMillis()));

        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setPaintFlags(tvDate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDate.setText(String.valueOf(stringDate));

        tvMinusSeg = (TextView) v.findViewById(R.id.tvMinusSeg);
        tvMinusSeg.setText(String.format("%.2f",Math.abs(DayMinus())) + " BYN");



        return v;

    }

    private void populaterecyclerView(){
        dbHelper = new DBHelper(getActivity());
        adapter = new ScheduleAdapter(dbHelper.scheduleMainList(), getActivity(), rvScheduleMain);
        rvScheduleMain.setAdapter(adapter);

    }


    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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
}
