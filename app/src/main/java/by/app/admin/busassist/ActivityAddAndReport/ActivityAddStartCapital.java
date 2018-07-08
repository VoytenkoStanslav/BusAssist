package by.app.admin.busassist.ActivityAddAndReport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import by.app.admin.busassist.ConstClass.Capital;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityAddStartCapital extends AppCompatActivity {

    EditText editStartCapital;

    Button btnAddStartCapital;
    Button btnNotStartCapital;
    Button btnCancelStartCapital;

    DBHelper dbHelper;

    long receivedSCId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_start_capital);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            //get intent to get person id
            receivedSCId = getIntent().getLongExtra("START_CAPITAL", -101);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Доб. стартовый капитал");

        editStartCapital = (EditText) findViewById(R.id.editStartCapital);
        editStartCapital.addTextChangedListener(new TextWatcher() {
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


        btnAddStartCapital = (Button) findViewById(R.id.btnAddStartCapital);
        btnAddStartCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receivedSCId == 1){
                    updateStartCapital();
                }
                saveStartCapital();
            }
        });


        if(receivedSCId == 1){
            dbHelper = new DBHelper(this);
            btnAddStartCapital.setText("Изменить");
            setTitle("Изм. стартовый капитал");
            Capital queriedCapital = dbHelper.getOneCapital(receivedSCId);
            float sum_start_capital = queriedCapital.getSum_start_capital();
            editStartCapital.setText(String.valueOf(sum_start_capital));
        }


        btnNotStartCapital = (Button) findViewById(R.id.btnNotStartCapital);
        btnNotStartCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receivedSCId == 1){
                    updateNotStartCapital();
                }
                saveNotStartCapital();
            }
        });


        btnCancelStartCapital = (Button) findViewById(R.id.btnCancelStartCapital);

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


    private void saveStartCapital(){

        Float sum_start_capital = Float.parseFloat(editStartCapital.getText().toString());

        String text = editStartCapital.getText().toString();

        dbHelper = new DBHelper(this);

        if(!text.isEmpty()){
            //error name is empty

            Capital capital = new Capital(sum_start_capital);
            dbHelper.saveNewCapital(capital);
            goBackHome();
        }else {
            Snackbar.make(btnAddStartCapital, "Введите сумму", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    private void updateStartCapital(){

        float sum_start_capital = Float.parseFloat(editStartCapital.getText().toString());

        String text = editStartCapital.getText().toString();

        dbHelper = new DBHelper(this);

        if(!text.isEmpty()){
            //error name is empty

            Capital capital = new Capital(sum_start_capital);
            dbHelper.updateCapital(receivedSCId, this, capital);
            goBackHome();
        }else {
            Snackbar.make(btnAddStartCapital, "Введите сумму", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    private void saveNotStartCapital(){

        Float sum_start_capital = Float.valueOf(0);

        dbHelper = new DBHelper(this);

        Capital capital = new Capital(sum_start_capital);
        dbHelper.saveNewCapital(capital);
        goBackHome();


    }

    private void updateNotStartCapital(){

        Float sum_start_capital = Float.valueOf(0);

        dbHelper = new DBHelper(this);

        Capital capital = new Capital(sum_start_capital);
        dbHelper.updateCapital(receivedSCId, this, capital);
        goBackHome();


    }

    private void goBackHome(){
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 444);
        startActivity(goToAdd);
    }

    @Override
    public void onBackPressed() {
    }

}
