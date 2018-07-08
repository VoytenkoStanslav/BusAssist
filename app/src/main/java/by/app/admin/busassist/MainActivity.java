package by.app.admin.busassist;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import by.app.admin.busassist.Dialog.DialogFragmentAdd;
import by.app.admin.busassist.Fragments.FragmentCalendar;
import by.app.admin.busassist.Fragments.FragmentClient;
import by.app.admin.busassist.Fragments.FragmentColleagues;
import by.app.admin.busassist.Fragments.FragmentCosts;
import by.app.admin.busassist.Fragments.FragmentGraphicMinus;
import by.app.admin.busassist.Fragments.FragmentGraphicPlus;
import by.app.admin.busassist.Fragments.FragmentMain;
import by.app.admin.busassist.Fragments.FragmentOptions;
import by.app.admin.busassist.Fragments.FragmentProject;
import by.app.admin.busassist.Fragments.FragmentSchedule;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    Class fragmentClass = null;
    DialogFragment dialog;

    private int receivedSignal;

    int d = 0;

    public static final Integer PERMISSION_REQUEST_CODE = 1;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            //get intent to get person id
            receivedSignal = getIntent().getIntExtra("SIGNAL_BACK", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(d==0){
            requestMultiplePermissions();
        }

        FragmentManager fragmentManager;

        if(receivedSignal == 111){
            fragment = new FragmentProject();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
            setTitle("Проекты");
        } else {
            if(receivedSignal == 222) {
                fragment = new FragmentClient();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                setTitle("Заказы");
            } else {
                if(receivedSignal == 333) {
                    fragment = new FragmentColleagues();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                    setTitle("Сотрудники");
                } else {
                    if(receivedSignal == 444) {
                        fragment = new FragmentCosts();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                        setTitle("Баланс");
                    } else {
                        if(receivedSignal == 555) {
                            fragment = new FragmentSchedule();
                            fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                            setTitle("Список задач");
                        } else {
                            if(receivedSignal == 666) {
                                fragment = new FragmentOptions();
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                                setTitle("Параметры");
                            } else {
                                fragment = new FragmentMain();
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                                setTitle("Главная");
                            }
                        }
                    }
                }
            }
        }

        dialog = new DialogFragmentAdd();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getSupportFragmentManager(), "dialog");
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                d++;
            }
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                ActivityCompat.finishAffinity(this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Подтверждение");
        builder.setMessage("Выйти из приложения?");
        builder.setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishAffinity();
                System.exit(0);

            }
        });

        builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Main) {

            fragmentClass = FragmentMain.class;

        } else if (id == R.id.Schedule) {

            fragmentClass = FragmentSchedule.class;

        } else if (id == R.id.Costs) {

            fragmentClass = FragmentCosts.class;

        } else if (id == R.id.Colleagues) {

            fragmentClass = FragmentColleagues.class;

        } else if (id == R.id.Partners) {

            fragmentClass = FragmentClient.class;

        } else if (id == R.id.Project) {

            fragmentClass = FragmentProject.class;

        } else if (id == R.id.Calendar) {

            fragmentClass = FragmentCalendar.class;

        } else if (id == R.id.Options) {

            fragmentClass = FragmentOptions.class;

        } else if (id == R.id.GrafMinus){

            fragmentClass = FragmentGraphicMinus.class;

        } else if (id == R.id.GrafPlus){

            fragmentClass = FragmentGraphicPlus.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставляем фрагмент, заменяя текущий фрагмент
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
