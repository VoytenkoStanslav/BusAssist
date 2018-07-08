package by.app.admin.busassist.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import by.app.admin.busassist.Adapters.ClientAdapter;
import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentClient extends Fragment {

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
    private ClientAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client, container, false);

        rvClient = (RecyclerView) v.findViewById(R.id.rvClient);
        rvClient.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        rvClient.setHasFixedSize(true);
        LMClient = new LinearLayoutManager(getActivity());
        rvClient.setLayoutManager(LMClient);

        clientList();

        return v;
    }

    public void clientList() {

        dbHelper = new DBHelper(getActivity());

        List<Client> clientLinkedList = new LinkedList<>();
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
        adapter = new ClientAdapter(clientLinkedList, getActivity(), rvClient);
        rvClient.setAdapter(adapter);
        dbHelper.close();

    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
