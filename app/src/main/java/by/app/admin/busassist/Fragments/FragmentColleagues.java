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

import by.app.admin.busassist.Adapters.ColleaguesAdapter;
import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 29.03.2018.
 */

public class FragmentColleagues extends Fragment {

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
    private ColleaguesAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_colleagues, container, false);

        rvColleag = (RecyclerView) v.findViewById(R.id.rvColleagues);
        rvColleag.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        rvColleag.setHasFixedSize(true);
        LMColleag = new LinearLayoutManager(getActivity());
        rvColleag.setLayoutManager(LMColleag);

        colleaguesList();

        return v;
    }

    public void colleaguesList() {

        dbHelper = new DBHelper(getActivity());

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
        adapter = new ColleaguesAdapter(colleaguesLinkedList, getActivity(), rvColleag);
        rvColleag.setAdapter(adapter);
        dbHelper.close();

    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
