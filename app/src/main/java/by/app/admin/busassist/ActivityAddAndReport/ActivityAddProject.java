package by.app.admin.busassist.ActivityAddAndReport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.ConstClass.Project;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityAddProject extends AppCompatActivity {


    Spinner spinnerProject;
    EditText editNameProject;
    EditText editCompanyProject;
    EditText editResponsibleProject;
    EditText editNameMyProject;
    TextView editFinishProject;

    View viewPr1;
    View viewPr2;
    View viewPr3;

    TextView tvNameProject;
    TextView tvCompanyProject;
    TextView tvResponsibleProject;
    TextView tvNameMyProject;
    TextView tvFinishProject;

    Button btnAddCompany;
    Button btnAddResponsible;
    Button btnOkProject;
    Button btnClearCompany;
    Button btnClearResp;

    DBHelper dbHelper;

    private long receivedClientId;

    private long receivedColleaguesId;

    private int receivedPos;

    private int receivedTime;

    private String receivedMyProject;

    private long receivedProjectForUpdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        try {
            //get intent to get person id

            receivedClientId = getIntent().getLongExtra("CLIENT_ID_PROJECT", -1);
            receivedColleaguesId = getIntent().getLongExtra("COLLEAGUES_ID_PROJECT", -2);
            receivedPos = getIntent().getIntExtra("POS_PROJECT", -3);
            receivedTime = getIntent().getIntExtra("TIME_PROJECT", -4);
            receivedProjectForUpdate = getIntent().getLongExtra("PROJECT_ID_FOR_UPDATE", -5);
            receivedMyProject = getIntent().getStringExtra("NAME_MY_PROJECT");

            Log.e("LooogUp", String.valueOf(receivedProjectForUpdate));
        } catch (Exception e) {
            e.printStackTrace();
        }


        editNameProject = (EditText) findViewById(R.id.editNameProject);
        editCompanyProject = (EditText) findViewById(R.id.editCompanyProject);
        editResponsibleProject = (EditText) findViewById(R.id.editResponsibleProject);
        editFinishProject = (TextView) findViewById(R.id.tvDateFinishProject);
        editFinishProject.setPaintFlags(editFinishProject.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        editFinishProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        editNameMyProject = (EditText) findViewById(R.id.editNameMyProject);

        tvNameProject = (TextView) findViewById(R.id.tvNameProject);
        tvCompanyProject = (TextView) findViewById(R.id.tvCompanyProject);
        tvResponsibleProject = (TextView) findViewById(R.id.tvResponsibleProject);
        tvFinishProject = (TextView) findViewById(R.id.tvDateProject);
        tvNameMyProject = (TextView) findViewById(R.id.tvNameMyProject);

        btnAddCompany = (Button) findViewById(R.id.btnEditCompany);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClient(receivedColleaguesId);
            }
        });
        btnAddResponsible = (Button) findViewById(R.id.btnEditCollegues);
        btnAddResponsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddResponsible(receivedClientId);
            }
        });
        btnOkProject = (Button) findViewById(R.id.btnOkProject);
        btnOkProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (receivedProjectForUpdate != -5){
                    updateProject();
                } else{
                    saveProject();
                }
            }
        });
        btnClearCompany = (Button) findViewById(R.id.btnClearCompany);
        btnClearCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCompanyProject.setText("");
                editNameProject.setText("");
            }
        });
        btnClearResp = (Button) findViewById(R.id.btnClearResponsible);
        btnClearResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editResponsibleProject.setText("");
            }
        });

        viewPr1 = (View) findViewById(R.id.viewProject1);
        viewPr2 = (View) findViewById(R.id.viewProject2);
        viewPr3 = (View) findViewById(R.id.viewProject3);


        spinnerProject = (Spinner) findViewById(R.id.spinnerProject);


        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //editNameProject.setText("");
                //editCompanyProject.setText("");
                //editResponsibleProject.setText("");
                if (position == 1) {
                    tvNameMyProject.setVisibility(View.GONE);
                    editNameMyProject.setVisibility(View.GONE);
                    tvNameProject.setVisibility(View.VISIBLE);
                    editNameProject.setVisibility(View.VISIBLE);
                    //editNameProject.setText("");
                    editNameProject.setEnabled(false);
                    tvCompanyProject.setVisibility(View.VISIBLE);
                    editCompanyProject.setVisibility(View.VISIBLE);
                    //editCompanyProject.setText("");
                    editCompanyProject.setEnabled(false);
                    tvResponsibleProject.setVisibility(View.VISIBLE);
                    editResponsibleProject.setVisibility(View.VISIBLE);
                    //editResponsibleProject.setText("");
                    editResponsibleProject.setEnabled(false);
                    tvFinishProject.setVisibility(View.VISIBLE);
                    editFinishProject.setVisibility(View.VISIBLE);
                    btnAddCompany.setVisibility(View.VISIBLE);
                    btnAddResponsible.setVisibility(View.VISIBLE);
                    viewPr1.setVisibility(View.VISIBLE);
                    viewPr2.setVisibility(View.VISIBLE);
                    viewPr3.setVisibility(View.VISIBLE);
                    btnOkProject.setVisibility(View.VISIBLE);
                }
                if (position == 2){
                    tvNameProject.setVisibility(View.GONE);
                    editNameProject.setVisibility(View.GONE);
                    tvNameMyProject.setVisibility(View.VISIBLE);
                    editNameMyProject.setVisibility(View.VISIBLE);
                    editNameMyProject.setEnabled(true);
                    tvCompanyProject.setVisibility(View.GONE);
                    editCompanyProject.setVisibility(View.GONE);
                    //editCompanyProject.setText("");
                    tvResponsibleProject.setVisibility(View.VISIBLE);
                    editResponsibleProject.setVisibility(View.VISIBLE);
                    //editResponsibleProject.setText("");
                    editResponsibleProject.setEnabled(false);
                    tvFinishProject.setVisibility(View.VISIBLE);
                    editFinishProject.setVisibility(View.VISIBLE);
                    btnAddCompany.setVisibility(View.GONE);
                    btnClearCompany.setVisibility(View.GONE);
                    btnAddResponsible.setVisibility(View.VISIBLE);
                    viewPr1.setVisibility(View.VISIBLE);
                    viewPr2.setVisibility(View.VISIBLE);
                    viewPr3.setVisibility(View.VISIBLE);
                    btnOkProject.setVisibility(View.VISIBLE);
                }
                if (position == 0){
                    tvNameProject.setVisibility(View.GONE);
                    editNameProject.setVisibility(View.GONE);
                    tvNameMyProject.setVisibility(View.GONE);
                    editNameMyProject.setVisibility(View.GONE);
                    tvCompanyProject.setVisibility(View.GONE);
                    editCompanyProject.setVisibility(View.GONE);
                    tvResponsibleProject.setVisibility(View.GONE);
                    editResponsibleProject.setVisibility(View.GONE);
                    tvFinishProject.setVisibility(View.GONE);
                    editFinishProject.setVisibility(View.GONE);
                    btnClearCompany.setVisibility(View.GONE);
                    btnClearResp.setVisibility(View.GONE);
                    btnAddCompany.setVisibility(View.GONE);
                    btnAddResponsible.setVisibility(View.GONE);
                    viewPr1.setVisibility(View.GONE);
                    viewPr2.setVisibility(View.GONE);
                    viewPr3.setVisibility(View.GONE);
                    btnOkProject.setVisibility(View.GONE);

                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbHelper = new DBHelper(this);

        if(receivedClientId != -1) {
            Client queriedClient = dbHelper.getOneClient(receivedClientId);
            String company = queriedClient.getCompany_client();
            if(company.isEmpty()) {
                editCompanyProject.setText(queriedClient.getName_client());
            } else {
                editCompanyProject.setText(queriedClient.getCompany_client());
            }
            editNameProject.setText(queriedClient.getProject_client());
        }

        if(receivedColleaguesId != -2){
            Colleagues queriedColleagues = dbHelper.getOneColleag(receivedColleaguesId);
            editResponsibleProject.setText(queriedColleagues.getName_colleag());
        }

        if(receivedPos == -3){
            spinnerProject.setSelection(0);
        } else{
            spinnerProject.setSelection(receivedPos);
        }

        if(receivedTime != -4){
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            String date = sdf.format(new Date((receivedTime)*1000L));
            editFinishProject.setText(date);
        }
        if(receivedMyProject != null) {
            editNameMyProject.setText(receivedMyProject);
        }

        if(receivedProjectForUpdate != -5) {
            setTitle("Изменение проекта");
        } else {
            setTitle("Добавление проекта");
        }

        if(receivedProjectForUpdate != -5){
            setTitle("Изменение проекта");
            Project queriedProject = dbHelper.getOneProject(receivedProjectForUpdate);
            receivedPos = queriedProject.getType_pos_project();
            spinnerProject.setSelection(receivedPos);

            if(receivedPos == 1) {
                if (receivedClientId != -1) {
                    Client queriedClient = dbHelper.getOneClient(receivedClientId);
                    String company = queriedClient.getCompany_client();
                    int pos = queriedClient.getType_pos_client();
                    if (pos == 1) {
                        editCompanyProject.setText(queriedClient.getName_client());
                    } else {
                        editCompanyProject.setText(queriedClient.getCompany_client());
                    }
                    editNameProject.setText(queriedClient.getProject_client());
                } else {
                    editCompanyProject.setText(queriedProject.getCompany_project());
                    editNameProject.setText(queriedProject.getName_project());
                }
            }

            if(receivedPos == 2){
                if(receivedMyProject != null) {
                    editNameMyProject.setText(receivedMyProject);
                } else {
                    editNameMyProject.setText(queriedProject.getName_project());
                }
            }

            if(receivedColleaguesId != -2){
                Colleagues queriedColleagues = dbHelper.getOneColleag(receivedColleaguesId);
                editResponsibleProject.setText(queriedColleagues.getName_colleag());
            } else {
                editResponsibleProject.setText(queriedProject.getResponsible_for_project());
            }

            if(receivedTime != -4){
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                String date = sdf.format(new Date((receivedTime)*1000L));
                editFinishProject.setText(date);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                String date = sdf.format(new Date((queriedProject.getDate_finish_project())*1000L));
                editFinishProject.setText(date);
            }

        }

    }

    public void saveProject(){

        Integer type_pos_project = spinnerProject.getSelectedItemPosition();
        String type_project = spinnerProject.getSelectedItem().toString();
        String name_project = editNameProject.getText().toString().trim();
        String name_my_project = editNameMyProject.getText().toString().trim();
        String company_my_project = "Собственный проект";
        String company_project = editCompanyProject.getText().toString().trim();
        String responsible_for_project = editResponsibleProject.getText().toString().trim();
        Integer date_finish_project = receivedTime;


        dbHelper = new DBHelper(this);

        if(type_pos_project == 1){
            //error name is empty

            if(!name_project.isEmpty()){

                if(!company_project.isEmpty()){

                    if(receivedTime != -4) {
                        if (receivedTime >= (componentDateToday() + 3600)) {
                            Project project = new Project(type_project, type_pos_project, name_project,
                                    company_project, responsible_for_project, date_finish_project);
                            dbHelper.saveNewPrject(project);
                            goBackHome();
                        } else {
                            Snackbar.make(btnOkProject, "Дата сдачи должна быть минимум на день больше " +
                                    "сегодншнего числа", Snackbar.LENGTH_SHORT)
                                    .setAction("ActionDZ", null).show();
                        }
                    } else {
                        Snackbar.make(btnOkProject, "Добавьте дату ", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkProject, "Выберите заказчика", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOkProject, "Выберите проект", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if(type_pos_project == 2){
            //error name is empty

            if(!name_my_project.isEmpty()){

                if(receivedTime != -4) {
                    if (receivedTime >= (componentDateToday() + 3600)) {
                        Project project = new Project(type_project, type_pos_project, name_my_project,
                                company_my_project, responsible_for_project, date_finish_project);
                        dbHelper.saveNewPrject(project);
                        goBackHome();
                    } else {
                        Snackbar.make(btnOkProject, "Дата сдачи должна быть минимум на день больше " +
                                "сегодншнего числа", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkProject, "Добавьте дату ", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOkProject, "Введите название проекта", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if(type_pos_project == 0){
            Snackbar.make(btnOkProject, "Выберите категорию", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goToAdd = new Intent(this, MainActivity.class);
                startActivity(goToAdd);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateProject(){

        Integer type_pos_project = spinnerProject.getSelectedItemPosition();
        String type_project = spinnerProject.getSelectedItem().toString();
        String name_project = editNameProject.getText().toString().trim();
        String name_my_project = editNameMyProject.getText().toString().trim();
        String company_my_project = "Собственный проект";
        String company_project = editCompanyProject.getText().toString().trim();
        String responsible_for_project = editResponsibleProject.getText().toString().trim();
        Integer date_finish_project = receivedTime;


        dbHelper = new DBHelper(this);

        if(type_pos_project == 1){
            //error name is empty

            if(!name_project.isEmpty()){

                if(!company_project.isEmpty()){

                    if(receivedTime != -4) {
                        if (receivedTime >= (componentDateToday() + 3600)) {
                            Project project = new Project(type_project, type_pos_project, name_project,
                                    company_project, responsible_for_project, date_finish_project);
                            dbHelper.updateProject(receivedProjectForUpdate, this, project);
                            goBackHome();
                        } else {
                            Snackbar.make(btnOkProject, "Дата сдачи должна быть минимум на день больше " +
                                    "сегодняшнего числа", Snackbar.LENGTH_SHORT)
                                    .setAction("ActionDZ", null).show();
                        }
                    } else {
                        Snackbar.make(btnOkProject, "Добавьте дату ", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkProject, "Выберите заказчика", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOkProject, "Выберите проект", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if(type_pos_project == 2){
            //error name is empty

            if(!name_my_project.isEmpty()){

                if(receivedTime != -4) {
                    if (receivedTime >= (componentDateToday() + 3600)) {
                        Project project = new Project(type_project, type_pos_project, name_my_project,
                                company_my_project, responsible_for_project, date_finish_project);
                        dbHelper.updateProject(receivedProjectForUpdate, this, project);
                        goBackHome();
                    } else {
                        Snackbar.make(btnOkProject, "Дата сдачи должна быть минимум на день больше " +
                                "сегодняшнего числа", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkProject, "Добавьте дату ", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            }else {
                Snackbar.make(btnOkProject, "Введите название проекта", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if(type_pos_project == 0){
            Snackbar.make(btnOkProject, "Выберите категорию", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }

    }

    private void goBackHome(){
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 111);
        startActivity(goToAdd);
    }


    private void AddResponsible(long clientId) {
        Intent goToID = new Intent(this, ActivityAddColleaguesForProject.class);
        goToID.putExtra("CLIENT_ID_ABOUT_PROJECT", clientId);
        goToID.putExtra("PROJECT_SPINNER", spinnerProject.getSelectedItemPosition());
        goToID.putExtra("PROJECT_TIME", receivedTime);
        receivedMyProject = editNameMyProject.getText().toString();
        goToID.putExtra("PROJECT_MY_NAME", receivedMyProject);
        goToID.putExtra("PROJECT_FOR_UPDATE", receivedProjectForUpdate);
        this.startActivity(goToID);
    }

    private void AddClient(long colleagId) {
        Intent goToID = new Intent(this, ActivityAddClientForProject.class);
        goToID.putExtra("COLLEAGUES_ID_ABOUT_PROJECT", colleagId);
        goToID.putExtra("PROJECT_SPINNER", spinnerProject.getSelectedItemPosition());
        goToID.putExtra("PROJECT_TIME", receivedTime);
        goToID.putExtra("PROJECT_FOR_UPDATE", receivedProjectForUpdate);
        this.startActivity(goToID);
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            receivedTime = componentDateToTimestampDate(year, month, day);
            String date = sdf.format(new Date((receivedTime)*1000L));
            editFinishProject.setText(date);
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

    int componentDateToday() {

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }
}
