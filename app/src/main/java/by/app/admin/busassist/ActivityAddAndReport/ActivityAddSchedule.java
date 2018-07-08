package by.app.admin.busassist.ActivityAddAndReport;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.ConstClass.Notific;
import by.app.admin.busassist.ConstClass.Schedule;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.Notifications.AlarmReceiver;
import by.app.admin.busassist.R;

public class ActivityAddSchedule extends AppCompatActivity{


    public static final String TABLE_NAME_NOTIF = "notific_table";
    public static final String COLUMN_ID_NOTIF = "_id_notific";


    TextView tvTimeSchedule;
    TextView tvDateSchedule;
    TextView tvGSMD;
    TextView tvGSMT;

    EditText edTaskSched;
    CheckBox chbSchedTime;
    Integer d;
    Button btnOkSchedule;
    DBHelper dbHelper;
    Integer t = 0;

    CheckBox chbNotificSched;
    TextView tvNotificTextSched;
    TextView tvNotificTimeSched;


    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int request_code = (int) (CountId()+1);


        Intent alarmIntent = new Intent(ActivityAddSchedule.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ActivityAddSchedule.this, request_code, alarmIntent, 0);


        tvTimeSchedule = (TextView) findViewById(R.id.tvTimeSched);
        tvTimeSchedule.setPaintFlags(tvTimeSchedule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvTimeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });

        tvDateSchedule = (TextView) findViewById(R.id.tvDateSched);
        tvDateSchedule.setPaintFlags(tvDateSchedule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        tvGSMD = (TextView) findViewById(R.id.tvGoneSchedMainDate);

        tvGSMT = (TextView) findViewById(R.id.tvGoneSchedMainTime);

        edTaskSched = (EditText) findViewById(R.id.edTaskSched);

        chbNotificSched = (CheckBox) findViewById(R.id.chbNotificSchedule);


        chbSchedTime = (CheckBox) findViewById(R.id.chbSchedTime);
        chbSchedTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    tvTimeSchedule.setVisibility(View.VISIBLE);
                }
                if(isChecked == false){
                    tvTimeSchedule.setVisibility(View.GONE);
                }
            }
        });

        tvNotificTextSched = (TextView) findViewById(R.id.tvNotificTextSched);
        tvNotificTimeSched = (TextView) findViewById(R.id.tvNotificTimeSched);
        tvNotificTimeSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });

        chbNotificSched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    tvNotificTextSched.setVisibility(View.VISIBLE);
                    tvNotificTimeSched.setVisibility(View.VISIBLE);
                }
                if(isChecked == false){
                    tvNotificTextSched.setVisibility(View.GONE);
                    tvNotificTimeSched.setVisibility(View.GONE);
                }
            }
        });


        btnOkSchedule = (Button) findViewById(R.id.btnOkSchedule);
        btnOkSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSchedule();
            }
        });

    }

    private void saveSchedule(){
        String notificStatus;
        Integer date_time = Integer.parseInt(tvGSMT.getText().toString());
        String check_time = "true";
        if(chbSchedTime.isChecked() == false){
            //error name is empty
            date_time = Integer.valueOf(tvGSMD.getText().toString());
            check_time = "false";
        }
        if(chbNotificSched.isChecked() == true){
            //error name is empty
            notificStatus = "true";
        } else {
            notificStatus = "false";
        }
        Integer notific_time = t;
        String notific_name = edTaskSched.getText().toString().trim();


        String task = edTaskSched.getText().toString().trim();
        dbHelper = new DBHelper(this);

        if(!task.isEmpty()){
            //error name is empty


            if(date_time != 0){

                if(chbSchedTime.isChecked() == true) {
                    if (date_time >= DateDayTime()) {

                        if (chbNotificSched.isChecked() == true) {
                            if  ((date_time - notific_time)>DateDayTime()) {
                                Schedule schedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                                Notific notific = new Notific(notific_name);
                                dbHelper.saveNewSchedule(schedule);
                                dbHelper.saveNewNotif(notific);
                                triggerAlarmManager(date_time - notific_time);
                                goBackHome();
                            } else {
                                Snackbar.make(btnOkSchedule, "Время предупреждения не должно быть прошедшим", Snackbar.LENGTH_SHORT)
                                        .setAction("ActionDZ", null).show();
                            }
                        } else {
                            Schedule schedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                            Notific notific = new Notific(notific_name);
                            dbHelper.saveNewSchedule(schedule);
                            dbHelper.saveNewNotif(notific);
                            goBackHome();
                        }
                    } else {
                        Snackbar.make(btnOkSchedule, "Дата и время не должны быть прошедшими", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    if (date_time >= DateDay()) {
                        if (chbNotificSched.isChecked() == true) {
                            if  ((date_time - notific_time)>DateDayTime()) {
                                Schedule schedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                                Notific notific = new Notific(notific_name);
                                dbHelper.saveNewSchedule(schedule);
                                dbHelper.saveNewNotif(notific);
                                triggerAlarmManager(date_time - notific_time);
                                goBackHome();
                            } else {
                                Snackbar.make(btnOkSchedule, "Время предупреждения не должно быть прошедшим", Snackbar.LENGTH_SHORT)
                                        .setAction("ActionDZ", null).show();
                            }
                        } else {
                            Schedule schedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                            Notific notific = new Notific(notific_name);
                            dbHelper.saveNewSchedule(schedule);
                            dbHelper.saveNewNotif(notific);
                            goBackHome();
                        }

                    } else {
                        Snackbar.make(btnOkSchedule, "Дата не должна быть прошедшей", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                }
            } else {
                Snackbar.make(btnOkSchedule, "Введите дату", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        } else {
            Snackbar.make(btnOkSchedule, "Введите задачу", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBackHome(){
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 555);
        startActivity(goToAdd);
    }

    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (id == 1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, year, month, day);
            return tpd;
        }
        if (id == 2) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, hour, minute, true);
            return tpd;
        }
        if (id == 3) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTimeNotific, 0, 0, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            //final Calendar c = Calendar.getInstance();
            //int hour = c.get(Calendar.HOUR_OF_DAY);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            d = componentDateToTimestampDate(year, month, day);
            //if (hour>=12) {
            //    d = componentDateToTimestampDate(year, month, day) - 43200;
            //}
            String date = sdf.format(new Date((d)*1000L));
            tvGSMD.setText(String.valueOf(d));
            tvDateSchedule.setText(date);
            chbSchedTime.setEnabled(true);
            chbNotificSched.setEnabled(true);
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {

            Integer t = componentTimeToTimestampTime(hour, minute);
            Integer tdv = d + t;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(new Date((tdv)*1000L));
            tvGSMT.setText(String.valueOf(tdv));
            tvTimeSchedule.setText(time);
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackTimeNotific = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {

            t = componentTimeToTimestampTimeNotific(hour, minute);
            SimpleDateFormat sdh = new SimpleDateFormat("HH");
            SimpleDateFormat sdm = new SimpleDateFormat("mm");
            String timeHour = sdh.format(new Date((t-10800)*1000L));
            String timeMinute = sdm.format(new Date((t-10800)*1000L));
            tvNotificTimeSched.setText(timeHour + " ч. " + timeMinute + " мин. ");
        }
    };

    int componentDateToTimestampDate(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int componentTimeToTimestampTime(int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, (hour+3));
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis()/1000);

    }

    int componentTimeToTimestampTimeNotific(int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, hour+3);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis()/1000);

    }

    public long CountId() {

        long i = 0;
        long d;

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_NOTIF;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getLong(cursor.getColumnIndex(COLUMN_ID_NOTIF));
                if (d>=i){
                    i = d;
                }
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        Log.e("Loooog", "novovovovo" + i);
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

    int DateDayTime() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    public void triggerAlarmManager(int alarmTriggerTime) {
        // get a Calendar object with current time
        Calendar c = Calendar.getInstance();

        long timer = alarmTriggerTime*1000L;

        c.add(Calendar.SECOND, alarmTriggerTime);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, timer, pendingIntent);//set alarm manager with entered timer by converting into milliseconds

    }

    @Override
    public void onBackPressed() {
    }

}
