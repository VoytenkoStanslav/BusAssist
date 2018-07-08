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

import by.app.admin.busassist.Adapters.ClientForProjectAdapter;
import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 02.05.2018.
 */

public class ActivityAddClientForProject extends AppCompatActivity {

    public ActivityAddClientForProject(){

    }


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

    private RecyclerView rvClient;
    private RecyclerView.LayoutManager LMClient;
    private DBHelper dbHelper;
    private ClientForProjectAdapter adapter;
    private long receivedColleaguesIdAboutProject;
    private int receivedPosProj;
    private int receivedTimeProj;
    private long receivedProjectForUpdate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            //get intent to get person id
            receivedColleaguesIdAboutProject = getIntent().getLongExtra("COLLEAGUES_ID_ABOUT_PROJECT", -2);
            receivedPosProj = getIntent().getIntExtra("PROJECT_SPINNER", -3);
            receivedTimeProj = getIntent().getIntExtra("PROJECT_TIME", -4);
            receivedProjectForUpdate = getIntent().getLongExtra("PROJECT_FOR_UPDATE", -5);
            Log.e("LooogUp", String.valueOf(receivedColleaguesIdAboutProject));
        } catch (Exception e) {
            e.printStackTrace();
        }


        rvClient = (RecyclerView) findViewById(R.id.rvClient);
        rvClient.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvClient.setHasFixedSize(true);
        LMClient = new LinearLayoutManager(this);
        rvClient.setLayoutManager(LMClient);
        clientList();

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

    public void clientList() {

        dbHelper = new DBHelper(this);

        final List<Client> clientLinkedList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query;
        query = "SELECT  * FROM " + TABLE_NAME_CLIENT + " ORDER BY "+ COLUMN_CLIENT_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Client client;

        if (cursor.moveToFirst()) {
            do {
                client = new Client();

                client.setId_client(cursor.getLong(cursor.getColumnIndex(COLUMN_CLIENT_ID)));
                client.setImage_path_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_FHOTO)));
                client.setType_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_CATEGORY)));
                client.setType_pos_client(cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_CATEGORY_POS)));
                client.setCompany_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_COMPANY)));
                client.setName_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME)));
                client.setNumber_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NUMBER)));
                client.setSkype_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_SKYPE)));
                client.setProject_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PROJECT)));
                client.setCommit_client(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_COMMIT)));
                clientLinkedList.add(client);
            } while (cursor.moveToNext());
        }
        adapter = new ClientForProjectAdapter(clientLinkedList, this, rvClient,
                receivedColleaguesIdAboutProject, receivedPosProj, receivedTimeProj,
                receivedProjectForUpdate);
        rvClient.setAdapter(adapter);
        dbHelper.close();

    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void goBack() {
        Intent goToUpdate = new Intent(this, ActivityAddProject.class);
        goToUpdate.putExtra("COLLEAGUES_ID_PROJECT", receivedColleaguesIdAboutProject);
        goToUpdate.putExtra("POS_PROJECT", receivedPosProj);
        goToUpdate.putExtra("TIME_PROJECT", receivedTimeProj);
        goToUpdate.putExtra("PROJECT_ID_FOR_UPDATE", receivedProjectForUpdate);
        this.startActivity(goToUpdate);
    }

    @Override
    public void onBackPressed() {
    }

}
