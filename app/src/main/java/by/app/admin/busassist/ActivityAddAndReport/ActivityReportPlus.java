package by.app.admin.busassist.ActivityAddAndReport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import by.app.admin.busassist.Adapters.BalancePlusAdapter;
import by.app.admin.busassist.ConstClass.BalancePlus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityReportPlus extends AppCompatActivity implements View.OnClickListener{

    public static final String TABLE_NAME_PLUS = "balance_plus";
    public static final String COLUMN_ID_PLUS = "_id_plus";
    public static final String COLUMN_DATE_PLUS = "date_plus";
    public static final String COLUMN_CATEGORY_PLUS = "category_plus";
    public static final String COLUMN_AMOUNT_PLUS = "amount_plus";
    public static final String COLUMN_COMMIT_PLUS = "commit_plus";

    TextView edDateOtRepPl;
    TextView edDateDoRepPl;
    Button btnBackPlus;

    private RecyclerView rvBalancePlus;
    private RecyclerView.LayoutManager LMBalancePlus;
    private DBHelper dbHelper;
    private BalancePlusAdapter adapter;

    int dateStartSearch = 0;
    int dateFinishSearch = 2147397247;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_plus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edDateOtRepPl = (TextView) findViewById(R.id.edDateOtRepPl);
        edDateOtRepPl.setPaintFlags(edDateOtRepPl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateOtRepPl.setOnClickListener(this);
        edDateDoRepPl = (TextView) findViewById(R.id.edDateDoRepPl);
        edDateDoRepPl.setPaintFlags(edDateDoRepPl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateDoRepPl.setOnClickListener(this);

        rvBalancePlus = (RecyclerView) findViewById(R.id.rvCostsPlus);
        rvBalancePlus.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvBalancePlus.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvBalancePlus.setHasFixedSize(true);
        LMBalancePlus = new LinearLayoutManager(this);
        rvBalancePlus.setLayoutManager(LMBalancePlus);

        balancePlusList(dateStartSearch, dateFinishSearch);

    }

    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (id == 1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackOt, year, month, day);
            return tpd;
        }
        if (id == 2) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDo, year, month, day);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBackOt = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Integer d = componentDateStamp(year, month, day); // - 43200;
            dateStartSearch = d;
            String date = sdf.format(new Date((d)*1000L));
            edDateOtRepPl.setText(date);
            balancePlusList(dateStartSearch, dateFinishSearch);

        }
    };

    DatePickerDialog.OnDateSetListener myCallBackDo = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Integer d = componentDateStamp(year, month, day); // - 43200;
            dateFinishSearch = d;
            String date = sdf.format(new Date((d)*1000L));
            edDateDoRepPl.setText(date);
            balancePlusList(dateStartSearch, dateFinishSearch);
        }
    };

    public void balancePlusList(int dateStartSearch, int dateFinishSearch) {

        dbHelper = new DBHelper(this);

        List<BalancePlus> balancePlusList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PLUS, null, COLUMN_DATE_PLUS + " BETWEEN " + dateStartSearch + " AND " + dateFinishSearch,
                null ,null, null, COLUMN_DATE_PLUS);
        BalancePlus balance;

        if (cursor.moveToFirst()) {
            do {
                balance = new BalancePlus();

                balance.setId_plus(cursor.getLong(cursor.getColumnIndex(COLUMN_ID_PLUS)));
                balance.setDate_plus(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_PLUS)));
                balance.setCategory_plus(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_PLUS)));
                balance.setCommit_plus(cursor.getString(cursor.getColumnIndex(COLUMN_COMMIT_PLUS)));
                balance.setAmount_plus(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS)));
                balancePlusList.add(balance);
            } while (cursor.moveToNext());
        }
        adapter = new BalancePlusAdapter(balancePlusList, this, rvBalancePlus);
        rvBalancePlus.setAdapter(adapter);
        dbHelper.close();

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edDateOtRepPl:
                showDialog(1);
                break;
            case R.id.edDateDoRepPl:
                showDialog(2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goToAdd = new Intent(this, MainActivity.class);
                goToAdd.putExtra("SIGNAL_BACK", 444);
                startActivity(goToAdd);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    int componentDateStamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);

    }

    @Override
    public void onBackPressed() {
    }

}
