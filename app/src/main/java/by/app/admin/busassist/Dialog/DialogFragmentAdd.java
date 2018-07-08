package by.app.admin.busassist.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.app.admin.busassist.ActivityAddAndReport.ActivityAddColleagues;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddMinus;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddClient;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddPlus;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddProject;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddSchedule;
import by.app.admin.busassist.ActivityAddAndReport.ActivityAddStartCapital;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 06.04.2018.
 */

public class DialogFragmentAdd extends DialogFragment {

    public static final String TABLE_NAME_START_CAPITAL = "start_capital";
    public static final String COLUMN_ID_START_CAPITAL = "_id_start_capital";
    public static final String COLUMN_SUM_START_CAPITAL = "sum_start_capital";

    DBHelper dbHelper;

    Integer d;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Добавление");
        View v = inflater.inflate(R.layout.dialog_add, null);


        d = StartCapital();


        v.findViewById(R.id.tvAddSched).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddSchedule.class);
                startActivity(intent);
            }
        });

        if (d == 0) {
            v.findViewById(R.id.tvAddPlus).setVisibility(View.GONE);
            v.findViewById(R.id.tvAddMinus).setVisibility(View.GONE);
            v.findViewById(R.id.tvAddStartCapital).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ActivityAddStartCapital.class);
                    startActivity(intent);
                }
            });
        } else {
            v.findViewById(R.id.tvAddStartCapital).setVisibility(View.GONE);
        }

        v.findViewById(R.id.tvAddMinus).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddMinus.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.tvAddPlus).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddPlus.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.tvAddColleag).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddColleagues.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.tvAddPartner).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddClient.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.tvAddProject).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), ActivityAddProject.class);
                startActivity(intent);
            }
        });

        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
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
