package by.app.admin.busassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import by.app.admin.busassist.ConstClass.BalanceMinus;
import by.app.admin.busassist.ConstClass.BalancePlus;
import by.app.admin.busassist.ConstClass.Capital;
import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.ConstClass.Notific;
import by.app.admin.busassist.ConstClass.Project;
import by.app.admin.busassist.ConstClass.Schedule;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 03.04.2018.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BusAssist.db";
    private static final int DATABASE_VERSION = 1 ;

    public static final String TABLE_NAME_SCHEDULE = "schedule";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCHEDULE_DATE_TIME = "date_time";
    public static final String COLUMN_SCHEDULE_TASK = "task";
    public static final String COLUMN_SCHEDULE_CHECK_TIME = "check_time";
    public static final String COLUMN_SCHEDULE_NOTIFIC = "notific";
    public static final String COLUMN_SCHEDULE_NOTIFIC_TIME = "notific_time";



    public static final String TABLE_NAME_PLUS = "balance_plus";
    public static final String COLUMN_ID_PLUS = "_id_plus";
    public static final String COLUMN_DATE_PLUS = "date_plus";
    public static final String COLUMN_CATEGORY_PLUS = "category_plus";
    public static final String COLUMN_CATEGORY_POS_PLUS = "category_pos_plus";
    public static final String COLUMN_AMOUNT_PLUS = "amount_plus";
    public static final String COLUMN_COMMIT_PLUS = "commit_plus";


    public static final String TABLE_NAME_MINUS = "balance_minus";
    public static final String COLUMN_ID_MINUS = "_id_minus";
    public static final String COLUMN_DATE_MINUS = "date_minus";
    public static final String COLUMN_CATEGORY_MINUS = "category_minus";
    public static final String COLUMN_CATEGORY_POS_MINUS = "category_pos_minus";
    public static final String COLUMN_AMOUNT_MINUS = "amount_minus";
    public static final String COLUMN_COMMIT_MINUS = "commit_minus";


    public static final String TABLE_NAME_COLLEAGUES = "colleagues";
    public static final String COLUMN_COLLEAGUES_ID = "_id_colleag";
    public static final String COLUMN_COLLEAGUES_FHOTO = "colleag_foto";
    public static final String COLUMN_COLLEAGUES_NAME = "colleag_name";
    public static final String COLUMN_COLLEAGUES_NAMBER = "colleag_number";
    public static final String COLUMN_COLLEAGUES_SKYPE = "colleag_skype";
    public static final String COLUMN_COLLEAGUES_COMMIT = "colleag_commit";


    public static final String TABLE_NAME_CLIENT = "client";
    public static final String COLUMN_CLIENT_ID = "_id_client";
    public static final String COLUMN_CLIENT_FHOTO = "image_path_client";
    public static final String COLUMN_CLIENT_CATEGORY = "type_client";
    public static final String COLUMN_CLIENT_CATEGORY_POS = "type_pos_client";
    public static final String COLUMN_CLIENT_COMPANY = "company_client";
    public static final String COLUMN_CLIENT_NAME = "name_client";
    public static final String COLUMN_CLIENT_NUMBER = "number_client";
    public static final String COLUMN_CLIENT_SKYPE = "skype_client";
    public static final String COLUMN_CLIENT_COMMIT = "commit_client";
    public static final String COLUMN_CLIENT_PROJECT = "project_client";


    public static final String TABLE_NAME_PROJECT = "project";
    public static final String COLUMN_PROJECT_ID = "_id_project";
    public static final String COLUMN_PROJECT_CATEGORY = "type_project";
    public static final String COLUMN_PROJECT_CATEGORY_POS = "type_pos_project";
    public static final String COLUMN_PROJECT_NAME = "name_project";
    public static final String COLUMN_PROJECT_COMPANY = "company_project";
    public static final String COLUMN_PROJECT_RESPONSIBLE = "responsible_for_project";
    public static final String COLUMN_PROJECT_FINISH = "date_finish_project";


    public static final String TABLE_NAME_START_CAPITAL = "start_capital";
    public static final String COLUMN_ID_START_CAPITAL = "_id_start_capital";
    public static final String COLUMN_SUM_START_CAPITAL = "sum_start_capital";


    public static final String TABLE_NAME_NOTIF = "notific_table";
    public static final String COLUMN_ID_NOTIF = "_id_notific";
    public static final String COLUMN_NAME_NOTIF = "name_notific";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + TABLE_NAME_SCHEDULE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCHEDULE_DATE_TIME + " INTEGER NOT NULL, " +
                COLUMN_SCHEDULE_CHECK_TIME + " TEXT NOT NULL, " +
                COLUMN_SCHEDULE_TASK + " TEXT NOT NULL, " +
                COLUMN_SCHEDULE_NOTIFIC + " TEXT NOT NULL, " +
                COLUMN_SCHEDULE_NOTIFIC_TIME + " INTEGER NOT NULL);"
        );
        db.execSQL(" CREATE TABLE " + TABLE_NAME_PLUS + " (" +
                COLUMN_ID_PLUS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE_PLUS + " INTEGER NOT NULL, " +
                COLUMN_CATEGORY_PLUS + " STRING NOT NULL, " +
                COLUMN_CATEGORY_POS_PLUS + " INTEGER NOT NULL, " +
                COLUMN_AMOUNT_PLUS + " REAL NOT NULL, " +
                COLUMN_COMMIT_PLUS + " STRING);"
        );
        db.execSQL(" CREATE TABLE " + TABLE_NAME_MINUS + " (" +
                COLUMN_ID_MINUS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE_MINUS + " INTEGER NOT NULL, " +
                COLUMN_CATEGORY_MINUS + " STRING NOT NULL, " +
                COLUMN_CATEGORY_POS_MINUS + " INTEGER NOT NULL, " +
                COLUMN_AMOUNT_MINUS + " REAL NOT NULL, " +
                COLUMN_COMMIT_MINUS + " STRING);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_NAME_COLLEAGUES + " (" +
                COLUMN_COLLEAGUES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COLLEAGUES_FHOTO + " STRING, " +
                COLUMN_COLLEAGUES_NAME + " STRING NOT NULL, " +
                COLUMN_COLLEAGUES_NAMBER + " STRING NOT NULL, " +
                COLUMN_COLLEAGUES_SKYPE + " STRING NOT NULL, " +
                COLUMN_COLLEAGUES_COMMIT + " STRING NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_NAME_CLIENT + " (" +
                COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENT_FHOTO + " STRING, " +
                COLUMN_CLIENT_CATEGORY + " STRING NOT NULL, " +
                COLUMN_CLIENT_CATEGORY_POS + " INTEGER NOT NULL, " +
                COLUMN_CLIENT_COMPANY + " STRING NOT NULL, " +
                COLUMN_CLIENT_NAME + " STRING NOT NULL, " +
                COLUMN_CLIENT_NUMBER + " STRING NOT NULL, " +
                COLUMN_CLIENT_SKYPE + " STRING NOT NULL, " +
                COLUMN_CLIENT_PROJECT + " STRING NOT NULL, " +
                COLUMN_CLIENT_COMMIT + " STRING NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_NAME_PROJECT + " (" +
                COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PROJECT_CATEGORY + " STRING NOT NULL, " +
                COLUMN_PROJECT_CATEGORY_POS + " INTEGER NOT NULL, " +
                COLUMN_PROJECT_NAME + " STRING NOT NULL, " +
                COLUMN_PROJECT_COMPANY + " STRING NOT NULL, " +
                COLUMN_PROJECT_RESPONSIBLE + " STRING NOT NULL, " +
                COLUMN_PROJECT_FINISH + " INTEGER NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_NAME_START_CAPITAL + " (" +
                COLUMN_ID_START_CAPITAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SUM_START_CAPITAL + " REAL NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_NAME_NOTIF + " (" +
                COLUMN_ID_NOTIF + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_NOTIF + " STRING NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public void saveNewBalanceMinus(BalanceMinus balanceMinus) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE_MINUS, balanceMinus.getDate_minus());
        values.put(COLUMN_CATEGORY_MINUS, balanceMinus.getCategory_minus());
        values.put(COLUMN_CATEGORY_POS_MINUS, balanceMinus.getCategory_pos_minus());
        values.put(COLUMN_AMOUNT_MINUS, balanceMinus.getAmount_minus());
        values.put(COLUMN_COMMIT_MINUS, balanceMinus.getCommit_minus());


        // insert
        db.insert(TABLE_NAME_MINUS,null, values);
        db.close();

    }

    public BalanceMinus getOneBalanceMinus(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_MINUS + " WHERE _id_minus =" + id;
        Cursor cursor = db.rawQuery(query, null);

        BalanceMinus rvBalanceMinus = new BalanceMinus();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            rvBalanceMinus.setDate_minus(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_MINUS)));
            rvBalanceMinus.setCategory_minus(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_MINUS)));
            rvBalanceMinus.setCategory_pos_minus(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_POS_MINUS)));
            rvBalanceMinus.setAmount_minus(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS)));
            rvBalanceMinus.setCommit_minus(cursor.getString(cursor.getColumnIndex(COLUMN_COMMIT_MINUS)));
        }
        return rvBalanceMinus;
    }

    public void deleteBalanceMinus(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_MINUS+" WHERE _id_minus ='"+id+"'");
    }

    /**update record**/
    public void updateBalanceMinus(long balanceMinusId, Context context, BalanceMinus updatedBalanceMinus) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_MINUS+" SET date_minus ='"+ updatedBalanceMinus.getDate_minus() + "', category_minus ='"
                + updatedBalanceMinus.getCategory_minus() + "', category_pos_minus ='" + updatedBalanceMinus.getCategory_pos_minus() +
                "', amount_minus ='" + updatedBalanceMinus.getAmount_minus() + "', commit_minus ='" +
                updatedBalanceMinus.getCommit_minus() + "'  WHERE _id_minus ='" + balanceMinusId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }





    public void saveNewBalancePlus(BalancePlus balancePlus) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE_PLUS, balancePlus.getDate_plus());
        values.put(COLUMN_CATEGORY_PLUS, balancePlus.getCategory_plus());
        values.put(COLUMN_CATEGORY_POS_PLUS, balancePlus.getCategory_pos_plus());
        values.put(COLUMN_AMOUNT_PLUS, balancePlus.getAmount_plus());
        values.put(COLUMN_COMMIT_PLUS, balancePlus.getCommit_plus());

        // insert
        db.insert(TABLE_NAME_PLUS,null, values);
        db.close();

    }

    public BalancePlus getOneBalancePlus(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_PLUS + " WHERE _id_plus =" + id;
        Cursor cursor = db.rawQuery(query, null);

        BalancePlus rvBalancePlus = new BalancePlus();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            rvBalancePlus.setDate_plus(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_PLUS)));
            rvBalancePlus.setCategory_plus(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_PLUS)));
            rvBalancePlus.setCategory_pos_plus(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_POS_PLUS)));
            rvBalancePlus.setAmount_plus(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS)));
            rvBalancePlus.setCommit_plus(cursor.getString(cursor.getColumnIndex(COLUMN_COMMIT_PLUS)));
        }
        return rvBalancePlus;
    }

    public void deleteBalancePlus(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_PLUS+" WHERE _id_plus ='"+id+"'");
    }

    /**update record**/
    public void updateBalancePlus(long balancePlusId, Context context, BalancePlus updatedBalancePlus) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_PLUS+" SET date_plus ='"+ updatedBalancePlus.getDate_plus() + "', category_plus ='"
                + updatedBalancePlus.getCategory_plus() + "', category_pos_plus ='" + updatedBalancePlus.getCategory_pos_plus()
                + "', amount_plus ='" + updatedBalancePlus.getAmount_plus() + "', commit_plus ='"
                + updatedBalancePlus.getCommit_plus() + "'  WHERE _id_plus ='" + balancePlusId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }






    public void saveNewSchedule(Schedule schedule) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHEDULE_DATE_TIME, schedule.getDate_Time());
        values.put(COLUMN_SCHEDULE_TASK, schedule.getTask());
        values.put(COLUMN_SCHEDULE_CHECK_TIME, schedule.getCheck_time());
        values.put(COLUMN_SCHEDULE_NOTIFIC, schedule.getNotific());
        values.put(COLUMN_SCHEDULE_NOTIFIC_TIME, schedule.getNotific_time());

        // insert
        db.insert(TABLE_NAME_SCHEDULE,null, values);
        db.close();
    }

    public List<Schedule> scheduleMainList() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int dayStart;
        int dayFinish;

        dayStart = componentDateToTimestamp(year, month, day);
        dayFinish = componentDateToTimestamp(year, month, day) + 86399;

        if (hour>=12) {
            dayStart = componentDateToTimestamp(year, month, day) - 43200;
            dayFinish = componentDateToTimestamp(year, month, day) + 43199;
        }

        String query;
        query = "SELECT  * FROM " + TABLE_NAME_SCHEDULE + " WHERE " + COLUMN_SCHEDULE_DATE_TIME + " BETWEEN " + dayStart + " AND " + dayFinish
                + " ORDER BY "+ COLUMN_SCHEDULE_DATE_TIME;


        List<Schedule> scheduleLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Schedule schedule;

        if (cursor.moveToFirst()) {
            do {
                schedule = new Schedule();

                schedule.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                schedule.setDate_Time(cursor.getInt(cursor.getColumnIndex(COLUMN_SCHEDULE_DATE_TIME)));
                schedule.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TASK)));
                schedule.setCheck_time(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_CHECK_TIME)));
                schedule.setNotific(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_NOTIFIC)));
                schedule.setNotific_time(cursor.getInt(cursor.getColumnIndex(COLUMN_SCHEDULE_NOTIFIC_TIME)));
                scheduleLinkedList.add(schedule);
            } while (cursor.moveToNext());
        }
        return scheduleLinkedList;
    }


    public Schedule getOneSchedule(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_SCHEDULE + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Schedule receivedSchedule = new Schedule();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedSchedule.setDate_Time(cursor.getInt(cursor.getColumnIndex(COLUMN_SCHEDULE_DATE_TIME)));
            receivedSchedule.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TASK)));
            receivedSchedule.setCheck_time(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_CHECK_TIME)));
            receivedSchedule.setNotific(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_NOTIFIC)));
            receivedSchedule.setNotific_time(cursor.getInt(cursor.getColumnIndex(COLUMN_SCHEDULE_NOTIFIC_TIME)));
        }
        return receivedSchedule;
    }

    public void deleteScheduleRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_SCHEDULE+" WHERE _id='"+id+"'");
    }

    /**update record**/
    public void updateScheduleRecord(long scheduleId, Context context, Schedule updatedSchedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_SCHEDULE+" SET date_time ='"+ updatedSchedule.getDate_Time() + "', task ='"
                + updatedSchedule.getTask() + "', check_time ='" + updatedSchedule.getCheck_time()
                + "', notific='" + updatedSchedule.getNotific() + "', notific_time='" + updatedSchedule.getNotific_time()
                + "'  WHERE _id='" + scheduleId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }


    public void saveNewColleag(Colleagues colleagues) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLLEAGUES_FHOTO, colleagues.getImage_uri());
        values.put(COLUMN_COLLEAGUES_NAME, colleagues.getName_colleag());
        values.put(COLUMN_COLLEAGUES_NAMBER, colleagues.getNumber_colleag());
        values.put(COLUMN_COLLEAGUES_SKYPE, colleagues.getSkype_colleag());
        values.put(COLUMN_COLLEAGUES_COMMIT, colleagues.getCommit_colleag());

        // insert
        db.insert(TABLE_NAME_COLLEAGUES,null, values);
        db.close();

    }

    public Colleagues getOneColleag(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_COLLEAGUES + " WHERE _id_colleag=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Colleagues rvColleagues = new Colleagues();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            rvColleagues.setImage_uri(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_FHOTO)));
            rvColleagues.setName_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_NAME)));
            rvColleagues.setNumber_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_NAMBER)));
            rvColleagues.setSkype_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_SKYPE)));
            rvColleagues.setCommit_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_COMMIT)));
        }
        return rvColleagues;
    }

    public void deleteColleag(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_COLLEAGUES+" WHERE _id_colleag='"+id+"'");
    }

    /**update record**/
    public void updateColleag(long ColleagId, Context context, Colleagues updatedColleag) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_COLLEAGUES+" SET colleag_foto ='"+ updatedColleag.getImage_uri() + "', colleag_name ='"
                + updatedColleag.getName_colleag() + "', colleag_number ='" + updatedColleag.getNumber_colleag() +
                "', colleag_skype ='" + updatedColleag.getSkype_colleag() + "', colleag_commit ='" +
                updatedColleag.getCommit_colleag() + "'  WHERE _id_colleag ='" + ColleagId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }



    public void saveNewClient(Client client) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_FHOTO, client.getImage_path_client());
        values.put(COLUMN_CLIENT_CATEGORY, client.getType_client());
        values.put(COLUMN_CLIENT_CATEGORY_POS, client.getType_pos_client());
        values.put(COLUMN_CLIENT_COMPANY, client.getCompany_client());
        values.put(COLUMN_CLIENT_NAME, client.getName_client());
        values.put(COLUMN_CLIENT_NUMBER, client.getNumber_client());
        values.put(COLUMN_CLIENT_SKYPE, client.getSkype_client());
        values.put(COLUMN_CLIENT_COMMIT, client.getCommit_client());
        values.put(COLUMN_CLIENT_PROJECT, client.getProject_client());

        // insert
        db.insert(TABLE_NAME_CLIENT,null, values);
        db.close();

    }

    public Client getOneClient(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_CLIENT + " WHERE _id_client=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Client rvClient = new Client();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            rvClient.setImage_path_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_FHOTO)));
            rvClient.setType_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_CATEGORY)));
            rvClient.setType_pos_client(cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_CATEGORY_POS)));
            rvClient.setCompany_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_COMPANY)));
            rvClient.setName_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME)));
            rvClient.setNumber_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NUMBER)));
            rvClient.setSkype_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_SKYPE)));
            rvClient.setCommit_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_COMMIT)));
            rvClient.setProject_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PROJECT)));
        }
        return rvClient;
    }

    public void deleteClient(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_CLIENT+" WHERE _id_client='"+id+"'");
    }

    /**update record**/
    public void updateClient(long ClientId, Context context, Client updatedClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_CLIENT+" SET image_path_client ='"+ updatedClient.getImage_path_client() + "', type_client ='"
                + updatedClient.getType_client() + "', type_pos_client ='" + updatedClient.getType_pos_client() +
                "', company_client ='" + updatedClient.getCompany_client() + "', name_client ='" +
                updatedClient.getName_client() + "', number_client ='" + updatedClient.getNumber_client() +
                "', skype_client ='" + updatedClient.getSkype_client() +
                "', project_client ='" + updatedClient.getProject_client() +
                "', commit_client ='" + updatedClient.getCommit_client() +
                "'  WHERE _id_client ='" + ClientId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }




    public void saveNewPrject(Project project) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_CATEGORY, project.getType_project());
        values.put(COLUMN_PROJECT_CATEGORY_POS, project.getType_pos_project());
        values.put(COLUMN_PROJECT_NAME, project.getName_project());
        values.put(COLUMN_PROJECT_COMPANY, project.getCompany_project());
        values.put(COLUMN_PROJECT_RESPONSIBLE, project.getResponsible_for_project());
        values.put(COLUMN_PROJECT_FINISH, project.getDate_finish_project());

        // insert
        db.insert(TABLE_NAME_PROJECT,null, values);
        db.close();

    }

    public Project getOneProject(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_PROJECT + " WHERE _id_project=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Project rvProject = new Project();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            rvProject.setType_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY)));
            rvProject.setType_pos_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY_POS)));
            rvProject.setName_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)));
            rvProject.setCompany_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_COMPANY)));
            rvProject.setResponsible_for_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_RESPONSIBLE)));
            rvProject.setDate_finish_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_FINISH)));
        }
        return rvProject;
    }

    public void deleteProject(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_PROJECT+" WHERE _id_project='"+id+"'");
    }

    /**update record**/
    public void updateProject(long ProjectId, Context context, Project updatedProject) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_PROJECT+" SET type_project ='"+ updatedProject.getType_project() + "', type_pos_project ='"
                + updatedProject.getType_pos_project() + "', name_project ='" + updatedProject.getName_project() +
                "', company_project ='" + updatedProject.getCompany_project() + "', responsible_for_project ='" +
                updatedProject.getResponsible_for_project() + "', date_finish_project ='" + updatedProject.getDate_finish_project() +
                "'  WHERE _id_project ='" + ProjectId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }


    public void saveNewCapital(Capital capital) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUM_START_CAPITAL, capital.getSum_start_capital());

        // insert
        db.insert(TABLE_NAME_START_CAPITAL,null, values);
        db.close();

    }

    public Capital getOneCapital(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_START_CAPITAL + " WHERE _id_start_capital=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Capital rvCapital = new Capital();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            rvCapital.setSum_start_capital(cursor.getFloat(cursor.getColumnIndex(COLUMN_SUM_START_CAPITAL)));
        }
        return rvCapital;
    }

    public void deleteCapital(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME_START_CAPITAL+" WHERE _id_start_capital='"+id+"'");
    }

    /**update record**/
    public void updateCapital(long CapitalId, Context context, Capital updatedCapital) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME_START_CAPITAL+" SET sum_start_capital ='"+ updatedCapital.getSum_start_capital() +
                "'  WHERE _id_start_capital ='" + CapitalId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }


    public void saveNewNotif(Notific notif) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NOTIF, notif.getName_notific());

        // insert
        db.insert(TABLE_NAME_NOTIF,null, values);
        db.close();

    }

    public Capital getOneNotif(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_NOTIF + " WHERE _id_notific=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Capital rvCapital = new Capital();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            rvCapital.setSum_start_capital(cursor.getFloat(cursor.getColumnIndex(COLUMN_NAME_NOTIF)));
        }
        return rvCapital;
    }





    int componentDateToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }


}
