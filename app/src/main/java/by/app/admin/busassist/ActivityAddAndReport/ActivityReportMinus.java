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

import by.app.admin.busassist.Adapters.BalanceMinusAdapter;
import by.app.admin.busassist.ConstClass.BalanceMinus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityReportMinus extends AppCompatActivity implements View.OnClickListener{

    public static final String TABLE_NAME_MINUS = "balance_minus";
    public static final String COLUMN_ID_MINUS = "_id_minus";
    public static final String COLUMN_DATE_MINUS = "date_minus";
    public static final String COLUMN_CATEGORY_MINUS = "category_minus";
    public static final String COLUMN_AMOUNT_MINUS = "amount_minus";
    public static final String COLUMN_COMMIT_MINUS = "commit_minus";

    TextView edDateOtRepMin;
    TextView edDateDoRepMin;
    Button btnBackMinus;

    private RecyclerView rvBalanceMinus;
    private RecyclerView.LayoutManager LMBalanceMinus;
    private DBHelper dbHelper;
    private BalanceMinusAdapter adapter;

    int dateStartSearch = 0;
    int dateFinishSearch = 2147397247;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_minus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edDateOtRepMin = (TextView) findViewById(R.id.edDateOtRepMin);
        edDateOtRepMin.setPaintFlags(edDateOtRepMin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateOtRepMin.setOnClickListener(this);
        edDateDoRepMin = (TextView) findViewById(R.id.edDateDoRepMin);
        edDateDoRepMin.setPaintFlags(edDateDoRepMin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edDateDoRepMin.setOnClickListener(this);
        btnBackMinus = (Button) findViewById(R.id.btnBackMinus);

        rvBalanceMinus = (RecyclerView) findViewById(R.id.rvCostsMinus);
        rvBalanceMinus.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvBalanceMinus.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvBalanceMinus.setHasFixedSize(true);
        LMBalanceMinus = new LinearLayoutManager(this);
        rvBalanceMinus.setLayoutManager(LMBalanceMinus);

        balanceMinusList(dateStartSearch, dateFinishSearch);

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
            edDateOtRepMin.setText(year + "." + (month+1) + "." + day);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Integer d = componentDateStamp(year, month, day); // - 43200;
            dateStartSearch = d;
            String date = sdf.format(new Date((d)*1000L));
            edDateOtRepMin.setText(date);
            balanceMinusList(dateStartSearch, dateFinishSearch);

        }
    };

    DatePickerDialog.OnDateSetListener myCallBackDo = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            //edDateDoRepMin.setText(year + "." + (month+1) + "." + day);
            edDateDoRepMin.setText(year + "." + (month+1) + "." + day);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Integer d = componentDateStamp(year, month, day); // - 43200;
            dateFinishSearch = d;
            String date = sdf.format(new Date((d)*1000L));
            edDateDoRepMin.setText(date);
            balanceMinusList(dateStartSearch, dateFinishSearch);
        }
    };

    public void balanceMinusList(int dateStartSearch, int dateFinishSearch) {

        dbHelper = new DBHelper(this);

        List<BalanceMinus> balanceMinusList = new LinkedList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_MINUS, null, COLUMN_DATE_MINUS + " BETWEEN " + dateStartSearch + " AND " + (dateFinishSearch),
                null ,null, null, COLUMN_DATE_MINUS);
        BalanceMinus balance;

        if (cursor.moveToFirst()) {
            do {
                balance = new BalanceMinus();

                balance.setId_minus(cursor.getLong(cursor.getColumnIndex(COLUMN_ID_MINUS)));
                balance.setDate_minus(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_MINUS)));
                balance.setCategory_minus(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_MINUS)));
                balance.setCommit_minus(cursor.getString(cursor.getColumnIndex(COLUMN_COMMIT_MINUS)));
                balance.setAmount_minus(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_MINUS)));
                balanceMinusList.add(balance);
            } while (cursor.moveToNext());
        }
        adapter = new BalanceMinusAdapter(balanceMinusList, this, rvBalanceMinus);
        rvBalanceMinus.setAdapter(adapter);
        dbHelper.close();

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnBackMinus:
                onStop();
                break;
            case R.id.edDateOtRepMin:
                showDialog(1);
                break;
            case R.id.edDateDoRepMin:
                showDialog(2);
                break;
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
