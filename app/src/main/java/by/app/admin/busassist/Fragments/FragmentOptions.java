package by.app.admin.busassist.Fragments;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.ActivityAddAndReport.ActivityAddStartCapital;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.Notifications.AlarmNotificationService;
import by.app.admin.busassist.Notifications.AlarmReceiver;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentOptions extends Fragment {

    public static final String TABLE_NAME_SCHEDULE = "schedule";
    public static final String COLUMN_SCHEDULE_DATE_TIME = "date_time";

    public static final String TABLE_NAME_PROJECT = "project";

    public static final String TABLE_NAME_NOTIF = "notific_table";
    public static final String COLUMN_ID_NOTIF = "_id_notific";

    public static final String TABLE_NAME_START_CAPITAL = "start_capital";
    public static final String COLUMN_ID_START_CAPITAL = "_id_start_capital";

    final long id = 1;

    Button btnUpdateSC;
    Button btnDeleteShedMonth;
    Button btnDeleteShedFinish;
    Button btnDeleteAllSched;
    Button btnDeleteProjectFinish;


    DBHelper dbHelper;

    Integer d;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_options, container, false);

        d = StartCapital();

        btnUpdateSC = (Button) v.findViewById(R.id.btnUpdateSC);
        btnUpdateSC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (d==0){
                    Snackbar.make(btnUpdateSC, "Стартовый капитал не был добавлен", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                } else {
                    Log.e("LoooooooooooooooooooG", "click");
                    Intent goToUpdate = new Intent(getActivity(), ActivityAddStartCapital.class);
                    goToUpdate.putExtra("START_CAPITAL", id);
                    startActivity(goToUpdate);
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        final String dateStart = sdf.format(new Date(getMonthBack()*1000L));
        final String dateFinish = sdf.format(new Date(getMonthToday()*1000L));

        btnDeleteShedMonth = (Button) v.findViewById(R.id.btnDeleteSchedMonth);
        btnDeleteShedMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Удаление");
                builder.setMessage("Будут удалены все задачи с " + dateStart + " до " + dateFinish);
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteScheduleRecordBackMonth();

                    }
                });
                builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        final String dayToday = sdf.format(new Date(getDayToday()*1000L));

        btnDeleteShedFinish = (Button) v.findViewById(R.id.btnDeleteShedFinish);
        btnDeleteShedFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Удаление");
                builder.setMessage("Будут удалены все задачи до " + dayToday);
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteScheduleRecordBackToday();

                    }
                });
                builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        btnDeleteAllSched = (Button) v.findViewById(R.id.btnDeleteAllShed);
        btnDeleteAllSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Удаление");
                builder.setMessage("Будут удалены абсолютно все задачи");
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteScheduleAll();
                        deleteAllNotific();

                    }
                });
                builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        btnDeleteProjectFinish = (Button) v.findViewById(R.id.btnDeleteProjFinish);
        btnDeleteProjectFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Удаление");
                builder.setMessage("Будут удалены проекты, у которых прошел срок сдачи");
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteScheduleAll();

                    }
                });
                builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        return v;
    }

    public void deleteScheduleRecordBackMonth() {
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_SCHEDULE+" WHERE " + COLUMN_SCHEDULE_DATE_TIME +
                " BETWEEN " + getMonthBack() + " AND " + getMonthToday());
        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public void deleteScheduleRecordBackToday() {
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_SCHEDULE+" WHERE date_time<" + getDayToday());
        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public void deleteScheduleAll() {
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_SCHEDULE);
        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public void deleteProjectFinish() {
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_PROJECT+" WHERE date_finish_project<" + getDayToday());
        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }



    int getMonthToday() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int getMonthBack() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        if (month == 0){
            month = 12;
            year = year - 1;
        }
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int getDayToday() {

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

    public void deleteAllNotific() {

        long d;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_NOTIF;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getLong(cursor.getColumnIndex(COLUMN_ID_NOTIF));
                stopAlarmManager(d);
            } while (cursor.moveToNext());
        }
        dbHelper.close();
    }

    public void stopAlarmManager(long code) {

        try {

            Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), (int) code, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);

            //remove the notification from notification tray
            NotificationManager notificationManager = (NotificationManager) getActivity()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        }catch (RuntimeException e){
            e.printStackTrace();
        }
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

}
