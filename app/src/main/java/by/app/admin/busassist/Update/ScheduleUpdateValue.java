package by.app.admin.busassist.Update;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import by.app.admin.busassist.ConstClass.Schedule;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.Notifications.AlarmNotificationService;
import by.app.admin.busassist.Notifications.AlarmReceiver;
import by.app.admin.busassist.R;

import static java.lang.String.valueOf;

/**
 * Created by Admin on 03.04.2018.
 */

public class ScheduleUpdateValue extends AppCompatActivity {

    TextView tvTimeSchedule;
    TextView tvDateSchedule;
    EditText edTaskSched;
    Button btnOkSchedule;

    CheckBox chbSchedTime;

    Integer d;
    Integer t;
    Integer notific_t;
    TextView tvGSMD;
    TextView tvGSMT;

    CheckBox chbNotificSched;
    TextView tvNotificTextSched;
    TextView tvNotificTimeSched;

    private DBHelper dbHelper;
    private long receivedPersonId;

    private PendingIntent pendingIntent;

    String dateNotUp;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init
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

        chbNotificSched = (CheckBox) findViewById(R.id.chbNotificSchedule);

        edTaskSched = (EditText) findViewById(R.id.edTaskSched);
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

        dbHelper = new DBHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("SCHEDULE_ID", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Schedule queriedSchedule = dbHelper.getOneSchedule(receivedPersonId);
        dateNotUp = new SimpleDateFormat("dd/MM/yyyy").format(new Date(queriedSchedule.getDate_Time()*1000L));
        String date = new SimpleDateFormat("dd MMM yyyy").format(new Date(queriedSchedule.getDate_Time()*1000L));
        time = new SimpleDateFormat("HH:mm").format(new Date(queriedSchedule.getDate_Time() * 1000L));
        tvGSMD.setText(valueOf(queriedSchedule.getDate_Time()));
        tvGSMT.setText(valueOf(queriedSchedule.getDate_Time()));
        tvDateSchedule.setText(date);
        tvTimeSchedule.setText(time);
        edTaskSched.setText(queriedSchedule.getTask());
        chbSchedTime.setEnabled(true);
        chbNotificSched.setEnabled(true);
        String check = queriedSchedule.getCheck_time();
        if(check.equals("true")){
            chbSchedTime.setChecked(true);
        }
        d = componentDateNotUpdate();
        tvGSMD.setText(String.valueOf(d));
        //t = componentTimeNotUpdate();
        //Integer tdv = t + d;
        //tvGSMT.setText(String.valueOf(tdv));

        notific_t = queriedSchedule.getNotific_time();
        String timeNotificHour = new SimpleDateFormat("HH").format(new Date((queriedSchedule.getNotific_time() - 10800) * 1000L));
        String timeNotificMinute = new SimpleDateFormat("mm").format(new Date((queriedSchedule.getNotific_time() - 10800) * 1000L));
        tvNotificTimeSched.setText(timeNotificHour + " ч. " + timeNotificMinute + " мин. ");
        String checkNotific = queriedSchedule.getNotific();
        if(checkNotific.equals("true")){
            chbNotificSched.setChecked(true);
        }

        Intent alarmIntent = new Intent(ScheduleUpdateValue.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ScheduleUpdateValue.this, (int) receivedPersonId, alarmIntent, 0);



        btnOkSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateSchedule();
            }
        });
    }

    private void updateSchedule() {
        Integer date_time = Integer.parseInt(tvGSMT.getText().toString());
        String check_time = "true";
        if(chbSchedTime.isChecked() == false){
            //error name is empty
            date_time = Integer.valueOf(tvGSMD.getText().toString());
            check_time = "false";
        }
        String task = edTaskSched.getText().toString().trim();
        String notificStatus;
        if(chbNotificSched.isChecked() == true){
            //error name is empty
            notificStatus = "true";
        } else {
            notificStatus = "false";
        }
        Integer notific_time = notific_t;

        dbHelper = new DBHelper(this);


        if(!task.isEmpty()){
            //error name is empty


            if(date_time != 0){

                if(chbSchedTime.isChecked() == true) {
                    if (date_time >= DateDayTime()) {

                        if (chbNotificSched.isChecked() == true) {
                            if  ((date_time - notific_time)>DateDayTime()) {
                                Schedule updateSchedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                                dbHelper.updateScheduleRecord(receivedPersonId, this, updateSchedule);
                                triggerAlarmManager(date_time - notific_time);
                                goBackHome();
                            } else {
                                Snackbar.make(btnOkSchedule, "Время предупреждения не должно быть прошедшим", Snackbar.LENGTH_SHORT)
                                        .setAction("ActionDZ", null).show();
                            }
                        } else {
                            Schedule updateSchedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                            dbHelper.updateScheduleRecord(receivedPersonId, this, updateSchedule);
                            stopAlarmManager();
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
                                Schedule updateSchedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                                dbHelper.updateScheduleRecord(receivedPersonId, this, updateSchedule);
                                triggerAlarmManager(date_time - notific_time);
                                goBackHome();
                            } else {
                                Snackbar.make(btnOkSchedule, "Время предупреждения не должно быть прошедшим", Snackbar.LENGTH_SHORT)
                                        .setAction("ActionDZ", null).show();
                            }
                        } else {
                            Schedule updateSchedule = new Schedule(date_time, task, check_time, notificStatus, notific_time);
                            dbHelper.updateScheduleRecord(receivedPersonId, this, updateSchedule);
                            stopAlarmManager();
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            d = componentDateToTimestampDate(year, month, day);
            String date = sdf.format(new Date(d*1000L));
            tvGSMD.setText(valueOf(d));
            tvDateSchedule.setText(date);
            t = componentTimeNotUpdate();
            Integer tdv = t + d;
            tvGSMT.setText(String.valueOf(tdv));
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {

            Integer t = componentTimeToTimestampTime(hour, minute);
            Integer tdv = d + t;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(new Date(tdv*1000L));
            tvGSMT.setText(valueOf(tdv));
            tvTimeSchedule.setText(time);
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackTimeNotific = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {

            notific_t = componentTimeToTimestampTimeNotific(hour, minute);
            SimpleDateFormat sdh = new SimpleDateFormat("HH");
            SimpleDateFormat sdm = new SimpleDateFormat("mm");
            String timeHour = sdh.format(new Date((notific_t-10800)*1000L));
            String timeMinute = sdm.format(new Date((notific_t-10800)*1000L));
            tvNotificTimeSched.setText(timeHour + " ч. " + timeMinute + " мин. ");
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBackHome() {
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 555);
        startActivity(goToAdd);
    }

    int componentDateToTimestampDate(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Log.e("Looogogog", String.valueOf(day));

        return (int) (c.getTimeInMillis() / 1000);

    }

    int componentTimeToTimestampTime(int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, (hour + 3));
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int componentTimeToTimestampTimeNotific(int hour, int minute) {

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

    int componentDateNotUpdate() {

        int mYear = Integer.parseInt(dateNotUp.substring(6));
        int mMonth = Integer.parseInt(dateNotUp.substring(3,5));
        int mDay = Integer.parseInt(dateNotUp.substring(0,2));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, mYear);
        c.set(Calendar.MONTH, (mMonth-1));
        c.set(Calendar.DAY_OF_MONTH, mDay);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);
    }

    int componentTimeNotUpdate() {

        int mHour = Integer.parseInt(time.substring(0,2));
        int mMinute = Integer.parseInt(time.substring(3));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, (mHour+3));
        c.set(Calendar.MINUTE, mMinute);
        c.set(Calendar.SECOND, 0);
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

    public void stopAlarmManager() {



        try {

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);

            //remove the notification from notification tray
            NotificationManager notificationManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
