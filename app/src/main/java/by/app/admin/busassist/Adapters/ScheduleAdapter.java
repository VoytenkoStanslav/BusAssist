package by.app.admin.busassist.Adapters;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import by.app.admin.busassist.ConstClass.Schedule;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.Notifications.AlarmNotificationService;
import by.app.admin.busassist.Notifications.AlarmReceiver;
import by.app.admin.busassist.R;
import by.app.admin.busassist.Update.ScheduleUpdateValue;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Admin on 03.04.2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> mScheduleList;
    private Context mContext;
    private RecyclerView mRVSchedule;
    DBHelper dbHelper;

    public static final String TABLE_NAME_SCHEDULE = "schedule";
    public static final String COLUMN_ID = "_id";


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView scheduleTime;
        public TextView scheduleDate;
        public TextView scheduleTask;
        public TextView scheduleYear;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            scheduleDate = (TextView) v.findViewById(R.id.tvSingleDate);
            scheduleTime = (TextView) v.findViewById(R.id.tvSingleTime);
            scheduleYear = (TextView) v.findViewById(R.id.tvSingleYear);
            scheduleTask = (TextView) v.findViewById(R.id.tvSingleTask);
        }
    }

    public void add(int position, Schedule person) {
        mScheduleList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mScheduleList.remove(position);
        notifyItemRemoved(position);

    }

    public ScheduleAdapter(List<Schedule> myDataset, FragmentActivity context, RecyclerView recyclerView) {
        mScheduleList = myDataset;
        mContext = context;
        mRVSchedule = recyclerView;
    }

    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_schedule, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {


        final Schedule schedule = mScheduleList.get(position);

        //Integer OutDate = Integer.parseInt(schedule.getDate_Time());
        //SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        //tvDate.setText(sdf.format(new Date(System.currentTimeMillis())));
        String date = new SimpleDateFormat("d MMM").format(new Date(schedule.getDate_Time()*1000L));
        String year = new SimpleDateFormat("yyyy").format(new Date(schedule.getDate_Time()*1000L));
        String time = new SimpleDateFormat("HH:mm").format(new Date(schedule.getDate_Time()*1000L));
        String check_time = schedule.getCheck_time();
        //holder.scheduleDate.setText(" " + date.format(new Date(schedule.getDate_Time()*1000)));
        holder.scheduleDate.setText(date);
        holder.scheduleYear.setText(year);
        holder.scheduleTime.setText(time);

        if (check_time.equals("false")){
            holder.scheduleTime.setText("--:--");
        }
        holder.scheduleTask.setText(schedule.getTask());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                Log.e("LOG_ID", String.valueOf(schedule.getId()));
                Log.e("LOG_ID_COUNT", String.valueOf(CountId()));
                builder.setTitle("Параметры");
                builder.setMessage("Удалить или изменить задачу?");
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(schedule.getId());

                    }
                });
                builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.deleteScheduleRecord(schedule.getId(), mContext);


                        mScheduleList.remove(position);
                        stopAlarmManager((int) schedule.getId());
                        //mRVSchedule.removeViewAt(position);
                        //notifyItemRemoved(position);
                        //notifyItemRangeChanged(position, mScheduleList.size());

                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void goToUpdateActivity(long scheduleId){
        Intent goToUpdate = new Intent(mContext, ScheduleUpdateValue.class);
        goToUpdate.putExtra("SCHEDULE_ID", scheduleId);
        mContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mScheduleList.size();
    }

    public void stopAlarmManager(int code) {



        try {
            Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, code, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
            manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

            //remove the notification from notification tray
            NotificationManager notificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
            Log.e("Ggjgk", "sjdkajskdjaksjdakjsdkajskdaksdkajs   " + code );
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    public long CountId() {

        long i = 0;
        long d;

        dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_SCHEDULE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                if (d>=i){
                    i = d;
                }
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        Log.e("Loooog", "novovovovo" + i);
        return i;
    }

}
