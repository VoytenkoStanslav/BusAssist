package by.app.admin.busassist.ActivityAddAndReport;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 30.03.2018.
 */

public class ActivityAddClient extends AppCompatActivity {

    static final int GALLERY_REQUEST_CLIENT = 1;
    ImageButton imageButtonClient;


    Spinner spinnerClientType;
    EditText editCompanyClient;
    EditText editNameClient;
    EditText editNumberClient;
    EditText editSkypeClient;
    EditText editCommitClient;
    EditText editProjectClient;

    TextView tvCompany;

    Button btnOkClient;

    DBHelper dbHelper;

    String path = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvCompany = (TextView) findViewById(R.id.tvCompany);

        spinnerClientType = (Spinner) findViewById(R.id.spinnerClientType);
        spinnerClientType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    tvCompany.setVisibility(View.VISIBLE);
                    editCompanyClient.setVisibility(View.VISIBLE);
                } else {
                    tvCompany.setVisibility(View.GONE);
                    editCompanyClient.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editCompanyClient = (EditText) findViewById(R.id.editCompanyClient);

        editNameClient = (EditText) findViewById(R.id.editNameClient);

        editNumberClient = (EditText) findViewById(R.id.editNumberClient);

        editSkypeClient = (EditText) findViewById(R.id.editSkypeClient);

        editCommitClient = (EditText) findViewById(R.id.editCommitClient);

        editProjectClient = (EditText) findViewById(R.id.editProjectClient);


        imageButtonClient = (ImageButton)findViewById(R.id.imageBtnClient);
        imageButtonClient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CLIENT);
            }
        });

        btnOkClient = (Button) findViewById(R.id.btnOkClient);
        btnOkClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColleagues();
            }
        });
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

    public void saveColleagues() {

        String image_path_client = path;
        String type_client = spinnerClientType.getSelectedItem().toString();
        Integer type_pos_client = spinnerClientType.getSelectedItemPosition();
        //editNameClient.setText(String.valueOf(type_pos_client));
        String company_client = editCompanyClient.getText().toString().trim();
        String name_client = editNameClient.getText().toString().trim();
        String number_client = editNumberClient.getText().toString().trim();
        String skype_client = editSkypeClient.getText().toString().trim();
        String commit_client = editCommitClient.getText().toString().trim();
        String project_client = editProjectClient.getText().toString().trim();


        dbHelper = new DBHelper(this);

        if (type_pos_client == 1) {
            //error name is empty

            if (!name_client.isEmpty()) {

                if (!number_client.isEmpty()) {

                    if (!project_client.isEmpty()) {

                        Client client = new Client(image_path_client, type_client, type_pos_client,
                                company_client, name_client, number_client, skype_client, commit_client,
                                project_client);
                        dbHelper.saveNewClient(client);
                        goBackHome();
                    } else {
                        Snackbar.make(btnOkClient, "Введите название проекта", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkClient, "Введите номер", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            } else {
                Snackbar.make(btnOkClient, "Введите имя", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if (type_pos_client == 2) {
            //error name is empty

            if (!company_client.isEmpty()) {

                if (!name_client.isEmpty()) {

                    if (!number_client.isEmpty()) {

                        if (!project_client.isEmpty()) {

                            Client client = new Client(image_path_client, type_client, type_pos_client,
                                    company_client, name_client, number_client, skype_client, commit_client,
                                    project_client);
                            dbHelper.saveNewClient(client);
                            goBackHome();
                        } else {
                            Snackbar.make(btnOkClient, "Введите название проекта", Snackbar.LENGTH_SHORT)
                                    .setAction("ActionDZ", null).show();
                        }
                    } else {
                        Snackbar.make(btnOkClient, "Введите номер", Snackbar.LENGTH_SHORT)
                                .setAction("ActionDZ", null).show();
                    }
                } else {
                    Snackbar.make(btnOkClient, "Введите имя", Snackbar.LENGTH_SHORT)
                            .setAction("ActionDZ", null).show();
                }
            } else {
                Snackbar.make(btnOkClient, "Введите название компаннии", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }

        if(type_pos_client == 0){
            Snackbar.make(btnOkClient, "Выберите заказчика", Snackbar.LENGTH_SHORT)
                    .setAction("ActionDZ", null).show();
        }
    }

    private void goBackHome(){
        Intent goToAdd = new Intent(this, MainActivity.class);
        goToAdd.putExtra("SIGNAL_BACK", 222);
        startActivity(goToAdd);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch(requestCode) {
            case GALLERY_REQUEST_CLIENT:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    path = getAbsPath(getApplicationContext(), selectedImage);

                    bitmap = decodeSampledBitmapFromResource(path, 100, 150);
                    imageButtonClient.setImageBitmap(bitmap);
                }
        }
    }


    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static String getAbsPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String picturePath = cursor.getString(columnIndex); // returns null
        cursor.close();
        return picturePath;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }
}
