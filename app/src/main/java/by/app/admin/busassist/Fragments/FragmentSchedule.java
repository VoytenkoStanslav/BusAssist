package by.app.admin.busassist.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import by.app.admin.busassist.Adapters.ScheduleAdapter;
import by.app.admin.busassist.ConstClass.Schedule;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.Dialog.DatePickerFragment;
import by.app.admin.busassist.Dialog.DatePickerFragmentDo;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentSchedule extends Fragment{

    public static final String TABLE_NAME_SCHEDULE = "schedule";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCHEDULE_DATE_TIME = "date_time";
    public static final String COLUMN_SCHEDULE_TASK = "task";
    public static final String COLUMN_SCHEDULE_CHECK_TIME = "check_time";

    TextView edDateOtSchedule;
    TextView edDateDoSchedule;
    TextView tvGoneSchedOt;
    TextView tvGoneSchedDo;

    private RecyclerView rvSchedule;
    private RecyclerView.LayoutManager LMSchedule;
    private DBHelper dbHelper;
    private ScheduleAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        tvGoneSchedOt = (TextView) v.findViewById(R.id.tvGoneSchedOt);
        tvGoneSchedDo = (TextView) v.findViewById(R.id.tvGoneSchedDo);

        edDateOtSchedule = (TextView) v.findViewById(R.id.edDateOtSched);
        edDateOtSchedule.setPaintFlags(edDateOtSchedule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateOtSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(getFragmentManager(), "Date Picker");
            }
        });


        edDateDoSchedule = (TextView) v.findViewById(R.id.edDateDoSched);
        edDateDoSchedule.setPaintFlags(edDateDoSchedule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateDoSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DialogFragment newFragment = new DatePickerFragmentDo();
                newFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        Button btnripl = (Button) v.findViewById(R.id.btnripl);
        btnripl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int dateStartSearch = Integer.parseInt(tvGoneSchedOt.getText().toString());
                int dateFinishSearch = Integer.parseInt(tvGoneSchedDo.getText().toString());
                scheduleList(dateStartSearch, dateFinishSearch);
            }
        });

        rvSchedule = (RecyclerView) v.findViewById(R.id.rvSchedule);
        rvSchedule.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        rvSchedule.setHasFixedSize(true);
        LMSchedule = new LinearLayoutManager(getActivity());
        rvSchedule.setLayoutManager(LMSchedule);

        scheduleList(0, 2147397247);

        return v;
    }


    public void scheduleList(int dateStartSearch, int dateFinishSearch) {

        dbHelper = new DBHelper(getActivity());

        List<Schedule> scheduleLinkedList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SCHEDULE, null, COLUMN_SCHEDULE_DATE_TIME+" BETWEEN "+dateStartSearch+" AND "+(dateFinishSearch+86399),
                null ,null, null, COLUMN_SCHEDULE_DATE_TIME);
        Schedule schedule;

        if (cursor.moveToFirst()) {
            do {
                schedule = new Schedule();

                schedule.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                schedule.setDate_Time(cursor.getInt(cursor.getColumnIndex(COLUMN_SCHEDULE_DATE_TIME)));
                schedule.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TASK)));
                schedule.setCheck_time(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_CHECK_TIME)));
                scheduleLinkedList.add(schedule);
            } while (cursor.moveToNext());
        }
        adapter = new ScheduleAdapter(scheduleLinkedList, getActivity(), rvSchedule);
        rvSchedule.setAdapter(adapter);
        dbHelper.close();

    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}


