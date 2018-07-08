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

import by.app.admin.busassist.ConstClass.BalancePlus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityAddPlus extends AppCompatActivity{

    TextView tvDate;
    Spinner spinCategory;
    EditText edAmount;
    EditText edCommit;

    Integer d;

    Button btnOkPlus;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvDate = (TextView) findViewById(R.id.tvDatePlus);
        tvDate.setPaintFlags(tvDate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });


        spinCategory = (Spinner) findViewById(R.id.spinnerPlus);


        edAmount = (EditText) findViewById(R.id.editAmountPlus);
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


        edCommit = (EditText) findViewById(R.id.editCommentPlus);

        btnOkPlus = (Button) findViewById(R.id.btnOkPlus);
        btnOkPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlus();
            }
        });
    }

    private void savePlus(){
        Integer date_plus = d;

        String spin_plus = spinCategory.getSelectedItem().toString();

        Integer spin_pos_plus = spinCategory.getSelectedItemPosition();

        String amount_str = edAmount.getText().toString();

        Float amount_plus = Float.parseFloat(edAmount.getText().toString());

        String commit_plus = edCommit.getText().toString();

        String date_str = tvDate.getText().toString();

        dbHelper = new DBHelper(this);

        if(!date_str.equals("Укажите дату")){
            //error name is empty

            if(spin_pos_plus != 0){

                if(!amount_str.isEmpty()){
                    BalancePlus balance = new BalancePlus(date_plus, spin_plus, spin_pos_plus, amount_plus, commit_plus);
                    dbHelper.saveNewBalancePlus(balance);
                    goBackHome();
                } else {
                    Snackbar.make(btnOkPlus, "Введите сумму", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOkPlus, "Выберите категорию", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }else {
            Snackbar.make(btnOkPlus, "Выберите дату", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

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

    private void goBackHome(){
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 444);
        startActivity(goToAdd);
    }


    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (id == 1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, year, month, day);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            d = componentDateToTimestampDate(year, month, day);
            String date = sdf.format(new Date((d)*1000L));
            tvDate.setText(date);
        }
    };

    int componentDateToTimestampDate(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    @Override
    public void onBackPressed() {
    }
}
