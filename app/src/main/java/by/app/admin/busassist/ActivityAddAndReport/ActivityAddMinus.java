package by.app.admin.busassist.ActivityAddAndReport;

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

import by.app.admin.busassist.ConstClass.BalanceMinus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityAddMinus extends AppCompatActivity{


    TextView tvDateMinus;
    Spinner spinCategory;
    EditText edAmount;
    EditText edCommit;

    Integer d;

    Button btnOk;
    DBHelper dbHelper;

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

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMinus();
            }
        });
    }

    private void saveMinus(){
        Integer date_minus = d;

        String spin_minus = spinCategory.getSelectedItem().toString();

        Integer spin_pos_minus = spinCategory.getSelectedItemPosition();

        String amount_str = edAmount.getText().toString();

        Float amount_minus = Float.parseFloat(edAmount.getText().toString());

        String commit = edCommit.getText().toString();

        String date_str = tvDateMinus.getText().toString();

        dbHelper = new DBHelper(this);

        if(!date_str.equals("Укажите дату")){
            //error name is empty

            if(spin_pos_minus != 0){

                if(!amount_str.isEmpty()){
                    BalanceMinus balance = new BalanceMinus(date_minus, spin_minus, spin_pos_minus, amount_minus, commit);
                    dbHelper.saveNewBalanceMinus(balance);
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
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 444);
        startActivity(goToAdd);
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
            int hour = c.get(Calendar.HOUR_OF_DAY);
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
