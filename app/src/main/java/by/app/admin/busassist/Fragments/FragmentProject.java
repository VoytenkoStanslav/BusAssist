package by.app.admin.busassist.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;

import by.app.admin.busassist.Adapters.ProjectAdapter;
import by.app.admin.busassist.ConstClass.Project;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 02.05.2018.
 */

public class FragmentProject extends Fragment {


    public static final String TABLE_NAME_PROJECT = "project";
    public static final String COLUMN_PROJECT_ID = "_id_project";
    public static final String COLUMN_PROJECT_CATEGORY = "type_project";
    public static final String COLUMN_PROJECT_CATEGORY_POS = "type_pos_project";
    public static final String COLUMN_PROJECT_NAME = "name_project";
    public static final String COLUMN_PROJECT_COMPANY = "company_project";
    public static final String COLUMN_PROJECT_RESPONSIBLE = "responsible_for_project";
    public static final String COLUMN_PROJECT_FINISH = "date_finish_project";

    private RecyclerView rvProject;
    private RecyclerView.LayoutManager LMProject;
    private DBHelper dbHelper;
    private ProjectAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_project, container, false);
        rvProject = (RecyclerView) v.findViewById(R.id.rvProject);
        rvProject.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        rvProject.setHasFixedSize(true);
        LMProject = new LinearLayoutManager(getActivity());
        rvProject.setLayoutManager(LMProject);
        projectsList();
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CategoryListProjectForFrag, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    projectsFilterList(pos);
                } else {
                    projectsList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner.setAdapter(adapter);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void projectsList() {

        dbHelper = new DBHelper(getActivity());

        List<Project> projectsLinkedList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query;
        query = "SELECT  * FROM " + TABLE_NAME_PROJECT + " ORDER BY "+ COLUMN_PROJECT_FINISH;
        Cursor cursor = db.rawQuery(query, null);
        Project project;

        if (cursor.moveToFirst()) {
            do {
                project = new Project();

                project.setId_project(cursor.getLong(cursor.getColumnIndex(COLUMN_PROJECT_ID)));
                project.setType_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY)));
                project.setType_pos_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY_POS)));
                project.setName_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)));
                project.setCompany_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_COMPANY)));
                project.setResponsible_for_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_RESPONSIBLE)));
                project.setDate_finish_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_FINISH)));
                projectsLinkedList.add(project);
            } while (cursor.moveToNext());
        }
        adapter = new ProjectAdapter(projectsLinkedList, getActivity(), rvProject);
        rvProject.setAdapter(adapter);
        dbHelper.close();

    }

    public void projectsFilterList(Integer filter) {

        dbHelper = new DBHelper(getActivity());

        List<Project> projectsLinkedList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query;
        query = "SELECT  * FROM " + TABLE_NAME_PROJECT + " WHERE type_pos_project =" + filter +
                " ORDER BY "+ COLUMN_PROJECT_FINISH;
        Cursor cursor = db.rawQuery(query, null);
        Project project;

        if (cursor.moveToFirst()) {
            do {
                project = new Project();

                project.setId_project(cursor.getLong(cursor.getColumnIndex(COLUMN_PROJECT_ID)));
                project.setType_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY)));
                project.setType_pos_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_CATEGORY_POS)));
                project.setName_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)));
                project.setCompany_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_COMPANY)));
                project.setResponsible_for_project(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_RESPONSIBLE)));
                project.setDate_finish_project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_FINISH)));
                projectsLinkedList.add(project);
            } while (cursor.moveToNext());
        }
        adapter = new ProjectAdapter(projectsLinkedList, getActivity(), rvProject);
        rvProject.setAdapter(adapter);
        dbHelper.close();

    }

}
