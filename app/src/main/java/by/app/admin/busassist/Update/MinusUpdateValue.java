package by.app.admin.busassist.Update;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.ActivityAddAndReport.ActivityReportMinus;
import by.app.admin.busassist.ConstClass.BalanceMinus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

import static java.lang.String.valueOf;

/**
 * Created by Admin on 23.04.2018.
 */

public class MinusUpdateValue extends AppCompatActivity {

    TextView tvDateMinus;
    Spinner spinCategory;
    EditText edAmount;
    EditText edCommit;

    Integer d;

    Button btnOk;
    private DBHelper dbHelper;
    private long receivedMinusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_minus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvDateMinus = (TextView) findViewById(R.id.tvDateMinus);
        tvDateMinus.setPaintFlags(tvDateMinus.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDateMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });


        spinCategory = (Spinner) findViewById(R.id.spinnerMinus);


        edAmount = (EditText) findViewById(R.id.editAmountMinus);
        edAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                int p = str.indexOf(".");
                if (p != -1) {
                    String tmpStr = str.substring(p);
                    if (tmpStr.length() == 4) {
                        s.delete(s.length()-1, s.length());
                    }
                }
            }
        });


        edCommit = (EditText) findViewById(R.id.editCommentMinus);

        dbHelper = new DBHelper(this);

        try {
            //get intent to get person id
            receivedMinusId = getIntent().getLongExtra("BALANCE_MINUS_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BalanceMinus queriedMinus = dbHelper.getOneBalanceMinus(receivedMinusId);
        String date = new SimpleDateFormat("dd MMM yyyy").format(new Date(queriedMinus.getDate_minus()*1000L));
        tvDateMinus.setText(date);
        int pos = queriedMinus.getCategory_pos_minus();
        spinCategory.setSelection(pos);
        edAmount.setText(String.valueOf(queriedMinus.getAmount_minus()));
        edCommit.setText(valueOf(queriedMinus.getCommit_minus()));
        d = queriedMinus.getDate_minus();

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMinus();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateMinus(){

        Integer date_minus = d;

        String spin_minus = spinCategory.getSelectedItem().toString();

        Integer spin_pos_minus = spinCategory.getSelectedItemPosition();

        Float amount_minus = Float.parseFloat(edAmount.getText().toString());

        String commit = edCommit.getText().toString();

        String date_str = tvDateMinus.getText().toString();

        dbHelper = new DBHelper(this);

        if(!date_str.equals("Укажите дату")){
            //error name is empty

            if(!spin_minus.equals("Не выбрано")){

                if(amount_minus != 0){
                    BalanceMinus balanceMinus = new BalanceMinus(date_minus, spin_minus, spin_pos_minus, amount_minus, commit);
                    dbHelper.updateBalanceMinus(receivedMinusId, this, balanceMinus);
                    goBackHome();
                } else {
                    Snackbar.make(btnOk, "Введите сумму", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOk, "Выберите категорию", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }else {
            Snackbar.make(btnOk, "Выберите дату", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    private void goBackHome(){
        Intent goToAdd = new Intent(this, ActivityReportMinus.class);
        startActivity(goToAdd);
    }


    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (id == 1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, year, month, day);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            d = componentDateToTimestampDate(year, month, day);
            String date = sdf.format(new Date((d)*1000L));
            tvDateMinus.setText(date);
        }
    };

    int componentDateToTimestampDate(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    @Override
    public void onBackPressed() {
    }

}
