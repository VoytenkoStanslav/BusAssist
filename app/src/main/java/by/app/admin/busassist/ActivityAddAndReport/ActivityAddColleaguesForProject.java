package by.app.admin.busassist.ActivityAddAndReport;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

import by.app.admin.busassist.Adapters.ColleaguesForProjectAdapter;
import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 05.05.2018.
 */

public class ActivityAddColleaguesForProject extends AppCompatActivity {

    public static final String TABLE_NAME_COLLEAGUES = "colleagues";
    public static final String COLUMN_COLLEAGUES_ID = "_id_colleag";
    public static final String COLUMN_COLLEAGUES_FHOTO = "colleag_foto";
    public static final String COLUMN_COLLEAGUES_NAME = "colleag_name";
    public static final String COLUMN_COLLEAGUES_NAMBER = "colleag_number";
    public static final String COLUMN_COLLEAGUES_SKYPE = "colleag_skype";
    public static final String COLUMN_COLLEAGUES_COMMIT = "colleag_commit";

    private RecyclerView rvColleag;
    private RecyclerView.LayoutManager LMColleag;
    private DBHelper dbHelper;
    private ColleaguesForProjectAdapter adapter;

    private long receivedClientIdAboutProject;

    private int receivedPosProj;

    private int receivedTimeProj;

    private String receivedMyProject;

    private long receivedProjectForUpdate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_colleagues);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            //get intent to get person id
            receivedClientIdAboutProject = getIntent().getLongExtra("CLIENT_ID_ABOUT_PROJECT", -1);
            receivedPosProj = getIntent().getIntExtra("PROJECT_SPINNER", -3);
            receivedTimeProj = getIntent().getIntExtra("PROJECT_TIME", -4);
            receivedMyProject = getIntent().getStringExtra("PROJECT_MY_NAME");
            receivedProjectForUpdate = getIntent().getLongExtra("PROJECT_FOR_UPDATE", -5);

            Log.e("LooogUp", String.valueOf(receivedClientIdAboutProject));
        } catch (Exception e) {
            e.printStackTrace();
        }

        rvColleag = (RecyclerView) findViewById(R.id.rvColleagues);
        rvColleag.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvColleag.setHasFixedSize(true);
        LMColleag = new LinearLayoutManager(this);
        rvColleag.setLayoutManager(LMColleag);

        colleaguesListForProject();
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

    public void colleaguesListForProject() {

        dbHelper = new DBHelper(this);

        List<Colleagues> colleaguesLinkedList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query;
        query = "SELECT  * FROM " + TABLE_NAME_COLLEAGUES + " ORDER BY "+ COLUMN_COLLEAGUES_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Colleagues colleagues;

        if (cursor.moveToFirst()) {
            do {
                colleagues = new Colleagues();

                colleagues.setId_colleag(cursor.getLong(cursor.getColumnIndex(COLUMN_COLLEAGUES_ID)));
                colleagues.setImage_uri(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_FHOTO)));
                colleagues.setName_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_NAME)));
                colleagues.setNumber_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_NAMBER)));
                colleagues.setSkype_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_SKYPE)));
                colleagues.setCommit_colleag(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEAGUES_COMMIT)));
                colleaguesLinkedList.add(colleagues);
            } while (cursor.moveToNext());
        }
        adapter = new ColleaguesForProjectAdapter(colleaguesLinkedList, this, rvColleag,
                receivedClientIdAboutProject, receivedPosProj, receivedTimeProj, receivedMyProject,
                receivedProjectForUpdate);

        rvColleag.setAdapter(adapter);
        dbHelper.close();

    }

    private void goBack() {
        Intent goToUpdate = new Intent(this, ActivityAddProject.class);
        goToUpdate.putExtra("CLIENT_ID_PROJECT", receivedClientIdAboutProject);
        goToUpdate.putExtra("POS_PROJECT", receivedPosProj);
        goToUpdate.putExtra("TIME_PROJECT", receivedTimeProj);
        goToUpdate.putExtra("NAME_MY_PROJECT", receivedMyProject);
        goToUpdate.putExtra("PROJECT_ID_FOR_UPDATE", receivedProjectForUpdate);
        this.startActivity(goToUpdate);
    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
    }

}
