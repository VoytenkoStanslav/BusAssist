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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

public class ActivityAddColleagues extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;
    ImageButton imageButtonColl;

    EditText editNameColl;
    EditText editNumberColl;
    EditText editSkypeColl;
    EditText editCommitColl;

    Button btnOkColl;

    DBHelper dbHelper;

    String path = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_colleagues);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editNameColl = (EditText) findViewById(R.id.editNameColl);

        editNumberColl = (EditText) findViewById(R.id.editNumberColl);

        editSkypeColl = (EditText) findViewById(R.id.editSkypeColl);

        editCommitColl = (EditText) findViewById(R.id.editCommitColl);

        imageButtonColl = (ImageButton)findViewById(R.id.imageBtnColl);
        imageButtonColl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        btnOkColl = (Button) findViewById(R.id.btnOkColl);
        btnOkColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColleagues();
            }
        });
    }

    public void saveColleagues(){

        String image_uri = path;
        String name_colleage = editNameColl.getText().toString().trim();
        String number_colleag = editNumberColl.getText().toString().trim();
        String skype_colleag = editSkypeColl.getText().toString().trim();
        String commit_colleag = editCommitColl.getText().toString().trim();


        dbHelper = new DBHelper(this);

        if(!name_colleage.isEmpty()){
            //error name is empty

            if(!number_colleag.isEmpty()){

                Colleagues colleagues = new Colleagues(image_uri, name_colleage, number_colleag, skype_colleag, commit_colleag);
                dbHelper.saveNewColleag(colleagues);
                goBackHome();
            }else {
                Snackbar.make(btnOkColl, "Введите номер", Snackbar.LENGTH_SHORT)
                        .setAction("ActionDZ", null).show();
            }
        }else {
            Snackbar.make(btnOkColl, "Введите имя", Snackbar.LENGTH_SHORT)
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
        goToAdd.putExtra("SIGNAL_BACK", 333);
        startActivity(goToAdd);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    path = getAbsPath(getApplicationContext(), selectedImage);


                    //uri = String.valueOf(selectedImage);
                    //Uri selectedImageNew = Uri.parse(uri);
                    //try {
                    //    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageNew);
                    //} catch (IOException e) {
                    //    e.printStackTrace();
                   //}
                    bitmap = decodeSampledBitmapFromResource(path, 100, 150);
                    imageButtonColl.setImageBitmap(bitmap);
                    //editNameColl.setText(uri);
                    //editCommitColl.setText(path);

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
    public void onBackPressed() {
    }
}
